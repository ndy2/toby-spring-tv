package demo.tobyspringtv.episode_2;

import demo.tobyspringtv.episode_2.MyController.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 스프링에서 수퍼 타입 토큰을 사용하는 예시
 * -> API 에서 리스트를 반환하지만 Type 을 정확하게 Mapping 할 수 있는 DTO 나 Entity 처럼 쓰고 싶다
 * -> ParameterizedTypeReference 를 사용!
 */
public class SpringTypeReferenceV1 {

    public static void main(String[] args) {
        RestTemplate rt = new RestTemplate();

        //Type 토큰 - Generic 정보가 없는 경우 잘 먹힌다.
        //딱보니까 Key Value 가 있는 Map 이로구나 (나는 User 로 달라고 했는데요?)
        // -> LinkedHashMap 으로 줄게
        // -> CastingException 빵
//        List<User> users =  rt.getForObject("http://localhost:8080", List.class);
//        for (User user : users) {
//            System.out.println("user = " + user.getName());
//        }

        ResponseEntity<List<User>> exchange = rt.exchange("http://localhost:8080"
                , HttpMethod.GET, null
                , new ParameterizedTypeReference<List<User>>() {});

        List<User> users = exchange.getBody();
        users.forEach(System.out::println);
    }
}
