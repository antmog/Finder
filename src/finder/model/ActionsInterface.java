package finder.model;


import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by antmog on 07.09.2017.
 */
public interface ActionsInterface {
    <T> T load(URL url, Object controller) throws IOException;

    void actionClick();
    void actionNewTab(File file);

    void actionSetTextArea(TextArea text);
    void actionSetTree(TreeView tree);
    void actionSetTableData(ObservableList<Extension> tableData);
    void actionSetTabPane(TabPane tabPane);
    void setResultTree(TreeView fileTree);


   // void createResultTree(File file, CheckBoxTreeItem<String> parent);

    /**
     * Getting filepath from the tree element (item).
     * @param item item of the tree
     * @return filepath
     */
    String getFilePath(TreeItem<String> item);
}
