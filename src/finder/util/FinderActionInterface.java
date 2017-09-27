package finder.util;

import finder.model.*;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static finder.util.OtherLogic.CheckIfRoot;
import static finder.util.OtherLogic.getShortFileName;


/**
 * Implementation of Action interface, Set finder interface + keeps finderInstance.
 */
public class FinderActionInterface implements ActionsInterface, SetFinderInstance {
    private FinderInstance finderInstance;
    private TaskExecutor taskExecutor = new TaskExecutor();

    public FinderActionInterface(FinderInstance finderInstance) {
        this.finderInstance = finderInstance;
    }

    @Override
    public String addUrlRoot(String url){
        File folder;
        if(url.endsWith(":")){
            folder = new File(url+"\\");
        }else{
            folder = new File(url);
        }
        if(CheckIfRoot(folder)){
            return "File system roots are default tree nodes.";
        }
        if(folder.isDirectory()&&folder.canRead()){
            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(getShortFileName(folder));
            finderInstance.getFileTree().getRoot().getChildren().add(treeItem);
            return "Successfully added tree item.";
        }
        return "Is not directory/no access! Cant add.";
    }

    @Override
    public <T> T load(URL url, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(controller);
        return loader.load();
    }

    @Override
    public void actionClickSearch() {
        if (finderInstance.getTextArea().getText().equals("")) {
            new WarningWindow("Enter the text you are going to search pls.");
        } else {
            // disabling part of application, while searching file, making application safe
            finderInstance.getSearchOptionsBlock().setDisable(true);
            finderInstance.getFileTreePane().setDisable(true);
            FileSearchLogic.searchFiles(finderInstance,taskExecutor.createService());
        }
    }

    @Override
    public void deleteSelectedTreeNode() {
        TreeView fileTree = finderInstance.getFileTree();
        CheckBoxTreeItem<String> selectedItem = (CheckBoxTreeItem<String>) fileTree.getSelectionModel().getSelectedItem();
        if (selectedItem.equals(fileTree.getRoot())||CheckIfRoot(new File(selectedItem.getValue()))||(!selectedItem.getParent().equals(fileTree.getRoot()))) {
            new WarningWindow("You can delete only parents of Root and you\ncant delete  main Root or roots of file system.");
        }else{
            selectedItem.getParent().getChildren().remove(selectedItem);
            new WarningWindow("Successfully deleted tree item.");
        }
    }

    @Override
    public void actionNewTab(File file, ResultFileTree resultFileTree) {
        if (file.isFile()) {
            TabLogic.addTab(file, resultFileTree.getSearchText(), finderInstance);
        }
    }

    @Override
    public void actionSetTextArea(TextArea textArea) {
        finderInstance.setTextArea(textArea);
    }

    @Override
    public void actionSetTree(TreeView treeView) {
        finderInstance.setFileTree(treeView);
    }

    @Override
    public void actionSetTableData(ObservableList<String> loadedTableData) {
        finderInstance.setTableData(loadedTableData);
    }

    @Override
    public void actionSetTabPane(TabPane tabPane) {
        finderInstance.setResultTabPane(tabPane);
    }

    @Override
    public void setResultTree(ResultFileTree fileTree) {
        finderInstance.setResultFileTree(fileTree);
    }

    @Override
    public void setSearchOptionsBlock(AnchorPane searchOptionsBlock) {
        finderInstance.setSearchOptionsBlock(searchOptionsBlock);
    }

    @Override
    public void setFileTreePane(SplitPane fileTreePane){
        finderInstance.setFileTreePane(fileTreePane);
    }

}
