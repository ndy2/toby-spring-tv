package demo.tobyspringtv.episode_4_2.multiplebound;

import java.io.Serializable;
import java.util.function.Function;

public class IntersectionTypeV1 {

    //Function 이란 인터페이스는 람다식의 구현에 사용할 수 있는
    //@FunctionalInterface 이다 - 일반 메소드가 하나만 정의된 인터페이스
    //Term : SAM - Single Abstract Method
    private static void hello1(Function o){}
    private static void hello2(Function<String,String> o){}

    //람다 -> 익명클래스와는 다름 (99퍼센트 동일함) (this 의 사용법이 다름)
    //익명클래스가 생성 안됨/ 근데 클래스는 생성됨
    public static void main(String[] args){
        //람다 오브젝트 - template
        hello1(s->s);
        hello2((String s)-> s.toUpperCase());
        hello2(String::toUpperCase);

        hello2(s->s);
        hello1((Function) s->s);

        //인터섹션 타입
        //람다식이 인터페이스를 두개 구현
        //이걸 왜 쓰냐?
        //Marker 인터페이스 - 메소드가 하나도 없는 인터페이스
        //e.g. Serializable - 나는 Serializable 해 (직렬화 가능해)
        hello1((Function & Serializable) s->s);
        hello1((Function & Serializable & Cloneable) s->s);

        //이걸 왜 쓰냐?
        //람다 오브젝트를 쓰면서 Marker 인터페이스 쓸때 클래스 만들기 싫어서


    }
}
