package finder.util.eventhandlers;

import finder.model.FileTab;
import finder.model.OptimizedRandomAccessFile;
import finder.model.TaskExecutor;
import finder.util.tasks.ShowTask;
import finder.view.searchblock.searchresult.TabTemplateController;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.FileNotFoundException;

/**
 * Handler for ShowTask success.
 */
public class ShowTaskOnSucceed implements EventHandler<WorkerStateEvent> {
    private FileTab tab;
    private ShowTask showTask;
    private OptimizedRandomAccessFile oRaf;
    private TabTemplateController tabTemplateController;

    public ShowTaskOnSucceed(FileTab tab, ShowTask showTask, OptimizedRandomAccessFile oRaf, TabTemplateController tabTemplateController) {
        this.tab = tab;
        this.showTask = showTask;
        this.oRaf = oRaf;
        this.tabTemplateController = tabTemplateController;
    }

    @Override
    public void handle(WorkerStateEvent event) {
        // operations with JavaFX elements (not allowed in thread)
        if (showTask.getValue()) {
            // loading values to scene elements (logic inside of FileTab(tab) instance)
            tab.setLineCount();
            tab.setShowLinesCount();
            tab.writeFromTabStringBuffer();
            tab.getRowNumbers().setText(tab.getRowNumbersBuffer().toString());
            // if search in file succeed => selecting elements in text area
            if (tab.searchResult()) {
                String textAreaText = tab.getTextArea().getText();
                int nextIndex = 0;
                int currentIndex;
                int line = (int) (tab.getFindElementLine() - tab.getStartLineNumber());

                int i = 0;
                while ((currentIndex = textAreaText.indexOf("\n", nextIndex)) != -1) {
                    if (line == i) {
                        tab.getTextArea().selectRange(textAreaText.indexOf(tab.getSearchText(), nextIndex),
                                textAreaText.indexOf(tab.getSearchText(), nextIndex) + tab.getSearchText().length());
                    }
                    i++;
                    nextIndex = currentIndex + "\n".length();
                }
                // setting search flag to initial state (false)
                tab.searchFinished();
            }
            // changing tab name back
            tab.setLoaded();
        } else {
            showTask = new ShowTask(tab, oRaf, tabTemplateController);
            showTask.setOnSucceeded(new ShowTaskOnSucceed(tab, showTask, oRaf, tabTemplateController));
            TaskExecutor.getInstance().executeTask(showTask);
        }
    }
}
