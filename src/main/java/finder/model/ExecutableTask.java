package finder.model;
import javafx.concurrent.Task;

import java.util.concurrent.ExecutorService;

/**
 * Created by antmog on 27.09.2017.
 */
public abstract class ExecutableTask extends Task<Void> {
    protected ExecutorService exec;
    public ExecutableTask(ExecutorService exec){
        this.exec = exec;
    }
}
