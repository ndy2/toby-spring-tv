package demo.tobyspringtv.episode_4;

import java.util.Arrays;
import java.util.List;

public class GenericsTvsQ {

    //도대체 무슨 차이 일까
    static void method1 (List<? extends Comparable<?>> list){};
    static <T extends Comparable<T>> void method2 (List<T> list){};


    //얘내는 또 무슨 차이 일까?
    static void printList(List<Object> list){
        list.forEach(System.out::println);
    }

    static void printList2(List<?> list){
        list.forEach(System.out::println);
    }

    //개념은 비슷하다.
    //? : wildcard - 모른다, 관심 없다.
    //T : type parameter - 지금은 모르는데, 정해지면 알고 사용 할꺼임

    public static void main(String[] args) {
//        아래 두개는 같다.
        List<?> list1;
        List<? extends Object> list2;

        //둘다 가능
        printList(Arrays.asList(1,2,3));
        printList2(Arrays.asList(1,2,3));

        List<Integer> list3 = Arrays.asList(1,2,3);
//        printList(list3); //안된다 띠용???
        printList2(list3);  //된다!
    }
}
