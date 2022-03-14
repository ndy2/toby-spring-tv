package demo.tobyspringtv.completablefuture.hugrydiver;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author jibumjung
 */
class completablefuture {

    // 5초 동안 쉬었다가 성공 로그를 찍는 TASK
    Runnable task = () -> {
        try {
            TimeUnit.SECONDS.sleep(5l);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("TASK completed");
    };

    @Test
    public void completableFuture() throws Exception {
        CompletableFuture
                .runAsync(task)
                .thenCompose(aVoid -> CompletableFuture.runAsync(task))
                .thenAcceptAsync(aVoid -> System.out.println("all tasks completed!!"))
                .exceptionally(throwable -> {
                    System.out.println("exception occurred!!");
                    return null;
                });

        try {
            TimeUnit.SECONDS.sleep(10l);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}