package demo.tobyspringtv.completablefuture.brunch;

import demo.tobyspringtv.completablefuture.brunch.code.CoffeeComponent;
import demo.tobyspringtv.completablefuture.brunch.code.CoffeeRepository;
import demo.tobyspringtv.completablefuture.brunch.code.TaskConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringJUnitConfig({CoffeeComponent.class, CoffeeRepository.class, TaskConfig.class})
class CoffeeComponentTest {

    @Autowired CoffeeComponent coffeeComponent;

    @Test
    void 가격_조회_동기_블록킹_호출_테스트(){
        int expectedPrice = 1100;
        int resultPrice = coffeeComponent.getPrice("latte");
        log.info("최종 가격 전달 받음");

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    //Non-Blocking 과 Blocking  이 혼합 되어있음
    @Test
    void 가격_조회_비동기_블록킹_호출_테스트(){
        int expectedPrice = 1100;
        CompletableFuture<Integer> future = coffeeComponent.getPriceAsync("latte");
        log.info("아직 최종 데이터를 전달 받지는 않았지만, 다른 작업 수행 가능");
        int resultPrice = future.join();
        log.info("최종 가격 전달 받음");

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }


    //Non-Blocking 구현 - thenAccept, thenApply
    /*
     * 위 코드는 get, join 메서드를 호출하는 순간 블록킹 현상이 발생함
     * 논블록킹으로 개선하기 위해서는 콜백함수를 구현해야함
     * get 이나 join 을 사용해서 최종 연산이 된 데이터를 클라이언트가 직접 조회할 필요 X
     * CompletableFuture 가 알아서 최종 연산이 되면 콜백 함수를 실행 해주기 때문
     */
    @Test
    public void 가격_조회_비동기_호출_콜백_반환없음_테스트(){
        Integer expectedPrice = 1100;

        CompletableFuture<Void> future = coffeeComponent
                .getPriceAsync("latte")
                .thenAccept(p -> {
                    log.info("콜백, 가격은 " + p + "원, 하지만 데이터를 반환하지는 않음");
                    assertThat(p).isEqualTo(expectedPrice);
                });
        log.info("아직 최종 데이터를 전달 받지는 않았지만, 다른 작업 수행 가능, 논블록킹");

        /*
        아래 구문이 없으면, main thread 가 종료되기 때문에, thenAccept 확인하기 전에 끝남
        그래서, 테스트를 위해서 메인쓰레드가 종료되지 않도록 블록킹으로 대기하기 위한 코드
        future 가 complete 이 되면 위에 작성한 thenAccept 코드가 실행이 됨
         */
        assertThat(future.join()).isNull();
    }

    @Test
    void 가격_조회_비동기_호출_콜백_반환_테스트(){
        Integer expectedPrice = 1100 + 100;

        CompletableFuture<Void> future = coffeeComponent
                .getPriceAsync("latte")
                .thenApply(p -> {
                    log.info("같은 쓰레드로 동작");
                    return p + 100;
                })
                .thenAccept(p -> {
                    log.info("콜백, 가격은 " + p + "원, 하지만 데이터를 반환하지는 않음");
                    assertThat(p).isEqualTo(expectedPrice);
                });
        log.info("아직 최종 데이터를 전달 받지는 않았지만, 다른 작업 수행 가능");
        assertThat(future.join()).isNull();
    }

    @Test
    void 가격_조회_비동기_호출_콜백_반환_테스트_다른_쓰레드(){
        Integer expectedPrice = 1100 + 100;
        Executor executor = Executors.newFixedThreadPool(5);

        CompletableFuture<Void> future = coffeeComponent
                .getPriceAsync("latte")
                .thenApplyAsync(p -> {
                    log.info("다른 쓰레드로 동작");
                    return p + 100;
                }, executor)
                .thenAcceptAsync(p -> {
                    log.info("콜백, 가격은 " + p + "원, 하지만 데이터를 반환하지는 않음");
                    assertThat(p).isEqualTo(expectedPrice);
                }, executor);
        log.info("아직 최종 데이터를 전달 받지는 않았지만, 다른 작업 수행 가능");
        assertThat(future.join()).isNull();
    }


    @Test
    void thenCombine_test(){

        Integer expectedPrice = 1100 + 1300;

        CompletableFuture<Integer> futureA = coffeeComponent.getPriceAsync("latte");
        CompletableFuture<Integer> futureB = coffeeComponent.getPriceAsync("mocha");

        Integer resultPrice = futureA.thenCombine(futureB, Integer::sum).join();
        log.info("done");
        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    // 1. 가격 조회
    // 2. 조회된 가격에 할인율 적용
    @Test
    void thenCompose_test(){
        Integer expectedPrice = (int)(1100 * 0.9);
        CompletableFuture<Integer> futureA = coffeeComponent.getPriceAsync("latte");

        Integer resultPrice = futureA.thenCompose(
                result -> coffeeComponent.getDiscountPriceAsync(result)).join();

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

    //모든 Future 가 완료 되었을 때 수행하는 allOf !
    @Test
    void allOf_test(){
        Integer expectedPrice = 1100 + 1300 + 900;

        CompletableFuture<Integer> futureA = coffeeComponent.getPriceAsync("latte");
        CompletableFuture<Integer> futureB = coffeeComponent.getPriceAsync("mocha");
        CompletableFuture<Integer> futureC = coffeeComponent.getPriceAsync("americano");

        List<CompletableFuture<Integer>> completableFutureList
                = Arrays.asList(futureA, futureB, futureC);

        Integer resultPrice = CompletableFuture.allOf(futureA, futureB, futureC)
                .thenApply(Void -> completableFutureList.stream()
                        .map(CompletableFuture::join)
                        .collect(toList()))
                .join()
                .stream()
                .reduce(0, Integer::sum);

        assertThat(resultPrice).isEqualTo(expectedPrice);
    }

}
