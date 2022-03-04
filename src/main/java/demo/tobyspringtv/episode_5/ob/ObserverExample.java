package demo.tobyspringtv.episode_5.ob;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObserverExample {

    static class IntegerObservable extends Observable implements Runnable{

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                setChanged();
                notifyObservers(i); //push
            }
        }
    }

    @SuppressWarnings("Deprecation")
    public static void main(String[] args) {
        // Source -> (Event/Data Source) -> Observer
        Observer ob = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println(Thread.currentThread().getName()+"  arg = " + arg);
            }
        };

        IntegerObservable io = new IntegerObservable();
        io.addObserver(ob);

//        io.run();

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);
        System.out.println(Thread.currentThread().getName()+" EXIT");
        es.shutdown();
    }
}
