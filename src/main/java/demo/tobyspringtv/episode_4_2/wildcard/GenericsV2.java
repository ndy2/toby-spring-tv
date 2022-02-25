package demo.tobyspringtv.episode_4_2.wildcard;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GenericsV2 {
    //내가 내부 구현에서 T 타입을 활용하겠다
    // -> 내부 구현 노출
    // -> 설계의 의도를 바르게 드러내지 못한 것
    static <T> long frequencyTypeParam(List<T> list, T elem){
        return list.stream().filter(e-> e.equals(elem)).count();
    }

    // ->이런 경우 와일드 카드를 사용할 것을 권장
    static long frequencyWildcard(List<?> list, Object elem){
        return list.stream().filter(e-> e.equals(elem)).count();
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,4,1,3,1);

        System.out.println(frequencyTypeParam(list,1));
        System.out.println(frequencyTypeParam(list,2));

        System.out.println(frequencyWildcard(list,1));
        System.out.println(frequencyWildcard(list, 2));
    }
}
