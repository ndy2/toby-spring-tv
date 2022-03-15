package demo.tobyspringtv.episode_9;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// See application.properties - server.tomcat.threads.max=1
@Slf4j
public class LoadTest {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        ExecutorService es = Executors.newFixedThreadPool(100);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rest?idx={idx}";

        CyclicBarrier barrier = new CyclicBarrier(101);

        for (int i = 0; i < 100; i++) {
            es.submit(() -> {
                int idx = counter.addAndGet(1);
                barrier.await();
                log.info("Thread " + idx);
                StopWatch watch = new StopWatch();

                watch.start();
                restTemplate.getForObject(url, String.class);
                watch.stop();
                log.info("Elapsed : {} -> {}", idx, watch.getTotalTimeSeconds());
                return null;
            });
        }
        barrier.await();
        StopWatch main = new StopWatch();
        main.start();

        es.shutdown();
        es.awaitTermination(100, TimeUnit.SECONDS);
        main.stop();

        log.info("Total Elapsed : {}", main.getTotalTimeSeconds());
    }
}
