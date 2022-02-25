package demo.tobyspringtv.episode_2;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * List 의 Generic 정보까지 넣어보자 - Super Type Token
 */
public class SuperTypeTokenV3 {
    static class TypeSafeMapV3{
        Map<TypeReference, Object> map = new HashMap<>();

        <T> void put(TypeReference<T> tr, T value){
            map.put(tr, value);
        }

        <T> T get(TypeReference<T> tr){
            //type : String, Integer -> Class<T> 로 Casting 가능
            if(tr.type instanceof Class<?>){
                return ((Class<T>)tr.type).cast(map.get(tr));

            //type : List<Integer> , List<String> -> Casting 바로 안됨
            }else{
                return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr));
            }

        }
    }

    static class TypeReference<T> { //Sup
        Type type;

        public TypeReference() {
            Type stype = getClass().getGenericSuperclass();
            if(stype instanceof ParameterizedType){
                this.type = ((ParameterizedType)stype).getActualTypeArguments()[0];
            }else throw new RuntimeException();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
            if (o == null || getClass().getSuperclass() != o.getClass().getSuperclass()) return false;

            TypeReference<?> that = (TypeReference<?>) o;
            return Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type);
        }
    }

    public static void main(String[] args) {

//        TypeReference t = new TypeReference<String>();
//        System.out.println(t.type); // 런타임 익셉션

        // 상속하자! 익명클래스로! - 바디만 만들어주면 됨
        TypeReference t = new TypeReference<String>(){};
        System.out.println(t.type); // 런타임 익셉션


        TypeSafeMapV3 m = new TypeSafeMapV3();
        m.put(new TypeReference<Integer>(){}, 1);
        m.put(new TypeReference<String>(){}, "string");
        m.put(new TypeReference<List<Integer>>(){}, Arrays.asList(1,2,3));
        m.put(new TypeReference<List<String>>(){}, Arrays.asList("a","b","c"));

        //equals and hashcode 를 구현해야 null이 안나온다.
        // -> 그래도 null 이 나온다
        // -> 익명 class 라서 getClass()하면 다르게 나온다.
        // -> 익명 class 를 재활용 해도 안된다.
        System.out.println(m.get(new TypeReference<Integer>(){}));
        System.out.println(m.get(new TypeReference<String>(){}));
        System.out.println(m.get(new TypeReference<List<Integer>>(){}));
        System.out.println(m.get(new TypeReference<List<String>>(){}));

    }
}
