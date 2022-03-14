package demo.tobyspringtv.completablefuture.brunch.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeComponent implements CoffeeUseCase {

    private final CoffeeRepository coffeeRepository;

//    Executor executor = Executors.newFixedThreadPool(2);
    private final ThreadPoolTaskExecutor executor;

    @Override
    public int getPrice(String name) {
        log.info("동기 호출 방식으로 가격 조회 시작");
        return coffeeRepository.getPriceByName(name);
    }

    // Covariant Return Type
    /*@Override
    public CompletableFuture<Integer> getPriceAsync(String name) {
        log.info("비동기 호출 방식으로 가격 조회 시작");
        CompletableFuture<Integer> future = new CompletableFuture<>();

        new Thread(() -> {
            log.info("새로운 쓰레드로 작업 시작");
            int price = coffeeRepository.getPriceByName(name);
            future.complete(price);
        }).start();

        return future;
    }*/

    // CompletableFuture 의 팩토리 메서드 활용
    @Override
    public CompletableFuture<Integer> getPriceAsync(String name) {
        log.info("비동기 호출 방식으로 가격 조회 시작");

        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("새로운 쓰레드로 작업 시작 - supplyAsync");
                    return coffeeRepository.getPriceByName(name);
                }, executor);   // executor 를 추가하여 ForkJoinPool 의 CommonPool 이 아닌 별도로 정의한 쓰레드풀 활용
    }

    @Override
    public CompletableFuture<Integer> getDiscountPriceAsync(Integer price) {
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("새로운 쓰레드로 작업 시작 - supplyAsync");
                    return (int)(0.9*price);
                }, executor);
    }
}
