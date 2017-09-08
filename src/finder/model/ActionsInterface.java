package finder.model;


import javafx.collections.ObservableList;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;

import java.io.File;

/**
 * Created by antmog on 07.09.2017.
 */
public interface ActionsInterface {
    void actionClick();
    void actionGetTextArea(TextArea text);
    void actionGetTree(TreeView tree);
    void actionGetTableData(ObservableList<Extension> tableData);

    /**
     * Getting filepath from the tree element (item).
     * @param item item of the tree
     * @return filepath
     */
    String getFilePath(CheckBoxTreeItem<String> item);
}
