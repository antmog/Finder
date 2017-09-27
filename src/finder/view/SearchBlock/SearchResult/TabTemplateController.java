package finder.view.SearchBlock.SearchResult;

import finder.model.*;
import finder.util.SearchInFileTask;
import finder.util.ShowTask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TabTemplateController {

    private CustomTab tab;
    private TaskExecutor taskExecutor = new TaskExecutor();
    private CustomRandomAccessFile cRaf;
    private CustomRandomAccessFile cRafShow;

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

    public TabTemplateController(CustomTab tab) {
        this.tab = tab;
        tab.setStartLineNumber(0);
        tab.setSearchPointer(0);
    }

    @FXML
    private void initialize() {
        tab.setElements(textArea, showLinesCount, rowNumbers, lineCount);
        searchTextArea.setText(tab.getSearchText());
        // analyzing file
        try (CustomRandomAccessFile cRaf = new CustomRandomAccessFile(tab.getFile(), "r")) {
            cRaf.seek(0);
            tab.setLineLength(cRaf.readLine().length() + 1);
            tab.setLineCount(cRaf.length() / (tab.getLineLength() + 1));
            // calculating buffer size according to line length
            bufferSize = (8192 / (Math.toIntExact(tab.getLineLength()) + 1)) * Math.toIntExact(tab.getLineLength() + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showText1();
    }

    /**
     * Display file content in TextArea
     */
    private void showText1() {
        // setting tab name to "Loading..."
        tab.setLoading();
        try {
            cRafShow = new CustomRandomAccessFile(tab.getFile(), "r", bufferSize);
            ExecutorService exec = taskExecutor.createService();
            exec.execute(new Thread(new ShowTask(tab, cRafShow, exec)));
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
    private void search(SearchDirection direction) {
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
                    cRaf = new CustomRandomAccessFile(tab.getFile(), "r", Math.toIntExact(tab.getLineLength() + 1));
                } else {
                    cRaf = new CustomRandomAccessFile(tab.getFile(), "r", bufferSize);
                }
                ExecutorService exec = taskExecutor.createService();
                exec.execute(new Thread(new SearchInFileTask(tab, cRaf, exec)));
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
            tab.setStartLineNumber(Long.parseLong(numberOfRow.getText()));
            tab.setSearchPointer(tab.getStartLineNumber());
            showText1();
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
            if (cRaf != null) {
                cRaf.close();
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
            tab.setSearchPointer(tab.getStartLineNumber());
            showText1();
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
            tab.setSearchPointer(tab.getStartLineNumber());
            showText1();
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

}

