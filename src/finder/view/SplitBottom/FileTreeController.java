package finder.view.SplitBottom;

import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;

public class FileTreeController {

    @FXML
    private TreeView fileTree ;

    @FXML
    private void initialize() {
        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>("Roots:");
        // Creates the cell factory.
        fileTree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

        // create tree from root directories.
        File[] roots = File.listRoots();
        for (File root : roots){
            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(root.getAbsolutePath());
            rootItem.getChildren().add(treeItem);
            // OnClick crete sub tree
                /*if(root.listFiles()!=null){
                    for (File f : root.listFiles()) {
                        createTree(f, treeItem);
                    }
                }*/
        }

        //

        fileTree.setRoot(rootItem);
    }

}