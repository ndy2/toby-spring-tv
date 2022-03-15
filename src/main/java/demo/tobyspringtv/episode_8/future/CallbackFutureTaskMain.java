package demo.tobyspringtv.episode_8.future;

import demo.tobyspringtv.episode_8.future.code.CallbackFutureTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CallbackFutureTaskMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask futureTask = new CallbackFutureTask(() -> {
            Thread.sleep(2000);
            /*if(1==1) throw new RuntimeException();*/
            log.info("Async");
            return "Hello";
        },
                result -> log.info("result : {} ", result),
                e -> log.error("error : ", e)
        );

        es.execute(futureTask);
        es.shutdown();
    }
}
