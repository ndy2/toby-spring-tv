package demo.tobyspringtv.episode_2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeTokenV2 {

    static class TypeUnsafeMap {
        Map<String, Object> map = new HashMap<>();

        void run(){
            map.put("a", "a");
            map.put("b", 1);
            Integer i = (Integer) map.get("b");
            String a = (String)map.get("a");
        }
    }

    static class TypeSafeMapV0{
        Map<Class<?>, Object> map = new HashMap<>();

        void put(Class<?> clazz, Object value){
            map.put(clazz, value);
        }
    }

    /**
     * 타입 토큰을 활용한 타입 안정성
     * 한계 - 타입 별로 한개씩만 저장 가능
     *  e.g. 리스트
     *       m.put(List<Integer>.class, Arrays.asList(1,2,3));
     *       m.put(List<String>.class, Arrays.asList("A","B","C")); - 불가능
     *
     * 즉 제네릭을 포함한 타입 토큰 활용 불가
     * -> 수퍼 타입 토큰!
     */
    static class TypeSafeMapV1{
        Map<Class<?>, Object> map = new HashMap<>();

        <T> void put(Class<T> clazz, T value){
            map.put(clazz, value);
        }

        <T> T get(Class<T> clazz){
//            return (T)map.get(clazz);   // Typesafe 하지 않다.
            return clazz.cast(map.get(clazz));
        }
    }

    public static void main(String[] args) throws Exception {
        TypeSafeMapV0 mapV0 = new TypeSafeMapV0();
        mapV0.put(Integer.class, "Value"); // 망했다

        TypeSafeMapV1 mapV1 = new TypeSafeMapV1();
        mapV1.put(String.class, "value");
        mapV1.put(List.class , Arrays.asList(1,2,3));
//        mapV1.put(Integer.class, "value");  // Typesafe!

        String s = mapV1.get(String.class);
        System.out.println("s = " + s);

        List list = mapV1.get(List.class);
        System.out.println("list = " + list);
    }
}
