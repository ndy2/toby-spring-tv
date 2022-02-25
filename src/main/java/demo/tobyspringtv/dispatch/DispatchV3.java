package demo.tobyspringtv.dispatch;

import java.util.Arrays;
import java.util.List;

/**
 * Double Dispatch 적용
 */
public class DispatchV3 {

    interface Post {
        void postOn(SNS sns);       //디스패치 두번
    }
    static class Text implements Post{
        public void postOn(SNS sns) {
            sns.post(this);


        }
    }
    static class Picture implements Post{
        public void postOn(SNS sns) {
            sns.post(this);
        }
    }

    interface SNS {
        void post(Text text);
        void post(Picture picture);
    }
    static class Facebook implements SNS{
        public void post(Text text) {
            System.out.println("text -> Facebook");
        }

        public void post(Picture picture) {
            System.out.println("picture -> Facebook");
        }
    }
    static class Twitter implements SNS{
        public void post(Text text) {
            System.out.println("text -> Twitter");
        }

        public void post(Picture picture) {
            System.out.println("picture -> Twitter");
        }
    }

    public static void main(String[] args) {
        List<Post> posts = Arrays.asList(new Text(), new Picture());
        List<SNS> sns = Arrays.asList(new Facebook(), new Twitter());

        posts.forEach(p -> sns.forEach(p::postOn)); //디스패치 한번
    }
}
