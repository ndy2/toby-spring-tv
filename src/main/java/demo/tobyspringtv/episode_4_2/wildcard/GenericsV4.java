package demo.tobyspringtv.episode_4_2.wildcard;

import java.util.*;

public class GenericsV4 {

    static <T> void reverseTypeParam(List<T> list){
        List<T> temp = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i,temp.get(list.size()-i-1));
        }
    }

    //캡쳐 - 나는 필요 없을꺼 같아서 와일드 카드로 줬는데 안애서는 타입이 필요함
//    방법 1 - 타입 파라미터로 쓴다
//    방법 2 - Raw Type 으로 Casting 하여 쓴다
//    방법 3 - Capture 하는 Helper 메소드를 만들어라... - API 구현에 대한 오해를 막자!!
    static void reverseWildcard(List<?> list){
        reverseHelper(list);
    }

    private static <T> void reverseHelper(List<T> list){
        List<T> temp = new ArrayList<>(list);
        for (int i = 0; i < list.size(); i++) {
            list.set(i,temp.get(list.size()-i-1));
        }
    }


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        reverseTypeParam(list);
        list.forEach(System.out::print);
        System.out.println();
        reverseWildcard(list);
        list.forEach(System.out::print);

    }
}
