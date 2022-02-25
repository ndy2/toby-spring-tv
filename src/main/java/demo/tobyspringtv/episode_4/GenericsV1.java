package demo.tobyspringtv.episode_4;

import java.util.ArrayList;
import java.util.List;

public class GenericsV1 {

    /**
     * @param <T> - 타입 파라미터
     */
    static class Hello<T> {
        T t;
        T method(T val){ return val; }
    }

    /**
     * @param value - 파라미터 - 매개변수
     */
    static void print(String value){
        System.out.println(value);
    }

    public static void main(String[] args) {
        //인자 argument;
        print("hello");

        // type argument
        Hello<String> stringHello = new Hello<>();

        List<String> list = new ArrayList<>();

        //목적 1. 컴파일 하는 시점에서 일관성 있게 타입 체킹
        //목적 2. 타입만 다른 클래스를 여러개 만들 필요 X - 제네릭 하게 타입만 바꿔서 재사용!
    }

}
