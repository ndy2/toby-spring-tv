package demo.tobyspringtv.episode_4_2.multiplebound;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 동적 메소드 삽입(?)
 */
public class LastExample {
    interface Pair<T>{
        T getFirst();
        T getSecond();
        void setFirst(T first);
        void setSecond(T second);
    }

    static class Name implements Pair<String>{
        String firstName;
        String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String getFirst() {
            return firstName;
        }
        @Override
        public String getSecond() {
            return lastName;
        }
        @Override
        public void setFirst(String first) {
            this.firstName = first;
        }
        @Override
        public void setSecond(String second) {
            this.lastName = second;
        }
    }

    interface DelegateTo<T>{
        T delegate();
    }

    interface ForwardingPair<T> extends DelegateTo<Pair<T>>, Pair<T>{
        @Override
        default T getFirst() {
            return delegate().getFirst();
        }

        @Override
        default T getSecond() {
            return delegate().getSecond();
        }

        @Override
        default void setFirst(T first) {
            delegate().setFirst(first);
        }

        @Override
        default void setSecond(T second) {
            delegate().setSecond(second);
        }
    }

    private static <T extends DelegateTo<S>, S> void run(T t, Consumer<T> consumer){
        consumer.accept(t);
    }

    public static void main(String[] args) {
        Pair<String> name = new Name("Toby", "Lee");
        run((ForwardingPair<String> & Convertable<String> & Printable<String>)()-> name, o->{
            System.out.println(o.getFirst());
            System.out.println(o.getSecond());

            o.print();
            o.convert(s-> s.toUpperCase());
            o.print();
            o.convert(s-> s.substring(0,2));
            o.print();
        });
    }

    private static <T> void print(Pair<T> pair) {
        System.out.println(pair.getFirst() +" " + pair.getSecond());
    }

    //아래는 Pair 이후 확장한 인터페이스

    interface Convertable<T> extends DelegateTo<Pair<T>>{
        default void convert(Function<T,T> mapper){
            Pair<T> pair = delegate();
            pair.setFirst(mapper.apply(pair.getFirst()));
            pair.setSecond(mapper.apply(pair.getSecond()));
        }
    }

    //프린터도 만들자
    interface Printable<T> extends DelegateTo<Pair<T>>{
        default void print(){
            System.out.println(delegate().getFirst() +" " + delegate().getSecond());
        }
    }
}
