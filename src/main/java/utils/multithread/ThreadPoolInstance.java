package utils.multithread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Class ThreadPoolInstance is used to initialize the instance of the thread pool executor
 */
public class ThreadPoolInstance {

    private static ThreadPoolExecutor poolExecutorInstance;

    private ThreadPoolInstance() {

    }

    /**
     * @return Return the instance of the thread pool. If there is not an instance now, create a new one with max thread number 10. Otherwise return current pool executor.
     */
    public static ThreadPoolExecutor getInstance() {
        if (ThreadPoolInstance.poolExecutorInstance == null)
            ThreadPoolInstance.poolExecutorInstance = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        return ThreadPoolInstance.getInstance();
    }

}
