package demo.tobyspringtv.episode_4;

import java.util.*;


public class TypeInference {

    static <T> void method(T t, List<T> list){

    }

    public static void main(String[] args) {

        //타입 힌트 - Witness - 추론을 도와줌
        TypeInference.<Integer>method(1, Arrays.asList(1,2,3));

        //다이아몬드 연산자
        List<String> str = new ArrayList<>();

        //인자가 없는데도 컴파일러가 타입 추론을 함
        List<String> c = Collections.emptyList();
    }
}
