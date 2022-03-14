package demo.tobyspringtv.completablefuture.brunch.code;

import java.util.concurrent.Future;

/**
 * 기능을 제공하는 곳에서 동기, 비동기에 대한 개념을 포함하고 있음
 * 블로킹/ 논블로킹은 기능을 활용하는 곳 - 클라이언트에서 선택할 것
 */
public interface CoffeeUseCase {

    int getPrice(String name);                              //Sync(동기)
    Future<Integer> getPriceAsync(String name);             //Async(비동기)
    Future<Integer> getDiscountPriceAsync(Integer price);   //Async(비동기)
}
