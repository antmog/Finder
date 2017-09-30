package finder.model;

import javafx.concurrent.Task;

import java.beans.EventHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private Executor exec;
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

    private TaskExecutor() {
        exec = Executors.newCachedThreadPool();
    }

    public void executeTask(Task<Void> task) {
        exec.execute(task);
    }
}

