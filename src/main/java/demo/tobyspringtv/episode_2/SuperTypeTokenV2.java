package demo.tobyspringtv.episode_2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SuperTypeTokenV2 {

    // Nested Static Class != Inner class
    // Non-static nested classes == inner classes.
    static class Sup<T>{
        T value;
    }

    //Local class - 메소드 안에서만 쓸 수 있다.
    //이름까지 생략하고 싶다 -> 익명 클래스
    public static void main(String[] args){
//        Sup b = new Sup<List<String>>() {};
        Sup<List<String>> b = new Sup<>() {};

        Type t = b.getClass().getGenericSuperclass();
        ParameterizedType ptype = (ParameterizedType) t;
        System.out.println(ptype.getActualTypeArguments()[0]);
    }
}
