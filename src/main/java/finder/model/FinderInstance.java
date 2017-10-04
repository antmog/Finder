package finder.model;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;

/**
 * Instance of application.
 */
public class FinderInstance {
    private ResultFileTree<String> resultFileTree;                      // ResultFileTree object (see ResultFileTree.java)
    private TextArea textArea;                                  // Main TextArea of application
    private TreeView<String> fileTree;                                  // Main file tree
    private TabPane resultTabPane;                              // TabPane containing tabs
    private ObservableList<String> tableData;                   // Table with extensions data

    private SplitPane fileTreePane;                             // All left part (addUrl + main tree)
    private AnchorPane searchOptionsBlock;                      // All searchOptions block

    private Button addButton;                                   // buttons of searchOptionsBlock
    private Button delButton;                                   //
    private Button searchButton;                                //
    private TextField addExtensionTextField;                    // textfield of searchOptionsBlock

    private BufferedReader searchInFileReader;                                  // reader for initial text search (in file).

    public BufferedReader getSearchInFileReader() {
        return searchInFileReader;
    }

    public void setSearchInFileReader(BufferedReader searchInFileReader) {
        this.searchInFileReader = searchInFileReader;
    }

    public TextField getAddExtensionTextField() {
        return addExtensionTextField;
    }

    public void setAddExtensionTextField(TextField addExtensionTextField) {
        this.addExtensionTextField = addExtensionTextField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getDelButton() {
        return delButton;
    }

    public void setDelButton(Button delButton) {
        this.delButton = delButton;
    }

    public Button getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(Button searchButton) {
        this.searchButton = searchButton;
    }


    public SplitPane getFileTreePane() {
        return fileTreePane;
    }

    public void setFileTreePane(SplitPane fileTreePane) {
        this.fileTreePane = fileTreePane;
    }



    public AnchorPane getSearchOptionsBlock() {
        return searchOptionsBlock;
    }

    public void setSearchOptionsBlock(AnchorPane searchOptionsBlock) {
        this.searchOptionsBlock = searchOptionsBlock;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public TreeView<String> getFileTree() {
        return fileTree;
    }

    public void setFileTree(TreeView<String> fileTree) {
        this.fileTree = fileTree;
    }

    public TabPane getResultTabPane() {
        return resultTabPane;
    }

    public void setResultTabPane(TabPane resultTabPane) {
        this.resultTabPane = resultTabPane;
    }

    public ObservableList<String> getTableData() {
        return tableData;
    }

    public void setTableData(ObservableList<String> tableData) {
        this.tableData = tableData;
    }

    public ResultFileTree<String> getResultFileTree() {
        return resultFileTree;
    }

    public void setResultFileTree(ResultFileTree<String> resultFileTree) {
        this.resultFileTree = resultFileTree;
    }
}
