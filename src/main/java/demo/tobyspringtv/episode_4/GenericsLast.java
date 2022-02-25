package demo.tobyspringtv.episode_4;

import java.util.*;


public class GenericsLast {

    static class A{}
    static class B extends A{}



    public static void main(String[] args) {
        List<B> listB = new ArrayList<>();
        
//        안됨
//        List<A> listA = listB;
        
        //이건 됨
        List<? extends A> listA = listB;
        List<? super B> listBB = listB;

        //캡쳐??? 이건 안됨
        //와일드 카드의 유용성과 제약성 - 다음시간에~
//        listA.add(new A());
//        listA.add(new B());
        listA.add(null);
    }
}
