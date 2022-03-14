package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class SumPublisher implements Publisher<Integer> {
    private final Publisher<Integer> pub;

    public SumPublisher(Publisher<Integer> pub) {
        this.pub = pub;
    }

    @Override
    public void subscribe(Subscriber<? super Integer> sub) {
        pub.subscribe(new DelegateSubscriber<Integer, Integer>(sub){

            int sum = 0;

            @Override
            public void onNext(Integer i) {
                sum += i;
            }

            @Override
            public void onComplete() {
                sub.onNext(sum);
                sub.onComplete();
            }
        });
    }
}
