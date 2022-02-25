package demo.tobyspringtv.episode_1.dispatch;

/**
 * Static Dispatch
 */
public class DispatchV1 {

    static class Service {
        void run(int number) {
            System.out.println("Service.run("+number+")");
        }

        void run(String msg){
            System.out.println("Service.run("+msg+")");
        }
    }

    public static void main(String[] args) {
        new Service().run(1);
        new Service().run("Dispatch");
    }
}
