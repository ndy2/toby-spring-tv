package demo.tobyspringtv.episode_4_2.wildcard;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenericsV3 {
    static <T extends Comparable<T>> T maxTypeParam(List<T> list){
        return list.stream()
                .reduce((a,b)-> a.compareTo(b)>0?a:b).get();
    }

    // 와일드 카드 가이드 라인
    // 메소드 안애서 사용된다 -> extends (upper bound)
    // 메소드 밖에서 사용된다 -> super (lower bound)
    static <T extends Comparable<? super T>> T maxTypeParamWildcard(List<? extends T> list){
        return list.stream()
                .reduce((a,b)-> a.compareTo(b)>0?a:b).get();
    }


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,4,1,3,1);

        System.out.println(maxTypeParam(list));
        System.out.println(maxTypeParamWildcard(list));
        System.out.println(Collections.max(list,(a,b) ->a-b));

        //??????
        System.out.println(Collections.<Number>max(list,(Comparator<Object>) (a, b) ->a.toString().compareTo(b.toString())));
    }
}
