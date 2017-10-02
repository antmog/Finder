package finder.util.eventhandlers;

import finder.model.FileTab;
import finder.model.OptimizedRandomAccessFile;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

public class ShowTaskOnSucceed implements EventHandler<WorkerStateEvent> {
    private FileTab tab;

    public ShowTaskOnSucceed(FileTab tab) {
        this.tab = tab;
    }

    @Override
    public void handle(WorkerStateEvent event) {
        // operations with JavaFX elements (not allowed in thread)
        tab.setLineCount();
        tab.setShowLinesCount();
        tab.writeFromTabStringBuffer();
        tab.getRowNumbers().setText(tab.getRowNumbersBuffer().toString());
        if (tab.searchResult()) {
            // if search in file succeed => selecting elements in text area
            System.out.println("Findeleline: "+ tab.getFindElementLine());
            System.out.println("StartLineNum: "+ tab.getStartLineNumber());
            System.out.println("Index: " + tab.getIndexOfFoundTextInLine());
            int startOfSelection = Math.toIntExact(((tab.getFindElementLine() - tab.getStartLineNumber()) *
                    tab.getLineLength()));// + tab.getIndexOfFoundTextInLine()
            System.out.println("Target: " + startOfSelection);
            tab.getTextArea().selectRange(
                    startOfSelection, startOfSelection + tab.getSearchText().length());
            // setting search flag to initial state (false)
            tab.searchFinished();
        }
        // changing tab name back
        tab.setLoaded();
    }
}
