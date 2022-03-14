package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DelegateSubscriber<T, R> implements Subscriber<T> {

    private final Subscriber sub;

    public DelegateSubscriber(Subscriber<? super R> sub) {
        this.sub = sub;
    }

    @Override
    public void onSubscribe(Subscription s) {
        sub.onSubscribe(s);
    }

    @Override
    public void onNext(T t) {
      sub.onNext(t); //기본 - 그냥 연결만 하기
    }

    @Override
    public void onError(Throwable throwable) {
        sub.onError(throwable);
    }

    @Override
    public void onComplete() {
        sub.onComplete();
    }
}
