package finder.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    public ExecutorService createService(){
        return Executors.newCachedThreadPool();
    }
}
