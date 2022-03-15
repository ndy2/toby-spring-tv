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
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
//@EnableAsync
//@SpringBootApplication
public class TobySpringTvApplication {

    public static void main(String[] args) {
//        SpringApplication.run(TobySpringTvApplication.class, args);

        try(ConfigurableApplicationContext c = SpringApplication.run(TobySpringTvApplication.class, args)){
        }
    }

    @Component
    public static class MyService{
//        @Async// -> 쓰레드 그냥 만들고 버림 : 실전에서 사용 X
        @Async("threadPool") // 하나만 등록되있으면 자동으로 설정
//        public Future<String> hello() throws InterruptedException {
        public ListenableFuture<String> hello() throws InterruptedException {
//        public CompletableFuture<String> hello() throws InterruptedException {
            log.info("Hello()");
            Thread.sleep(1000);
            return new AsyncResult<>("hello");
        }
    }

    @Autowired MyService myService;

    @Bean
    ApplicationRunner run(){
        return args -> {
            log.info("run()");
            ListenableFuture<String> future = myService.hello();
            future.addCallback(
                    s -> System.out.println("Success : " + s),
                    e -> System.out.println("Failure : " + e.getMessage())
            );
            log.info("exit(), future.isDone : {}", future.isDone());
            log.info("result : {}", future.get());
        };
    }

    @Bean
    ThreadPoolTaskExecutor threadPool(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(10);
        threadPoolTaskExecutor.setQueueCapacity(50);
        threadPoolTaskExecutor.setThreadNamePrefix("myThread-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
