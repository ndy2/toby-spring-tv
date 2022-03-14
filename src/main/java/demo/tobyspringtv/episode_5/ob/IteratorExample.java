package demo.tobyspringtv.episode_5.ob;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorExample {

    // Iterable <-----> Observable 쌍대성 (duality)
    // Pull     <-----> Push
    public static void main(String[] args) {
        //Iterable - 순회할 수 있는!
        /*Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);*/
        /*for (Integer integer : iter) {  //for-each
            System.out.println("integer = " + integer);
        }*/



        Iterator<Integer> iterator = new Iterator<>() {
            int i = 0;
            final static int MAX = 10;

            @Override
            public boolean hasNext() {
                return i<MAX;
            }

            @Override
            public Integer next() {
                return ++i;
            }
        };

        //Iterable의 SAM인 iterator 구현
        Iterable<Integer> iter2 = () -> iterator;
        for (Integer integer : iter2) {
            System.out.println("integer = " + integer);
        }

        for(; iterator.hasNext(); ){
            System.out.println("it.next() = " + iterator.next());
        }

        while (iterator.hasNext()) {
            System.out.println("it.next() = " + iterator.next()); //pull
        }
    }
}
