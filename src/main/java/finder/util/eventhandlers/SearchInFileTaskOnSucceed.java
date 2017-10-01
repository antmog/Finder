package finder.util.eventhandlers;

import finder.model.FileTab;
import finder.model.OptimizedRandomAccessFile;
import finder.model.TaskExecutor;
import finder.model.WarningWindow;
import finder.util.tasks.ShowTask;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import java.io.FileNotFoundException;

public class SearchInFileTaskOnSucceed implements EventHandler<WorkerStateEvent> {
    private FileTab tab;
    private OptimizedRandomAccessFile oRaf;

    public SearchInFileTaskOnSucceed(FileTab tab, OptimizedRandomAccessFile oRaf){
        this.tab = tab;
        this.oRaf = oRaf;
    }

    @Override
    public synchronized void handle(WorkerStateEvent event) {
        if (tab.searchResult()) {
            // if search is successful -> reloading content
            try {
                oRaf = new OptimizedRandomAccessFile(tab.getFile(), "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ShowTask showTask = new ShowTask(tab, oRaf);
            showTask.setOnSucceeded(new ShowTaskOnSucceed(tab));
            TaskExecutor.getInstance().executeTask(showTask);
        } else {
            tab.setLoaded();
            new WarningWindow(tab.getTab().getText() + ": Nothing found. Sorry.");
        }
    }
}
