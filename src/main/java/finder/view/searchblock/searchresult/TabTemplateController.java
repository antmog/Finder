package finder.view.searchblock.searchresult;

import finder.model.*;
import finder.util.eventhandlers.SearchInFileTaskOnSucceed;
import finder.util.eventhandlers.ShowTaskOnSucceed;
import finder.util.tasks.CheckShowParamsTask;
import finder.util.tasks.SearchInFileTask;
import finder.util.tasks.ShowTask;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TabTemplateController {

    private FileTab tab;
    private OptimizedRandomAccessFile oRaf;

    private int bufferSize;


    @FXML
    private TextField numberOfRow;

    @FXML
    private Label lineCount;

    @FXML
    private TextField showLinesCount;

    @FXML
    private TextArea textArea;

    @FXML
    private TextArea rowNumbers;

    @FXML
    private TextArea searchTextArea;

    public TabTemplateController(FileTab tab) {
        this.tab = tab;
        tab.setStartLineNumber(0);
        tab.setSearchPointer(0);
        tab.setFileLength(0);
        tab.setLineCount(0);
        tab.addLine(0L, 0L);
    }

    @FXML
    private void initialize() {
        tab.setElements(textArea, showLinesCount, rowNumbers, lineCount);
        searchTextArea.setText(tab.getSearchText());
        // analyze file here if needed
        showText();
    }

    /**
     * Display file content in TextArea
     */
    private synchronized void showText() {
        // setting tab name to "Loading..."
        tab.setLoading();
        try {
            oRaf = new OptimizedRandomAccessFile(tab.getFile(), "r", 8192);
            ShowTask showTask = new ShowTask(tab, oRaf,this);
            showTask.setOnSucceeded(new ShowTaskOnSucceed(tab,showTask,oRaf,this));
            TaskExecutor.getInstance().executeTask(showTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void searchForward() {
        search(SearchDirection.FORWARD);
    }

    @FXML
    private void searchBack() {
        search(SearchDirection.BACK);
    }

    /**
     * Searching text in file.
     */
    private synchronized void search(SearchDirection direction) {
        if (tab.isFree()) {
            tab.setDirection(direction);
            searchTextArea.setText(tab.getSearchText());
            // setting tab name to "Loading..."
            tab.setLoading();
            try {
                if (tab.getDirection() == SearchDirection.BACK) {
                    // buffer length = line length for BACK search
                    // cause readLineCustom() uses pointer parameter deep inside its class and BACK search logic
                    // in this app realised on top levels so when buffer = line length , buffer in readLineCustom()
                    // refreshes after 1 iteration of readLineCustom().
                    // because of that method works much slower
                    //oRaf = new OptimizedRandomAccessFile(tab.getFile(), "r", Math.toIntExact(tab.getLineLength()));
                    oRaf = new OptimizedRandomAccessFile(tab.getFile(), "r", 1);
                } else {
                    //oRaf = new OptimizedRandomAccessFile(tab.getFile(), "r", bufferSize);
                    oRaf = new OptimizedRandomAccessFile(tab.getFile(), "r", 8192);
                }
                SearchInFileTask searchInFileTask = new SearchInFileTask(tab, oRaf);
                searchInFileTask.setOnSucceeded(new SearchInFileTaskOnSucceed(tab, oRaf,this));
                TaskExecutor.getInstance().executeTask(searchInFileTask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new WarningWindow("Cant operate with text while information is loading.");
        }
    }


    @FXML
    private void goTo() {
        if (tab.isFree()) {
            try{
                tab.setStartLineNumber(Long.parseLong(numberOfRow.getText()));
                checkShowParamsOnAction();
            }catch(NumberFormatException e){
                new WarningWindow("Wrong data format.");
            }
        } else {
            new WarningWindow("Cant operate with text while information is loading.");
        }
    }

    @FXML
    private void selectAll() {
        if (tab.isFree()) {
            tab.getTextArea().selectAll();
        } else {
            new WarningWindow("Wait for text to be loaded.");
        }
    }

    @FXML
    private void close() {
        tab.getTabPane().getTabs().remove(tab.getTab());
        try {
            if (oRaf != null) {
                oRaf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Scroll file content up.
     */
    @FXML
    private void up() {
        if (tab.isFree()) {
            tab.setShowLinesCountFromField(showLinesCount.getText());
            tab.setStartLineNumber(tab.getStartLineNumber() - tab.getShowLinesCount());
            if (tab.getStartLineNumber() < 0) {
                tab.setStartLineNumber(0);
            }
            checkShowParamsOnAction();
        } else {
            new WarningWindow("Cant operate with text while information is loading.");
        }
    }

    /**
     * Scroll file content down.
     */
    @FXML
    private void down() {
        if (tab.isFree()) {
            tab.setShowLinesCountFromField(showLinesCount.getText());
            tab.setStartLineNumber(tab.getStartLineNumber() + tab.getShowLinesCount());
            if (tab.getStartLineNumber() < 0) {
                tab.setStartLineNumber(Long.MAX_VALUE - tab.getShowLinesCount());
            }
            checkShowParamsOnAction();
        } else {
            new WarningWindow("Cant operate with text while information is loading.");
        }
    }


    @FXML
    private void changeSearchText() {
        if (tab.isFree()) {
            if (searchTextArea.getText().equals("")) {
                new WarningWindow("You have to enter search text.");
                searchTextArea.setText(tab.getSearchText());
            } else {
                tab.setSearchText(searchTextArea.getText());
            }
        } else {
            new WarningWindow("Cant operate with text while information is loading.");
        }

    }

    /**
     * Check new lines set to be shown and load from  file if needed. See CheckShowParamsTask.
     */
    private synchronized void checkShowParamsOnAction(){
        tab.setLoading();
        CheckShowParamsTask checkShowParamsTask = new CheckShowParamsTask(tab);
        checkShowParamsTask.setOnSucceeded(event -> {
            showText();
            // replace search pointer after navigation (goTo/up/down)
            tab.setSearchPointer(tab.getStartLineNumber());

        });
        TaskExecutor.getInstance().executeTask(checkShowParamsTask);
    }

}

