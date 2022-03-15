package demo.tobyspringtv.episode_8.future;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

// See application.properties - server.tomcat.threads.max=20
@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(100);
        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://localhost:8080/async";   // -> 10초 + alpha
        String url = "http://localhost:8080/callable";  // -> 2초 + alpha

        StopWatch main = new StopWatch();
        main.start();

        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                int idx = counter.addAndGet(1);
                log.info("Thread " + idx);
                StopWatch watch = new StopWatch();

                watch.start();
                restTemplate.getForObject(url, String.class);
                watch.stop();
                log.info("Elapsed : {} -> {}", idx, watch.getTotalTimeSeconds());
            });
        }

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        main.stop();

        log.info("Total Elapsed : {}", main.getTotalTimeSeconds());
    }
}
