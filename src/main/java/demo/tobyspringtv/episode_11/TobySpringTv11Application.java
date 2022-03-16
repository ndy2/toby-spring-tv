package demo.tobyspringtv.episode_11;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@Slf4j
@SpringBootApplication(scanBasePackageClasses = TobySpringTv11Application.class)
public class TobySpringTv11Application {

    public static void main(String[] args) {
        SpringApplication.run(TobySpringTv11Application.class, args);
    }

    @RestController
    public class MyController {

        @Autowired
        MyService myService;
        AsyncRestTemplate rt = new AsyncRestTemplate();

        static final String URL1 = "http://localhost:8081/service?req={req}";
        static final String URL2 = "http://localhost:8081/service2?req={req}";

        // 코드 장풍!!!
        // 콜백 헬
        @GetMapping("/rest-callback-hell")
        public DeferredResult<String> rest(int idx) {
            DeferredResult<String> dr = new DeferredResult<>();
            ListenableFuture<ResponseEntity<String>> f1 = rt.getForEntity(URL1, String.class, "h" + idx);
            f1.addCallback(
                    s -> {
                        ListenableFuture<ResponseEntity<String>> f2 = rt.getForEntity(URL2, String.class, s.getBody());
                        f2.addCallback(s2 -> {
                            ListenableFuture<String> f3 = myService.hello(s2.getBody());
                            f3.addCallback(s3 -> {
                                dr.setResult(s3);
                            }, e -> {
                                dr.setErrorResult(e.getMessage());
                            });
                        }, e -> {
                            dr.setErrorResult(e.getMessage());
                        });
                    }, e -> {
                        dr.setErrorResult(e.getMessage());
                    }
            );

            return dr;
        }

        @GetMapping("/rest-completablefuture")
        public DeferredResult<String> rest2(int idx) {
            DeferredResult<String> dr = new DeferredResult<>();
            toCf(rt.getForEntity(URL1, String.class, "h" + idx))
                    .thenCompose(s -> toCf(rt.getForEntity(URL2, String.class, s.getBody())))
//                    .thenCompose(s2 -> toCf(myService.hello(s2.getBody()))
                    .thenApplyAsync(s2->myService.hello2(s2.getBody()))
                    .thenAccept(s3 -> dr.setResult(s3))
                    .exceptionally(e -> {
                        dr.setResult(e.getMessage());
                        return null;
                    });

            return dr;
        }

        <T> CompletableFuture<T> toCf(ListenableFuture<T> lf) {
            CompletableFuture<T> cf = new CompletableFuture<>();
            lf.addCallback(cf::complete, cf::completeExceptionally);
            return cf;
        }
    }

    @Component
    public static class MyService {
        @Async
        public ListenableFuture<String> hello(String req) {
            log.info("Hello()");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}
            return new AsyncResult<>("hello " + req);
        }

        public String hello2(String req) {
            return "hello " + req;
        }
    }

    @Bean
    ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setThreadNamePrefix("myThread-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
