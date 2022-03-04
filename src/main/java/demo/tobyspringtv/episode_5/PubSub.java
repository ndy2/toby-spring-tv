package demo.tobyspringtv.episode_5;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import static java.util.concurrent.Flow.*;

public class PubSub {
        // Publisher  <- Observable
        // Subscriber <- Observer
    public static void main(String[] args) {

        Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);

        /* onSubscribe onNext* (onError | onComplete)? */
        Publisher<Integer> p = new Publisher<Integer>() {

            final Iterator<Integer> it =iter.iterator();

            @Override
            public void subscribe(Subscriber<? super Integer> subscriber) {
                subscriber.onSubscribe(new Subscription() {
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
        };

        Subscriber<Integer> s = new Subscriber<Integer>() {

            Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                System.out.println("onSubscribe");
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("onNext " + item);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("onError");
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        };

        p.subscribe(s);
    }
}
