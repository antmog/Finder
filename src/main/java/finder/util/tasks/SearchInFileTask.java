package finder.util.tasks;

import finder.model.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Task (for thread): search text in file.
 */
public class SearchInFileTask extends Task<Void> {
    private OptimizedRandomAccessFile oRaf;
    private FileTab tab;

    public SearchInFileTask(FileTab tab, OptimizedRandomAccessFile oRaf) {
        this.oRaf = oRaf;
        this.tab = tab;
    }
    public FileTab getTab() {
        return tab;
    }

    @Override
    public Void call() throws IOException {
        int indexInLine;
        // position = numberOfLine(searchPointer)*(line length+1) : as long as line separator takes 1 more symbol ...
        oRaf.seek(tab.getSearchPointer() * tab.getLineLength() + tab.getSearchPointer());
        String line;
        while ((line = oRaf.readLineCustom()) != null) {
            //if(line.contains(tab.getSearchText())){
            if ((indexInLine = line.indexOf(tab.getSearchText())) != -1) {
                // setting index in line
                tab.setIndexOfFoundTextInLine(indexInLine);
                // getting number of line of found element from pointer
                tab.setFindElementLine((oRaf.getActualPos() / (tab.getLineLength() + 1)) - 1);
                tab.setSearchPointer(tab.getFindElementLine());
                tab.setSearchPosition(tab.getFindElementLine());
                // setting first displayed line as number of line of found element
                tab.setStartLineNumber(tab.getSearchPointer());
                tab.searchSucceed();
                // checking limit of lines
                if ((tab.getDirection() == SearchDirection.FORWARD) && (tab.getSearchPointer() < tab.getLineCount() - 1)) {
                    tab.incSearchPointer();
                }
                if ((tab.getDirection() == SearchDirection.BACK) && (tab.getSearchPointer() != 0)) {
                    tab.incSearchPointer();
                }
                break;
            }
            // manually setting new pointer for BACK direction of search in file
            if (tab.getDirection() == SearchDirection.BACK) {
                if (tab.getSearchPointer() == 0) {
                    break;
                } else {
                    tab.incSearchPointer();
                }
                oRaf.seek(tab.getSearchPointer() * tab.getLineLength() + tab.getSearchPointer());
            }

        }
        oRaf.close();
        Platform.runLater(() -> {
            // moved to onSucceed
        });
        return null;
    }
}
