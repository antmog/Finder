package finder.util.tasks;

import finder.model.*;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.IOException;

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

    @Override
    public Void call() throws IOException {
        int indexInLine;
        String line;
        Long lineNumber = tab.getSearchPointer();

        // Check if searchPointer is in loaded lines range and search in buffer (hashMap lines) if it is.
        // if found value in buffer - break => lineNumber is set to number of line, where value was found.
        if (tab.getDirection() == SearchDirection.FORWARD) {
            for (; lineNumber < tab.getLastLineKey(); lineNumber++) {
                if (tab.getLineContains(lineNumber)) {
                    break;
                }
            }
        }else{
            for (; lineNumber > 0; lineNumber--) {
                if (tab.getLineContains(lineNumber)) {
                    break;
                }
            }
        }
        // Start search from this line and start search from this line
        // if lineNumber hasn't been changed (line hasn't been found in buffer) - continue search from initial pointer
        tab.setSearchPointer(lineNumber);
        oRaf.seek(tab.getLinePos(lineNumber));

        while ((line = oRaf.readLineCustom()) != null) {
            if ((indexInLine = line.indexOf(tab.getSearchText())) != -1) {
                // set index in line pos
                tab.setIndexOfFoundTextInLine(indexInLine);
                // set line number of found element
                tab.setFindElementLine(lineNumber);
                // change search pointer
                tab.setSearchPointer(tab.getFindElementLine());
                // change search position
                tab.setSearchPosition(tab.getFindElementLine());
                // setting first displayed line as number of line of found element
                tab.setStartLineNumber(tab.getSearchPointer());
                tab.searchSucceed();
                // end search and break w/o increment pointer
                break;
            }
            // iterations of position for "back" search
            // lineNumber inc/dec according to direction
            if (tab.getDirection() == SearchDirection.BACK) {
                if (tab.getSearchPointer() == 0) {
                    tab.setSearchPointer(tab.getStartLineNumber());
                    break;
                } else {
                    tab.incSearchPointer();
                }

                long gena = tab.getLinePos(tab.getSearchPointer());
                oRaf.seek(gena);
                lineNumber--;
            } else {
                lineNumber++;
            }

        }
        // inc/dec search pointer(according to direction) after search
        if ((line != null) && (tab.getDirection() == SearchDirection.FORWARD)) {
            tab.incSearchPointer();
        }
        if ((tab.getDirection() == SearchDirection.BACK) && (tab.getSearchPointer() != 0)) {
            tab.incSearchPointer();
        }

        oRaf.close();
        Platform.runLater(() -> {
            // moved to onSucceed
        });
        return null;
    }
}
