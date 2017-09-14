package finder.view.SplitLeft;

import finder.model.ActionsInterface;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import sun.reflect.generics.tree.Tree;

import java.io.File;

public class FileTreeController {

    private ActionsInterface actionsInterface;

    @FXML
    private TreeView fileTree;

    public FileTreeController(ActionsInterface actionsInterface) {
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
            //treeItem.setIndependent(true); uncomment to be able to select independent folder search (no subfolders selected etc - only direct folder.
            rootItem.getChildren().add(treeItem);
        }

        // Setting root item for file tree.
        fileTree.setRoot(rootItem);
        actionsInterface.actionSetTree(fileTree);

        // File tree listener (expands folders and loads content w/o content of sub-folders, user will have to click at
        // sub-folder to load more content).
        // Each time you click folder - content is being loaded.
        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CheckBoxTreeItem<String> selectedItem = (CheckBoxTreeItem<String>) newValue;
            //selectedItem.setIndependent(true); uncomment to be able to select independent folder search (no subfolders selected etc - only direct folder.
            // "Roots:\" - root item of FileTree is not a File, so it wont be reloaded by using createTree method after being retained (look createTree method) ->
            // -> that part of code creates children for root item.
            if(!selectedItem.equals(fileTree.getRoot())){
                File file = new File(actionsInterface.getFilePath(selectedItem));
                createTree(file, selectedItem);
            }
        });



    }

    /**
     * Creating a part of tree with selected folder content.
     * @param file   filepath for this treenode.
     * @param parent treenode.
     */
    private void createTree(File file, CheckBoxTreeItem<String> parent) {
        // Each time you click folder - content is being loaded, so retain and reload - refreshes file tree.
        parent.getChildren().retainAll();
        boolean isSelected = parent.isSelected();
        if (file.listFiles() != null) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(f.getName(), null, isSelected);
                    //treeItem.setIndependent(true); uncomment to be able to select independent folder search (no subfolders selected etc - only direct folder.
                    parent.getChildren().add(treeItem);
                }
            }
            parent.setExpanded(true);
        }

    }


}