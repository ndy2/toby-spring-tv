package demo.tobyspringtv.episode_4;

public class GenericMethodV1 {

    <T> void print(T t){
        System.out.println(t);
    }

    static <T>  void staticprint(T t){
        System.out.println(t);
    }

    public static void main(String[] args) {
        new GenericMethodV1().print("hi");
        new GenericMethodV1().print(1);

        staticprint("hi");
        staticprint(1);
    }
}
