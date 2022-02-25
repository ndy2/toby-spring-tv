package demo.tobyspringtv.episode_4;

//제네릭 클래스일때
public class GenericsV3<T> {

    //생성자에도 사용 가능
    public <S> GenericsV3(S s) {
    }

    void print(T t){
        System.out.println(t);
    }

    //메소드에 타입 파라미터를 사용하고 싶다면
    //다른 문자를 쓰자
    <S> void method1(S s){}

//    <S> T Method2(S s){} 이런식도 가능





    // T는 static 으로 못쓴다.
    // Class 의 타입 파라미터 T는 인스턴스가 생성될때 지정해 주는것
    // 쓰고 싶다면 Method의 타입 파라미터로 사용할것
    /*static void staticprint(T t){
        System.out.println(t);
    }*/

    public static void main(String[] args) {
//        new GenericV3<>().print("hi");
//        new GenericV3<>().print(1);


    }
}
