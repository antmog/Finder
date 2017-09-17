package finder.util;

import finder.model.*;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Task (for thread): search text in file.
 */
public class SearchTask extends Task<Void> {
    private CustomRandomAccessFile cRaf;
    private CustomTab tab;
    private TaskExecutor exec;

    public SearchTask(CustomTab tab, CustomRandomAccessFile cRaf, TaskExecutor exec) {
        this.cRaf = cRaf;
        this.tab = tab;
        this.exec = exec;
    }

    @Override
    public Void call() throws IOException {
        int indexInLine;
        // position = numberOfLine(searchPointer)*(line length+1) : as long as line separator takes 1 more symbol ...
        cRaf.seek(tab.getSearchPointer() * tab.getLineLength() + tab.getSearchPointer());
        String line;
        while ((line = cRaf.readLineCustom()) != null) {
            //if(line.contains(tab.getSearchText())){
            if ((indexInLine = line.indexOf(tab.getSearchText())) != -1) {
                // setting index in line
                tab.setIndexOfFoundTextInLine(indexInLine);
                // getting number of line of found element from pointer
                tab.setFindElementLine((cRaf.getActualPos() / (tab.getLineLength() + 1)) - 1);
                tab.setSearchPointer(tab.getFindElementLine());
                tab.setSearchPosition(tab.getFindElementLine());
                // setting first displayed line as number of line of found element
                tab.setStartLineNumber(tab.getSearchPointer());
                tab.searchSucceed();
                // cheking limit of lines
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
                cRaf.seek(tab.getSearchPointer() * tab.getLineLength() + tab.getSearchPointer());
            }

        }
        cRaf.close();
        Platform.runLater(() -> {
            if (tab.searchResult()) {
                // if search is successful -> reloading content
                try {
                    cRaf = new CustomRandomAccessFile(tab.getFile(), "r");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                exec.getExecutor().execute(new Thread(new ShowTask(tab, cRaf)));
            } else {
                tab.setLoaded();
                new WarningWindow(tab.getTab().getText() + ": Nothing found. Sorry.");
            }
        });
        return null;
    }
}
