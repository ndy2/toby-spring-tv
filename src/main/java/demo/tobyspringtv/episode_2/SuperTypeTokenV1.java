package demo.tobyspringtv.episode_2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SuperTypeTokenV1 {

    static class Sup<T>{
        T value;
    }

    static class Sub extends Sup<List<String>> {

    }

    public static void main(String[] args) throws NoSuchFieldException {
        Sup<String> s = new Sup<>();
        s.value = "string";


        // ===== 그냥 Object 가 나온다 ====
        // ===== erasure 에 의해 runtime 에서 위 정보가 싸그리 사라짐
        // ===== (바이트 코드 호환성 문제 때문)
        System.out.println(s.getClass().getDeclaredField("value").getType());

        // ==== 정확한 타입 정보를 알 수 없다.
        // ==== 요거를 가져 올 수 있는 경우가 있다.


        //상속하는 시점에 제네릭을 넘긴 Sub
        //reification 했다고 볼 수 있다.
        Sub b = new Sub();
        Type t = b.getClass().getGenericSuperclass();
        ParameterizedType ptype = (ParameterizedType) t;
        System.out.println(ptype.getActualTypeArguments()[0]);

        // 어떤건 없어지고 어떤건 안 없어진다.
        // 여기서 아이디어를 내서 V2
    }
}
