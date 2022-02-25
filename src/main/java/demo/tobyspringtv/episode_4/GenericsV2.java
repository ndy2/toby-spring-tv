package demo.tobyspringtv.episode_4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericsV2 {

    public static void main(String[] args) {

        //Raw Type - 제네릭 이면서 타입 파라미터를 지정하지 않은 경우
        List list = new ArrayList();

        List<Integer> ints = Arrays.asList(1, 2, 3);
        List rawInts = ints;

        @SuppressWarnings("unchecked")
        List<Integer> ints2 = rawInts;    //Warning - unsafe casting/ unchecked conversion

        @SuppressWarnings("unchecked")
        List<String> strs = rawInts;      //Warning - unsafe casting/ unchecked conversion
        String s = strs.get(0);           //ClassCastException!
        System.out.println("s = " + s);

        //호환성을 위해 rawType conversion 을 지원
    }

}
