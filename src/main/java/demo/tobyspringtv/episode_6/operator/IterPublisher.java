package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class IterPublisher implements Publisher<Integer> {

    Iterable<Integer> iter;

    public IterPublisher(Iterable<Integer> iter) {
        this.iter = iter;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> sub) {
        sub.onSubscribe(new Subscription() {
            @Override
            public void request(long l) {
                try{
                    iter.forEach(a -> sub.onNext(a));
                    sub.onComplete();
                }catch (Throwable t){
                    sub.onError(t);
                }
            }

            @Override
            public void cancel() {

            }
        });
    }
}
