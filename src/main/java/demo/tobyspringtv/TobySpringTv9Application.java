package demo.tobyspringtv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackageClasses = TobySpringTv9Application.class)
public class TobySpringTv9Application {

    public static void main(String[] args) {

        SpringApplication.run(TobySpringTv9Application.class, args);
    }

    @RestController
    public static class MyController {

        RestTemplate rt = new RestTemplate();

        @GetMapping("/rest")
        public String rest(int idx){
            String result = rt.getForObject("http://localhost:8081/service?req={req}", String.class, "hello" + idx);
            //getForObject : 블로킹 메소드. -> 쓰레드 자원 낭비로 이어진다!

            return result;
        }
    }


    @RestController
    public static class MyAsyncController {

        AsyncRestTemplate rt = new AsyncRestTemplate();

        @GetMapping("/rest")
        public ListenableFuture<ResponseEntity<String>> rest(int idx){
            ListenableFuture<ResponseEntity<String>> result = rt.getForEntity("http://localhost:8081/service?req={req}", String.class, "hello" + idx);
            //getForEntity : 논-블로킹 메소드. -> 쓰레드 자원 낭비 X  여기서 2초 대기 X!
            //SpringMVC 가 callback 도 알아서 등록해줌

            return result;
        }
    }
}
