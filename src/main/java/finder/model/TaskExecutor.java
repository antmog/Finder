package finder.model;

import javafx.concurrent.Task;

import java.beans.EventHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private ExecutorService exec;
    private static volatile TaskExecutor instance;

    public static TaskExecutor getInstance() {
        TaskExecutor localInstance = instance;
        if (localInstance == null) {
            synchronized (TaskExecutor.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TaskExecutor();
                }
            }
        }
        return localInstance;
    }
    public ExecutorService getExec(){
        return exec;

    }
    private TaskExecutor() {
        exec = Executors.newCachedThreadPool();
    }

    public void executeTask(Task task) {
        exec.execute(task);
    }

}

