package finder.util.tasks;

import finder.model.OptimizedRandomAccessFile;
import finder.model.FileTab;
import finder.model.TaskExecutor;
import finder.view.searchblock.searchresult.TabTemplateController;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/**
 * Task (for thread) show file content in TextArea.
 */
public class ShowTask extends Task<Boolean> {
    private OptimizedRandomAccessFile oRaf;
    private FileTab tab;
    private TabTemplateController tabTemplateController;

    public ShowTask(FileTab tab, OptimizedRandomAccessFile oRaf, TabTemplateController tabTemplateController) {
        this.oRaf = oRaf;
        this.tab = tab;
        this.tabTemplateController = tabTemplateController;
    }

    public Boolean call() throws IOException, InterruptedException {
        tab.clearRowNumbersBuffer();
        tab.clearTabStringBuffer();
        String line;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        TaskExecutor.getInstance().executeTask(new CheckShowParamsTask(tab,countDownLatch));
        System.out.println("Wait pls");
        countDownLatch.await();
        System.out.println("finished indexer");
        oRaf.seek(tab.getLinePos(tab.getStartLineNumber()));

        for (int i = 0; i < tab.getShowLinesCount(); i++) {
            if ((line = oRaf.readLineCustom()) != null) {
                // writing content to buffer
                tab.writeToTabStringBuffer(line);
                tab.writeToTabStringBuffer(System.lineSeparator());
                // writing line numbers to buffer
                tab.getRowNumbersBuffer().append(tab.getStartLineNumber() + i);
                tab.getRowNumbersBuffer().append(System.lineSeparator());
            }
            else{
                // count of lines in file (0..N) -> (1..N)
                // if count of displayed lines set is more than file length (count of lines).
                System.out.println("eto konec.............................................................................................");
                return false;
            }
        }
        oRaf.close();
        // @after thread@ logic
        Platform.runLater(() -> {
            // moved to onSucceed
        });
        return true;
    }
}
