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

    @FXML
    private TextField showLinesCount;

    @FXML
    private TextArea textArea;

    public TabTemplateController(CustomTab tab){
        this.tab = tab;
        startLineNumber = 0;
    }

    @FXML
    private void initialize() {
        System.out.println("we init");
        showLinesCount.setText("10");
        tab.setElements(textArea,showLinesCount);
        showText();
    }

    private void showText(){
        tab.getTextArea().setText("");
        try (BufferedReader in = new BufferedReader(new FileReader(tab.getFile()))) {
            int linesCount = 0;
            String line;
            while ((line = in.readLine()) != null) {
                if(linesCount==startLineNumber){
                    System.out.println(line);
                    tab.getTextArea().appendText(line);
                    tab.getTextArea().appendText(System.lineSeparator());
                    for( int i = 1 ; i < tab.getShowLinesCount() ; i++ ){
                        if((line = in.readLine()) != null){
                            System.out.println(line);
                            tab.getTextArea().appendText(line);
                            tab.getTextArea().appendText(System.lineSeparator());
                        }
                    }
                    break;
                }
                linesCount++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        startLineNumber -= 10;
        if(startLineNumber<0){
            startLineNumber=0;
        }
        showText();
    }

    @FXML
    private void down() {
        startLineNumber += 10;
        if(startLineNumber<0){
            startLineNumber=Long.MAX_VALUE-tab.getShowLinesCount();
        }
        showText();
    }


}

