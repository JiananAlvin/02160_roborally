package utils.multithread;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ProgrammingTimer {

    public static Future<Integer> calculateTime() {
        ThreadPoolExecutor executor = ThreadPoolInstance.getInstance();
        return executor.submit(() -> {
            return 0;
        });
    }
}
