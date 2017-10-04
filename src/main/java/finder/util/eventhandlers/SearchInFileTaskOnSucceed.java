package finder.util.eventhandlers;

import finder.model.FileTab;
import finder.model.OptimizedRandomAccessFile;
import finder.model.TaskExecutor;
import finder.model.WarningWindow;
import finder.util.tasks.ShowTask;
import finder.view.searchblock.searchresult.TabTemplateController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import java.io.FileNotFoundException;

public class SearchInFileTaskOnSucceed implements EventHandler<WorkerStateEvent> {
    private FileTab tab;
    private OptimizedRandomAccessFile oRaf;
    private TabTemplateController tabTemplateController;

    public SearchInFileTaskOnSucceed(FileTab tab, OptimizedRandomAccessFile oRaf, TabTemplateController tabTemplateController){
        this.tab = tab;
        this.oRaf = oRaf;
        this.tabTemplateController = tabTemplateController;
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
            ShowTask showTask = new ShowTask(tab, oRaf,tabTemplateController);
            showTask.setOnSucceeded(new ShowTaskOnSucceed(tab,showTask,oRaf,tabTemplateController));
            TaskExecutor.getInstance().executeTask(showTask);
        } else {
            tab.setLoaded();
            new WarningWindow(tab.getTab().getText() + ": Nothing found. Sorry.");
        }
    }
}
