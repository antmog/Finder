package finder.util;

import finder.model.CustomRandomAccessFile;
import finder.model.CustomTab;
import finder.model.ExecutableTask;
import finder.model.TaskExecutor;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Task (for thread) show file content in TextArea.
 */
public class ShowTask extends ExecutableTask {
    private CustomRandomAccessFile cRaf;
    private CustomTab tab;

    public ShowTask(CustomTab tab, CustomRandomAccessFile cRaf, ExecutorService exec) {
        super(exec);
        this.cRaf = cRaf;
        this.tab = tab;
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
        cRaf.seek(tab.getStartLineNumber() * tab.getLineLength() + tab.getStartLineNumber());
        String line;

        for (int i = 0; i < tab.getShowLinesCount(); i++) {
            if ((line = cRaf.readLineCustom()) != null) {
                // writing content to buffer
                tab.writeToSb(line);
                tab.writeToSb(System.lineSeparator());
                // writing line numbers to buffer
                tab.getRowNumbersBuffer().append(tab.getStartLineNumber() + i);
                tab.getRowNumbersBuffer().append(System.lineSeparator());
            }
        }
        cRaf.close();
        // @after thread@ logic
        Platform.runLater(() -> {
            // operations with JavaFX elements (not allowed in thread)
            tab.setLineCount();
            tab.setShowLinesCount();
            tab.writeFromSb();
            tab.getRowNumbers().setText(tab.getRowNumbersBuffer().toString());
            if (tab.searchResult()) {
                // if search in file succeed => selecting elements in text area
                int startOfSelection = Math.toIntExact(((tab.getFindElementLine() - tab.getStartLineNumber()) * tab.getLineLength()) + tab.getIndexOfFoundTextInLine());
                tab.getTextArea().selectRange(startOfSelection, startOfSelection + tab.getSearchText().length());
                // setting search flag to initial state (false)
                tab.searchFinished();
            }
            // changing tab name back
            tab.setLoaded();
            exec.shutdown();
        });
        return null;
    }
}
