package demo.tobyspringtv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "demo.tobyspringtv.episode_2")
public class TobySpringTvApplication {

    public static void main(String[] args) {
        SpringApplication.run(TobySpringTvApplication.class, args);
    }
}
