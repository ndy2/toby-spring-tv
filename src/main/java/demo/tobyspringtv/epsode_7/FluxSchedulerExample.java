package demo.tobyspringtv.epsode_7;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FluxSchedulerExample {

    public static void main(String[] args) throws InterruptedException {

//        Flux.range(1, 10)
//                .publishOn(Schedulers.newSingle("pub"))
//                .log()
//                .subscribeOn(Schedulers.newSingle("sub"))
//                .subscribe(System.out::println);


        //[parallel-1] : Daemon Thread
        Flux.interval(Duration.ofMillis(200))
                .take(10)
                .subscribe(i -> log.info("onNext : {}", i));

        //Main Thread 를 살려 놓아야 로그가 찍힌다.
        TimeUnit.SECONDS.sleep(5);

        //[pool-1-thread-1] : User Thread
        /*ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute( () -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("hello");
        });*/




        log.info("Main Exit");
    }
}
