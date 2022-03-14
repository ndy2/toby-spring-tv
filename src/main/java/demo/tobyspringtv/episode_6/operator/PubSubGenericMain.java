package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PubSubGenericMain {

    public static void main(String[] args) {
        Iterable<Integer> iter = Stream.iterate(1, a -> a + 1).limit(5).collect(Collectors.toList());

        Publisher<Integer> pub = new IterPublisher(iter);
        Subscriber<Integer> sub = new LogSubscriber();


    }
}
