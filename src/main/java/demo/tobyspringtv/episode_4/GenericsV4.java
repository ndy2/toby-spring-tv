package demo.tobyspringtv.episode_4;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * upper/lower
 * Bounded Type Parameter
 *
 * 여러개도 가능 but class 는 하나
 */
public class GenericsV4 {

    static <T extends Comparable<T>> long countGreaterThan(T[] arr, T elem){
        return Arrays.stream(arr).filter(s -> s.compareTo(elem)>0).count();
    }

    public static void main(String[] args) {
        System.out.println(countGreaterThan(new String[] {"a","b","c","d","e"}, "d"));

        System.out.println(countGreaterThan(new Integer[] {1,2,3,4,5}, 4));
    }
}
