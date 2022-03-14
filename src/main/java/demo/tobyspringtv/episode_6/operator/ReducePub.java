package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.BiFunction;

public class ReducePub<T,R> implements Publisher<R> {
    private final Publisher<T> pub;
    private final R seed;
    private final BiFunction<R, T, R> reduce;

    public ReducePub(Publisher<T> pub, R seed, BiFunction<R, T, R> reduce) {
        this.pub = pub;
        this.seed = seed;
        this.reduce = reduce;
    }

    @Override
    public void subscribe(Subscriber<? super R> sub) {
        pub.subscribe(new DelegateSubscriber<T, R>(sub){
            R result = seed;

            @Override
            public void onNext(T t) {
                result = reduce.apply(result,t);
            }

            @Override
            public void onComplete() {
                sub.onNext(result);
                sub.onComplete();
            }
        });
    }
}
