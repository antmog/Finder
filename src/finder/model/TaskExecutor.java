package finder.model;

import java.util.concurrent.ExecutorService;

public class TaskExecutor {
    private ExecutorService exec;

    public TaskExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    public ExecutorService getExecutor() {
        return this.exec;
    }
}
