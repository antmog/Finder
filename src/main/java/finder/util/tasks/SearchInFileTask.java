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

        /*
        Long position;
        position = lineNumber;
        if(tab.getLinePos(position)==null){
            TaskExecutor.getInstance().executeTask
                    (new GetDataForTab(tab,oRaf,lineNumber));
        }*/
        System.out.println("start search");
        System.out.println(tab.getLastLineKey());
        System.out.println(tab.getLines().toString());
        if (tab.getDirection() == SearchDirection.FORWARD) {
            for (; lineNumber < tab.getLastLineKey(); lineNumber++) {
                System.out.println("I: " + lineNumber);
                if (tab.getLineContains(lineNumber)) {
                    System.out.println("found by indexed items");
                    break;
                }
            }
        }else{
            for (; lineNumber > 0; lineNumber--) {
                System.out.println("I: " + lineNumber);
                if (tab.getLineContains(lineNumber)) {
                    System.out.println("found by indexed items");
                    break;
                }
            }
            System.out.println("lineNumber: " + lineNumber);
        }


        System.out.println("HOBA BLET");
        System.out.println("so check from: " + lineNumber);
        tab.setSearchPointer(lineNumber);
        oRaf.seek(tab.getLinePos(lineNumber));
        while ((line = oRaf.readLineCustom()) != null) {
            if ((indexInLine = line.indexOf(tab.getSearchText())) != -1) {

                /*if(lineNumber == tab.getFindElementLine()){
                    tab.setIndexOfFoundTextInLine(indexInLine,true);
                    if(tab.getIndexOfFoundTextInLine().getEntryNumber() > tab.getIndexOfFoundTextInLine().entryAmount()){

                    }
                }else{

                }*/
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
                // checking limit of lines
                break;
            }
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
        if ((line != null) && (tab.getDirection() == SearchDirection.FORWARD)) {
            tab.incSearchPointer();
        }
        if ((tab.getDirection() == SearchDirection.BACK) && (tab.getSearchPointer() != 0)) {
            tab.incSearchPointer();
        }

      /*  // position = numberOfLine(searchPointer)*(line length+1) : as long as line separator takes 1 more symbol ...
        System.out.println("POS: "+ tab.getSearchPointer() * tab.getLineLength() );
        System.out.println("SearchPointer: "+ tab.getSearchPointer() );
        oRaf.seek(tab.getSearchPointer() * tab.getLineLength());
        line = null;
        while ((line = oRaf.readLineCustom()) != null) {
            //if(line.contains(tab.getSearchText())){
            if ((indexInLine = line.indexOf(tab.getSearchText())) != -1) {
                // setting index in line
                System.out.println("Index in search: " + indexInLine);
                tab.setIndexOfFoundTextInLine(indexInLine);
                // getting number of line of found element from pointer
                System.out.println("Actual: " + oRaf.getActualPos());
                tab.setFindElementLine((oRaf.getActualPos()+System.lineSeparator().length()) / tab.getLineLength() - 1);
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
                oRaf.seek(tab.getSearchPointer() * tab.getLineLength() +1);
                System.out.println("POsition: "+(tab.getSearchPointer() * tab.getLineLength()+1) );
            }

        }*/
        if (!tab.searchResult()) {
            System.out.println(lineNumber);
            System.out.println(tab.getSearchPointer());
            System.out.println(tab.getSearchPosition());
        }
        oRaf.close();
        Platform.runLater(() -> {
            // moved to onSucceed
        });
        return null;
    }
}
