package finder.util;

import finder.model.*;
import javafx.application.Platform;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Task (for thread): search text in file.
 */
public class SearchInFileTask extends ExecutableTask {
    private CustomRandomAccessFile cRaf;
    private CustomTab tab;
    private ExecutorService exec;

    public SearchInFileTask(CustomTab tab, CustomRandomAccessFile cRaf, ExecutorService exec) {
        super(exec);
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
                exec.execute(new Thread(new ShowTask(tab, cRaf, exec)));
            } else {
                tab.setLoaded();
                new WarningWindow(tab.getTab().getText() + ": Nothing found. Sorry.");
                exec.shutdown();
            }
        });
        return null;
    }
}
