package finder.view.SplitRight.SplitBottom;

import com.sun.javafx.embed.HostDragStartListener;
import finder.model.CustomTab;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by antmog on 13.09.2017.
 */
public class TabTemplateController {

    private CustomTab tab;
    private long startLineNumber;
    private long endOfFile = 0;

    @FXML
    private TextField showLinesCount;

    @FXML
    private TextArea textArea;

    public TabTemplateController(CustomTab tab) {
        this.tab = tab;
        startLineNumber = 0;
    }

    @FXML
    private void initialize() {
        System.out.println("we init");
        showLinesCount.setText("10");
        tab.setElements(textArea, showLinesCount);
        showText();
    }

    private void showText() {
        boolean isInRange = false;
        // if step is too big (bigger than file size - legit for small files)
        if ((endOfFile > 0) && endOfFile < tab.getShowLinesCount()) {
            optimizeStep();
        }
        tab.getTextArea().setText("");
        try (BufferedReader in = new BufferedReader(new FileReader(tab.getFile()))) {
            long linesCount = 0;
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("StartLineNumber" + startLineNumber);
                System.out.println(linesCount);
                if (linesCount == startLineNumber) {
                    isInRange = true;
                    System.out.println(line);
                    linesCount++;
                    tab.getTextArea().appendText(line);
                    tab.getTextArea().appendText(System.lineSeparator());
                    for (int i = 1; i < tab.getShowLinesCount(); i++) {
                        if ((line = in.readLine()) != null) {
                            linesCount++;
                            tab.getTextArea().appendText(line);
                            tab.getTextArea().appendText(System.lineSeparator());
                        } else {
                            // If step is good but its the end of file.
                            optimizeStep(linesCount);
                            break;
                        }
                    }
                    break;
                }
                linesCount++;
            }
            // If step was too big but still smaller than filesize.
            if (!isInRange) {
                optimizeStep(linesCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Setting step of reading file to default, moving marker to (endOfFile-DEFAULT_STEP)
     */
    private void optimizeStep(){
        tab.setShowLinesCountDefault();
        startLineNumber = endOfFile - tab.getShowLinesCount();
    }

    /**
     * Setting end of file;
     * Setting step of reading file to default, moving marker to (endOfFile-DEFAULT_STEP);
     * Reloading file view.
     * @param linesCount
     */
    private void optimizeStep(long linesCount){
        endOfFile = linesCount;
        optimizeStep();
        showText();
    }

    @FXML
    private void searchNext() {

    }

    @FXML
    private void searchPrevious() {

    }

    @FXML
    private void selectAll() {

    }

    @FXML
    private void close() {
        tab.getTabPane().getTabs().remove(tab.getTab());
    }

    @FXML
    private void up() {
        startLineNumber -= tab.getShowLinesCount();
        if (startLineNumber < 0) {
            startLineNumber = 0;
        }
        showText();
    }

    @FXML
    private void down() {
        startLineNumber += tab.getShowLinesCount();
        if (startLineNumber < 0) {
            startLineNumber = Long.MAX_VALUE - tab.getShowLinesCount();
        }
        if ((endOfFile > 0) && (startLineNumber >= endOfFile)) {
            startLineNumber = endOfFile - tab.getShowLinesCount();
        }
        showText();
    }


}

