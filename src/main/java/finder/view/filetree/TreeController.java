package finder.view.filetree;

import finder.util.FinderAction;
import finder.util.FileSystemLogic;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;
import java.util.ArrayList;

public class TreeController {

    @FXML
    private TreeView<String> fileTree;

    public TreeController() {

    }

    @FXML
    private void initialize() {
        // Creating root tree item.
        CheckBoxTreeItem<String> rootItem = new CheckBoxTreeItem<>("Roots:" + File.separator);
        // Creates the cell factory.
        fileTree.setCellFactory(CheckBoxTreeCell.forTreeView());
        fileTree.getStylesheets().add(Resources.CSS + "tree_view.css");

        // Create tree from root directories.
        File[] roots = File.listRoots();
        for (File root : roots) {
            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(root.getAbsolutePath());
            //treeItem.setIndependent(true); uncomment to be able to select independent folder search
            // (no subfolders selected etc - only direct folder.
            rootItem.getChildren().add(treeItem);
        }
        // Setting root item for file tree.
        fileTree.setRoot(rootItem);
        FinderAction.getInstance().setTree(fileTree);

        // File tree listener (expands folders and loads content w/o content of sub-folders, user will have to click at
        // sub-folder to load more content).
        // Each time you click folder - content is being loaded.
        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CheckBoxTreeItem<String> selectedItem = (CheckBoxTreeItem<String>) newValue;
            // "Roots:\" - root item of file_tree is not a File, so it wont be reloaded by using createTree method
            // after being retained (look createTree method) ->
            // -> that part of code creates children for any item, except Root - root is never reloaded.
            if (!selectedItem.equals(fileTree.getRoot())) {
                File file = new File(FileSystemLogic.getFilePath(selectedItem));
                createTree(file, selectedItem);
            }
        });
    }

    /**
     * Creating a part of tree with selected folder content.
     *
     * @param file   filepath for this treenode.
     * @param parent treenode.
     */
    private void createTree(File file, CheckBoxTreeItem<String> parent) {
        // Each time you click folder - content is being loaded, so retain and reload - refreshes file tree.
        //parent.getChildren().retainAll();
        parent.getChildren().retainAll(new ArrayList<CheckBoxTreeItem<String>>());
        boolean isSelected = parent.isSelected();
        if (file.listFiles() != null) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(f.getName(), null, isSelected);
                    //treeItem.setIndependent(true); uncomment to be able to select independent folder search
                    // (no subfolders selected etc - only direct folder.
                    if(parent.getChildren().add(treeItem)){
                        createTreeGeneration(f,treeItem);
                    }
                }
            }
            parent.setExpanded(true);
        }

    }

    /**
     * Creating 1 more generation for selected treeItem.
     *
     * @param file   filepath for this treenode.
     * @param parent treenode.
     */
    private void createTreeGeneration(File file, CheckBoxTreeItem<String> parent){
        //parent.getChildren().retainAll();
        parent.getChildren().retainAll(new ArrayList<CheckBoxTreeItem<String>>());
        boolean isSelected = parent.isSelected();
        if (file.listFiles() != null) {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(f.getName(), null, isSelected);
                    //treeItem.setIndependent(true); uncomment to be able to select independent folder search
                    // (no subfolders selected etc - only direct folder.
                    parent.getChildren().add(treeItem);
                }
            }
        }
    }
}