package demo.tobyspringtv.episode_5.pubsub;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow;

public class CustomPublisher implements Flow.Publisher<Integer> {

    Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);
    final Iterator<Integer> it =iter.iterator();

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new Flow.Subscription() {
            @Override
            public void request(long n) {
                try{
                    while(n -- > 0){
                        if(it.hasNext()){
                            subscriber.onNext(it.next());
                        }else{
                            subscriber.onComplete();
                            break;
                        }
                    }
                }catch (RuntimeException e){
                    subscriber.onError(e);
                }
            }

            @Override
            public void cancel() {
            }
        });
    }
}
