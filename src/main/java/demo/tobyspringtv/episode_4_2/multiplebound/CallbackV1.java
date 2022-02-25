package demo.tobyspringtv.episode_4_2.multiplebound;

import java.util.function.Consumer;
import java.util.function.Function;

public class CallbackV1 {

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

    interface Printer{
        default void print(String str){
            System.out.println("str = " + str);
        }
    }

    public static void main(String[] args) {
        run((Function & Hello & Hi) s->s, o-> {
            o.hello();
            o.hi();
        });
    }

    private static <T extends Function> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
