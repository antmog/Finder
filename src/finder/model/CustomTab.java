package finder.model;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.File;

/**
 * Created by antmog on 13.09.2017.
 */
public class CustomTab {
    private final long DEFAULT_STEP = 10;
    private int id;
    private String name;
    private TabPane parentTabPane;
    private TextField showLinesCount;

    File file;

    private Tab tab;
    private TextArea textArea;

    public CustomTab(int tabId, String tabName, TabPane resultTabPane,File file) {
        this.id = tabId;
        this.name = tabName;
        this.parentTabPane = resultTabPane;

        this.file = file;

        tab = new Tab(this.name);
        parentTabPane.getTabs().add(tab);
    }

    public void setElements(TextArea textArea, TextField showLinesCount){
        this.textArea = textArea;
        this.showLinesCount = showLinesCount;
    }
    public File getFile(){
        return file;
    }
    public void setContent(Node node){
        tab.setContent(node);
    }
    public Tab getTab(){
        return this.tab;
    }
    public TabPane getTabPane(){
        return parentTabPane;
    }

    public TextArea getTextArea(){
        return textArea;
    }

    public long getShowLinesCount(){
        try{
            return Long.parseLong(showLinesCount.getText());
        }catch(Exception e){
            showLinesCount.setText(String.valueOf(DEFAULT_STEP));
            new WarningWindow("You have to enter a number in \"rows count\" text field.");
        }
        return DEFAULT_STEP;
    }
    public void setShowLinesCountDefault(){
        showLinesCount.setText(String.valueOf(DEFAULT_STEP));
    }
}
