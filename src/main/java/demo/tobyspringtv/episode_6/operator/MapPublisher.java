package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.function.Function;

public class MapPublisher<T,R> implements Publisher<R> {


    private final Publisher<T> pub;
    private final Function<T,R> mapper;

    public MapPublisher(Publisher<T> pub, Function<T, R> mapper) {
        this.pub = pub;
        this.mapper = mapper;
    }

    @Override
    public void subscribe(Subscriber<? super R> sub) {
        //그냥 연결만 하기
        /*pub.subscribe(sub);*/

        pub.subscribe(new DelegateSubscriber<T,R>(sub){
            @Override
            public void onNext(T t) {
                sub.onNext(mapper.apply(t)); //매퍼 퍼블리셔 - 매퍼 적용
            }
        });
    }
}
