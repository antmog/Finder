package finder.model;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Interface used to set parameters of FinderInstance.
 */
public interface SetFinderInstanceParams {
    /**
     * Create new tab in tabPane with @file opened.
     *
     * @param file           that gonna be opened.
     * @param resultFileTree ResultFileTree element, containing tree, containing that file.
     */
    void actionNewTab(File file, ResultFileTree resultFileTree);

    /**
     * Attaches main text area to FinderInstance.
     *
     */
    void setTextArea(TextArea textArea);

    /**
     * Attaches main tree to FinderInstance.
     *
     */
    void setTree(TreeView<String> treeView);

    /**
     * Attaches tableData to FinderInstance.
     *
     */
    void setTableData(ObservableList<String> tableData);

    /**
     * Attaches TabPane to FinderInstance.
     *
     */
    void setTabPane(TabPane tabPane);

    /**
     * Attaches ResultFileTree to FinderInstance.
     *
     */
    void setResultTree(ResultFileTree<String> fileTree);

    /**
     * Attaches SearchOptionsBlock to FinderInstance.
     *
     */
    void setSearchOptionsBlock(AnchorPane searchOptionsBlock);

    /**
     * Attaches FileTreePane to FinderInstance.
     *
     */
    void setFileTreePane(SplitPane fileTreePane);

    /**
     * Adds URL root to main file tree.
     *
     */
    String addUrlRoot(String url);
}
