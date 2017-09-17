package finder.model;

import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;

/**
 * Interface used to set parameters of FinderInstance.
 */
public interface SetFinderInstance {
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
    void actionSetTextArea(TextArea textArea);

    /**
     * Attaches main tree to FinderInstance.
     *
     */
    void actionSetTree(TreeView treeView);

    /**
     * Attaches tableData to FinderInstance.
     *
     */
    void actionSetTableData(ObservableList<String> tableData);

    /**
     * Attaches TabPane to FinderInstance.
     *
     */
    void actionSetTabPane(TabPane tabPane);

    /**
     * Attaches ResultFileTree to FinderInstance.
     *
     */
    void setResultTree(ResultFileTree fileTree);

    /**
     * Attaches SearchOptionsBlock to FinderInstance.
     *
     */
    void setSearchOptionsBlock(AnchorPane searchOptionsBlock);
}
