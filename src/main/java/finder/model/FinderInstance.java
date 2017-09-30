package finder.model;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * Instance of application.
 */
public class FinderInstance {
    private ResultFileTree<String> resultFileTree;                      // ResultFileTree object (see ResultFileTree.java)
    private TextArea textArea;                                  // Main TextArea of application
    private TreeView<String> fileTree;                                  // Main file tree
    private TabPane resultTabPane;                              // TabPane containing tabs
    private ObservableList<String> tableData;                   // Table with extensions data

    private AnchorPane searchOptionsBlock;                      // All searchOptions block
    private SplitPane fileTreePane;                             // All left part (addUrl + main tree)

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
