package demo.tobyspringtv.episode_6.operator;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
public class LogSubscriber<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription subscription) {
        log.debug("onSubscribe");
        subscription.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        log.debug("onNext : {}", t);
    }

    @Override
    public void onError(Throwable throwable) {
        log.debug("onError : ", throwable);
    }

    @Override
    public void onComplete() {
        log.debug("onComplete");
    }
}
