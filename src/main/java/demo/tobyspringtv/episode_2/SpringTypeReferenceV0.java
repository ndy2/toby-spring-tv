package demo.tobyspringtv.episode_2;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class SpringTypeReferenceV0 {

    public static void main(String[] args) {
        // 3.2 >=
        ParameterizedTypeReference<?> typeRef =
                new ParameterizedTypeReference<List<Map<Set<Integer>,String>>>() {};
        System.out.println(typeRef.getType());
    }
}
