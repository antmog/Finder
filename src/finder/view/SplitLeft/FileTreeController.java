package finder.view.SplitLeft;

import finder.model.ActionsInterface;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;

public class FileTreeController {

    private ActionsInterface actionsInterface;

    @FXML
    private TreeView fileTree;

    public FileTreeController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private void initialize() {

        // Creating root tree item.
        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>("Roots:" + File.separator);
        // Creates the cell factory.
        fileTree.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
        // Create tree from root directories.
        File[] roots = File.listRoots();
        for (File root : roots) {
            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(root.getAbsolutePath());
            treeItem.setIndependent(true);
            rootItem.getChildren().add(treeItem);
        }

        // Setting root item for file tree.
        fileTree.setRoot(rootItem);
        actionsInterface.actionGetTree(fileTree);

        // File tree listener (expands folders and loads content w/o content of sub-folders, user will have to click at
        // sub-folder to load more content).
        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CheckBoxTreeItem<String> selectedItem = (CheckBoxTreeItem<String>) newValue;
            selectedItem.setIndependent(true);
            File file = new File(actionsInterface.getFilePath(selectedItem));
            createTree(file, selectedItem);
        });
    }

    /**
     * Creating a part of tree with selected folder content.
     * @param file filepath for this treenode.
     * @param parent treenode.
     */
    private void createTree(File file, CheckBoxTreeItem<String> parent) {
        if (file.isDirectory()) {
            parent.getChildren().retainAll();
            if (file.listFiles() != null) {
                for (File f : file.listFiles()) {
                    if(f.isDirectory()){
                        CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(f.getName());
                        treeItem.setIndependent(true);
                        parent.getChildren().add(treeItem);
                    }
                }
                parent.setExpanded(true);
            }
        } else if (true) {
           // any logic?
        }
    }


}