package demo.tobyspringtv.episode_2;

public class TypeTokenV1 {
    static <T> T create (Class<T> clazz) throws Exception {
        return clazz.newInstance();
    }

    static class Generic<T> {
        T value;    //타입 파라미터
        void set(T t){}
        T get() {return null;}
    }

    public static void main(String[] args) throws Exception {
        String string = create(String.class);
        System.out.println("string.getClass() = " + string.getClass());

//        Integer integer = create(Integer.class); - Default 생성자가 없는 Integer -> Exception
//        System.out.println("integer.getClass() = " + integer.getClass());

        Generic<String> s = new Generic<>();
        s.value = "String";

        Generic<Integer> i = new Generic<>();
        i.value = 1;
        i.set(10);

    }
}
