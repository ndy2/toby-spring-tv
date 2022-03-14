package demo.tobyspringtv.episode_6.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactorController {

    @RequestMapping("/hello")
    public Publisher<String> hello (String name){
        return new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> sub) {
                sub.onSubscribe(new Subscription() {
                    @Override
                    public void request(long l) {
                        sub.onNext("Hello " + name);
                        sub.onComplete();
                    }

                    @Override
                    public void cancel() {

                    }
                });
            }
        };
    }
}
