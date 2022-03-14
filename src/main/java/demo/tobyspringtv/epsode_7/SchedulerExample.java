package demo.tobyspringtv.epsode_7;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 토비의 봄 TV 7화 - 리액티브 스프링 웹 개발 (3)
 * Reactive Streams - Schedulers
 */
@Slf4j
public class SchedulerExample {

    public static void main(String[] args) {

        Publisher<Integer> pub = sub ->
            sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long l) {
                        sub.onNext(1);
                        sub.onNext(2);
                        sub.onNext(3);
                        sub.onNext(4);
                        sub.onNext(5);
                        sub.onComplete();
                    }

                    @Override
                    public void cancel() {
                    }
                }
            );
        Publisher<Integer> subOnPub = s-> {
            ExecutorService es = Executors.newSingleThreadExecutor(
                    new CustomizableThreadFactory("subOn-")
            );
            es.execute(() -> pub.subscribe(s));
            es.shutdown();
        };

        Subscriber<Integer> sub = new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                log.debug("onSubscribe");
                subscription.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                log.debug("onNext : {}", integer);
            }

            @Override
            public void onError(Throwable throwable) {
                log.debug("onError : ", throwable);

            }

            @Override
            public void onComplete() {
                log.debug("onComplete");
            }
        };

//        subOnPub.subscribe(sub);
        //스케쥴러! - publishOn, subscribeOn


        Publisher<Integer> pubOnPub = s -> {
          subOnPub.subscribe(new Subscriber<Integer>() {
              ExecutorService es = Executors.newSingleThreadExecutor(
                      new CustomizableThreadFactory("pubOn-")
              );

              @Override
              public void onSubscribe(Subscription subscription) {
                  sub.onSubscribe(subscription);
              }

              @Override
              public void onNext(Integer integer) {
                  es.execute(()-> sub.onNext(integer));
              }

              @Override
              public void onError(Throwable throwable) {
                  es.execute(()-> sub.onError(throwable));
                  es.shutdown();
              }

              @Override
              public void onComplete() {
                  es.execute(()-> sub.onComplete());
                  es.shutdown();
              }
          });
        };

        pubOnPub.subscribe(sub);

        log.info("Main Exit");
    }
}
