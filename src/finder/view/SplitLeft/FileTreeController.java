package finder.view.SplitLeft;

import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;

import java.io.File;

public class FileTreeController {

    @FXML
    private TreeView fileTree;

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
            rootItem.getChildren().add(treeItem);
        }

        // Setting root item for file tree.
        fileTree.setRoot(rootItem);

        // File tree listener (expands folders and loads content w/o content of sub-folders, user will have to click at
        // sub-folder to load more content).
        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            CheckBoxTreeItem<String> selectedItem = (CheckBoxTreeItem<String>) newValue;
            File file = new File(getFilePath(selectedItem));
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
                        parent.getChildren().add(treeItem);
                    }
                }
                parent.setExpanded(true);
            }
        } else if (true) {
           // any logic?
        }
    }

    /**
     * Getting filepath from the tree element (item).
     * @param item item of the tree
     * @return filepath
     */
    private String getFilePath(CheckBoxTreeItem<String> item) {
        StringBuffer filePath = new StringBuffer();
        filePath.append(item.getValue());
        while(item.getParent()!=null){
            item = (CheckBoxTreeItem<String>) item.getParent();
            if(!item.getValue().endsWith("\\")){
                filePath.insert(0,item.getValue() + "\\");
            }else if(!item.getValue().equals("Roots:" + File.separator)){
                filePath.insert(0,item.getValue());
            }
        }
        return filePath.toString();
    }
}