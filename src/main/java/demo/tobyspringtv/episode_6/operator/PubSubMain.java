package demo.tobyspringtv.episode_6.operator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TOBY TV 6화 - 스프링 리액티브 웹 개발 (2) - Operators
 *
 * Reactive Streams - Review/ Operator
 *
 * Publisher -> Data -> Subscriber
 *
 * 퍼블리셔가 중간에 작업(Operator)을 걸어 보자!
 * Publisher -> [Data1] -> Operator1 -> [Data2] -> Operator2 -> [Data3] -> Subscriber
 *
 *
 * pub -> [Data1] -> mapPub -> [Data2] ->logSub
 *
 */
public class PubSubMain {

    public static void main(String[] args) {

        Iterable<Integer> iter = Stream.iterate(1, a -> a + 1).limit(5).collect(Collectors.toList());

        Publisher<Integer> pub = new IterPublisher(iter);
        Subscriber<Object> sub = new LogSubscriber<>();

//        pub.subscribe(sub); //Review

        /* Operator : 10배 */
        Publisher<Integer> mapPub = new MapPublisher<>(pub, s -> s * 10);
//        mapPub.subscribe(sub);


        /* Operator : mapPub 두번!! */
        Publisher<Integer> map2Pub = new MapPublisher<>(mapPub, s-> s-1);
//        map2Pub.subscribe(sub);

        /* Operator : 합계를 계산해서 Pub 으로 리턴 */
        Publisher<Integer> sumPub = new SumPublisher(map2Pub);
//        sumPub.subscribe(sub);

        /* Operator : Reduce ! */
        Publisher<Integer> reducePub = new ReducePub<>(map2Pub, 0 , Integer::sum);
//        reducePub.subscribe(sub);

        Publisher<String> stringMapPub = new MapPublisher<>(pub, s -> "[" + s + "]");
//        stringMapPub.subscribe(sub);

        Publisher<String> concatPub = new ReducePub<>(stringMapPub, "", (a,b) -> a+"/"+b);
//        concatPub.subscribe(sub);

        Publisher<String> concatIntPub = new ReducePub<>(mapPub, "", (a, b) -> a + "/" + b);
//        concatIntPub.subscribe(sub);

        Publisher<StringBuilder> psb = new ReducePub<>(mapPub, new StringBuilder(), (a,b) -> a.append(',').append(b));
        psb.subscribe(sub);
    }
}
