package demo.tobyspringtv.episode_1.dispatch;

/**
 * Dynamic Dispatch
 */
public class DispatchV2 {

    static abstract class Service {
        abstract void run();
    }

    static class MyService1 extends Service {
        @Override
        void run() {
            System.out.println("MyService1.run");
        }
    }

    static class MyService2 extends Service {
        @Override
        void run() {
            System.out.println("MyService2.run");
        }
    }

    public static void main(String[] args) {
        Service svc = new MyService1();
        svc.run();
    }
}
