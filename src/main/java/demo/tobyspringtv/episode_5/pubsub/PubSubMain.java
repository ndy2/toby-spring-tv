package demo.tobyspringtv.episode_5.pubsub;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class PubSubMain {
        // Publisher  <- Observable
        // Subscriber <- Observer
    public static void main(String[] args) {

        /* onSubscribe onNext* (onError | onComplete)? */
        Publisher<Integer> p = new CustomPublisher();
        Subscriber<Integer> s = new CustomSubscriber<>();

        p.subscribe(s);
    }
}
