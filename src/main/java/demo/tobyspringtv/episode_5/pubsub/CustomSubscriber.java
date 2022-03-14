package demo.tobyspringtv.episode_5.pubsub;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

public class CustomSubscriber<T> implements Flow.Subscriber<T> {

    Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
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
}
