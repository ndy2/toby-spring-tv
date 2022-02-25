package demo.tobyspringtv.episode_4_2.wildcard;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GenericsV1 {

    //메소드의 타입 파라미터 - 리턴 값 바로 앞
    <T> void method1(List<T> t){}
    static <T> void method2(List<T> t){}

    //메소드의 와일드 카드
    //내부에서 사용 불가
    //unbound wildcard
    static void method3(List<?> list){
        //아래의 메서드 밖에 사용 불가 (add는 null만)
        //근데 왜씀?
        list.add(null);
        list.size();
        list.clear();
        Iterator<?> it = list.iterator();

        list.toString();
    }

    static <T> boolean isEmptyTypeParam(List<T> list){
        return list.size() == 0;
    }

    //타입에 관심이 없다
    //리스트의 기능만 사용
    static boolean isEmptyWildcard(List<?> list){
        return list.size() == 0;
    }

    public static void main(String[] args) {
        List<Integer> emptyList = Collections.emptyList();
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        method2(list);

        System.out.println(isEmptyTypeParam(list));
        System.out.println(isEmptyTypeParam(emptyList));

        System.out.println(isEmptyWildcard(list));
        System.out.println(isEmptyWildcard(emptyList));
    }
}
