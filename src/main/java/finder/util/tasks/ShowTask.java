package finder.util.tasks;

import finder.model.OptimizedRandomAccessFile;
import finder.model.FileTab;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;


/**
 * Task (for thread) show file content in TextArea.
 */
public class ShowTask extends Task<Void> {
    private OptimizedRandomAccessFile oRaf;
    private FileTab tab;

    public ShowTask(FileTab tab, OptimizedRandomAccessFile oRaf) {
        this.oRaf = oRaf;
        this.tab = tab;
    }
    public FileTab getTab() {
        return tab;
    }

    public Void call() throws IOException {
        tab.clearRowNumbersBuffer();
        // if count of displayed lines set is more than file length (count of lines).
        if (tab.getLineCount() < tab.getShowLinesCount()) {
            // set show lines count to default
            tab.setShowLinesCountDefault();
            // and if default is till more - set to file length
            if (tab.getLineCount() < tab.getShowLinesCount()) {
                tab.setShowLinesCount(tab.getLineCount());
            }
            tab.setStartLineNumber(0);
        }

        // correcting startLine of displayed part of file
        if (tab.getLineCount() < tab.getStartLineNumber() + tab.getShowLinesCount()) {
            tab.setStartLineNumber(tab.getLineCount() - tab.getShowLinesCount());
        }
        // setting position
        oRaf.seek(tab.getStartLineNumber() * tab.getLineLength() + tab.getStartLineNumber());
        String line;

        for (int i = 0; i < tab.getShowLinesCount(); i++) {
            if ((line = oRaf.readLineCustom()) != null) {
                // writing content to buffer
                tab.writeToTabStringBuffer(line);
                tab.writeToTabStringBuffer(System.lineSeparator());
                // writing line numbers to buffer
                tab.getRowNumbersBuffer().append(tab.getStartLineNumber() + i);
                tab.getRowNumbersBuffer().append(System.lineSeparator());
            }
        }
        oRaf.close();
        // @after thread@ logic
        Platform.runLater(() -> {
            // moved to onSucceed
        });
        return null;
    }
}
