package demo.tobyspringtv.episode_4_2.multiplebound;

import java.util.function.Function;

public class IntersectionTypeV2 {

    interface Hello{
        default void hello(){
            System.out.println("hello");
        }
    }

    interface Hi{
        default void hi(){
            System.out.println("hi");
        }
    }

    public static void main(String[] args) {
        hello((Function & Hello & Hi) s->s);
    }

    private static <T extends Function & Hello & Hi> void hello(T t) {
        t.hello();
        t.hi();
    }
}
