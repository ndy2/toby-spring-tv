package demo.tobyspringtv.episode_7;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class IntervalExample {

    public static void main(String[] args) {

        Publisher<Integer> pub = sub -> {
            sub.onSubscribe(new Subscription() {

                int no = 0;
                boolean canceled;

                @Override
                public void request(long n) {
                    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                    exec.scheduleAtFixedRate(()
                            -> {
                                if(canceled){
                                    exec.shutdown();
                                    return;
                                }
                                sub.onNext(no++);
                            },
                            0, 300, TimeUnit.MILLISECONDS);
                }

                @Override
                public void cancel() {
                    canceled = true;
                }
            });
        };

        //Operator 로 Scheduling!
        Publisher<Integer> takePub = sub-> {
          pub.subscribe(new Subscriber<Integer>() {

              int count = 0;
              Subscription subscription;

              @Override
              public void onSubscribe(Subscription s) {
                  sub.onSubscribe(s);
                  subscription = s;
              }

              @Override
              public void onNext(Integer integer) {
                  sub.onNext(integer);
                  if(++count >= 10){
                      subscription.cancel();
                  }
              }

              @Override
              public void onError(Throwable throwable) {
                sub.onError(throwable);
              }

              @Override
              public void onComplete() {
                sub.onComplete();
              }
          });
        };


        takePub.subscribe(new Subscriber<Integer>() {
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
        });
    }
}
