package demo.tobyspringtv.episode_8.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 토비의 봄 TV 8 화 - 스프링 리액티브 웹 개발 4부, 자바와 스프링의 비동기 개발 기술 살펴보기.
 */
@Slf4j
public class FutureExample {

    /* 비동기 작업의 결과를 받는 그릇 */
    //Future
    //Callback

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService es = Executors.newCachedThreadPool();
        /*es.execute(() ->
        {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
            log.info("Async");
        });*/

        Future<String> future = es.submit(() -> {   // submit 은 Non-Blocking 임
            Thread.sleep(2000);
            log.info("Async");
            return "Hello";
        });
        log.info("Another Work!");

        String result = future.get(); // -> future 의 get 은 Blocking 임
        log.info("result :  {}", result);

        log.info("Exit");
        es.shutdown();
    }
}
