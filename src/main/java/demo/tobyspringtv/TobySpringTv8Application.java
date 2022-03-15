package demo.tobyspringtv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

@Slf4j
//@EnableAsync
//@SpringBootApplication
public class TobySpringTv8Application {

    public static void main(String[] args) {
        SpringApplication.run(TobySpringTv8Application.class, args);

    }

    // [nio-8080-exec-1] : 서블릿 쓰레드
    // [      MvcAsync1] : 작업 쓰레드
    @RestController
    public static class MyController {

        @GetMapping("/callable")
        public Callable<String> callable() {
            log.info("callable");
            return () -> {
                log.info("async");
                Thread.sleep(2000);
                return "Hello";
            };
        }

        @GetMapping("/async")
        public String async() throws InterruptedException {
            log.info("callable");
            Thread.sleep(2000);
            return "Hello";
        }

        Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();

        @GetMapping("/deferred")
        public DeferredResult<String> deferredResult() throws InterruptedException {
            log.info("deferred");
            Thread.sleep(2000);
            DeferredResult<String> dr = new DeferredResult<>();
            results.add(dr);
            return dr;
        }

        @GetMapping("/defferred/count")
        public String drCount(){
            return String.valueOf(results.size());
        }


        @GetMapping("/defferred/event")
        public String drEvent(String message){
            for (DeferredResult<String> deferredResult : results) {
                deferredResult.setResult("Hello " +message);
                results.remove(deferredResult);
            }
            return "OK";
        }

        @GetMapping("/emitter")
        public ResponseBodyEmitter emitter() {
            ResponseBodyEmitter emitter = new ResponseBodyEmitter();
            Executors.newSingleThreadExecutor().submit(() -> {
                for (int i = 0; i < 50; i++) {
                    try {
                        emitter.send("<p>Stream " + i + "</p>");
                        Thread.sleep(2000);
                    } catch (Exception ignored) {}
                }
            });

            return emitter;
        }
    }
}
