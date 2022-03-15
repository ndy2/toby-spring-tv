package demo.tobyspringtv.episode_8.future.code;

@FunctionalInterface
public interface ExceptionCallback {

    void onError(Throwable t);
}
