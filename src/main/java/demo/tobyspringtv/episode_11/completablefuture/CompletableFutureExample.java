package demo.tobyspringtv.episode_11.completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * <p>Future</p> - 비동기 작업의 결과를 담고 있는 객체!
 * <p>ListenableFuture</p> - 콜백 방식으로 결과가 완료되는 시점에 후킹을 걸어서 가져올 수 있는 Future 객체!
 * 어쨋든 위에 친구들은 비동기 실행하는 코드 내에서 후처리(콜백)를 하게함
 *
 * <p> CompletableFuture </p>
 * <li>진짜 간단하게 비동기 작업의 결과를 만들 수 있다. (??)</li>
 * <li>리스트의 모든 값이 완료될 때까지 기다릴지 아니면 하나의 값만 완료되길 기다릴지 선택할 수 있다는 것이 장점</li>
 * <li>람다 표현식과 파이프라이닝을 활용하면 구조적으로 이쁘게 만들 수 있다.</li>
 *
 * <li>병렬성과 동시성에서 CompletableFuture가 중요한데 여러개의 cpu core 사이에 지연 실행이나 예외를 callable하게 처리할 수 있어 명시적인 처리가 가능해 집니다.</li>
 */
@Slf4j
public class CompletableFutureExample {

    public static void main(String[] args) throws Exception {
//        ex1();
//        ex2();
//        ex3();
//        ex4();
        ex5();
    }

    static void ex1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.completedFuture(1);
        System.out.println(future.get());


        CompletableFuture<Integer> f = new CompletableFuture<>();
        f.complete(2);
        System.out.println(f.get());

        CompletableFuture<Integer> ex = new CompletableFuture<>();
        ex.completeExceptionally(new RuntimeException());
        System.out.println(ex.get());   // get 을 호출해야 예외가 던져짐

    }

    static void ex2() throws InterruptedException {
        CompletableFuture
                .runAsync(()->log.info("runAsync"))
                .thenRun(()->log.info("thenRun"))
                .thenRun(()->log.info("thenRun"));
        log.info("exit");

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

    static void ex3() throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("supplyAsync");
                    return 1;
                })
                .thenApply(s -> {
                    log.info("thenApply : {}", s);
                    return s+1;
                })
                .thenAccept(s -> log.info("thenAccept : {}", s));

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * function 의 리턴 값이 그냥 값이 아니고 CompletableFuture인 경우
     * thenCompose 를 사용하자!
     */
    static void ex4() throws InterruptedException {
        CompletableFuture
                .supplyAsync(() -> {
                    log.info("supplyAsync");
                    if(1==1) throw new RuntimeException("예외 빵!");
                    return 1;
                })
                .thenCompose(s -> {
                    log.info("thenApply : {}", s);
                    return CompletableFuture.completedFuture(s+1);
                })
                .exceptionally(e -> -10) // 성공 콜백과 예외 콜백을 꼭 쌍으로 만들지 않아도 된다!!
                .thenAccept(s -> log.info("thenAccept : {}", s));

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }

    /**
     * 쓰레드를 새로 생성해서 적용하고 싶으면 thenXXXAsync 를 쓰자! , 쓰레드 풀을 파라미터로 넘기면서!
     */
    static void ex5() throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CompletableFuture
                .supplyAsync(() -> {
                    log.info("supplyAsync");
                    return 1;
                }, es)
                .thenCompose(s->{
                    log.info("thenCompose : {}", s);
                    return CompletableFuture.completedFuture(s+1);
                })
                .thenComposeAsync(s -> {
                    log.info("thenComposeAsync : {}", s);
                    return CompletableFuture.completedFuture(s+1);
                }, es)
                .thenAcceptAsync(s -> log.info("thenAccept : {}", s), es);

        ForkJoinPool.commonPool().shutdown();
        ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
    }
}
