package demo.tobyspringtv.episode_4_2.multiplebound;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Delegate
 */
public class CallbackV2 {

    interface DelegateTo<T> {
        T delegate();
    }

    interface Hello extends DelegateTo<String>{
        default void hello(){
            System.out.println("hello " + delegate());
        }
    }

    interface UpperCase extends DelegateTo<String>{
        default void toUpper(){
            System.out.println(delegate().toUpperCase());
        }
    }

    public static void main(String[] args) {
        run((DelegateTo<String> & Hello & UpperCase)()->"toby", o->{
            o.hello();
            o.hello();
            o.toUpper();
        });
    }

    private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

}
