package finder.util;

import finder.model.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static finder.util.FileSystemLogic.CheckIfRoot;
import static finder.util.FileSystemLogic.getShortFileName;


/**
 * Implementation of Action interface, SetInstanceParamsInterface + keeps finderInstance.
 */
public class FinderAction implements ActionsInterface, SetInstanceParamsInterface {
    private FinderInstance finderInstance;
    private static volatile FinderAction instance;

    public static FinderAction getInstance() {
        FinderAction localInstance = instance;
        if (localInstance == null) {
            synchronized (FinderAction.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new FinderAction();
                }
            }
        }
        return localInstance;
    }
    private FinderAction(){
        finderInstance = new FinderInstance();
    }

    ChangeListener<TreeItem<String>> changeListener;

    public FinderInstance getFinderInstance() {
        return finderInstance;
    }

    @Override
    public String addUrlRoot(String url) {
        File folder;
        if (url.endsWith(":")) {
            folder = new File(url + File.separator);
        } else {
            folder = new File(url);
        }
        if (CheckIfRoot(folder)) {
            return "File system roots are default tree nodes.";
        }
        if (folder.isDirectory() && folder.canRead()) {
            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(getShortFileName(folder));
            finderInstance.getFileTree().getRoot().getChildren().add(treeItem);
            return "Successfully added tree item.";
        }
        return "Is not directory/no access! Cant add.";
    }

    @Override
    public <NodeType,ControllerType> NodeType load(URL url, ControllerType controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(controller);
        return loader.load();
    }

    @Override
    public void actionClickSearch() {
        finderInstance.getTextArea().setText("text");
        if (finderInstance.getTextArea().getText().equals("")) {
            new WarningWindow("Enter the text you are going to search pls.");
        } else {
            // disabling part of application, while searching file, making application safe
            finderInstance.getAddButton().setDisable(true);
            finderInstance.getDelButton().setDisable(true);
            finderInstance.getAddExtensionTextField().setDisable(true);
            finderInstance.getSearchButton().setText("Stop");
            finderInstance.getFileTreePane().setDisable(true);
            ExecutorService exec = Executors.newCachedThreadPool();
            finderInstance.getSearchButton().setOnAction(event -> FileSearchLogic.stopSearch(exec));
            FileSearchLogic.searchFiles(exec);
        }
    }

    @Override
    public void deleteSelectedTreeNode() {
        TreeView<String> fileTree = finderInstance.getFileTree();
        CheckBoxTreeItem<String> selectedItem =
                (CheckBoxTreeItem<String>) fileTree.getSelectionModel().getSelectedItem();
        if (selectedItem.equals(fileTree.getRoot()) || CheckIfRoot(new File(selectedItem.getValue())) ||
                (!selectedItem.getParent().equals(fileTree.getRoot()))) {
            new WarningWindow("You can delete only parents of Root and you\ncant " +
                    "delete  main Root or roots of file system.");
        } else {
            selectedItem.getParent().getChildren().remove(selectedItem);
            new WarningWindow("Successfully deleted tree item.");
        }
    }

    @Override
    public void addSelectionListener(ResultFileTree<String> tree) {
        changeListener = (observable, oldValue, newValue) -> this.actionNewTab
                (new File(FileSystemLogic.getFilePath(newValue)), tree);
        // selection listener - opens file from result tree
        tree.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    @Override
    public void deleteSelectionListener(ResultFileTree<String> tree) {
        tree.getSelectionModel().selectedItemProperty().removeListener(changeListener);
    }

    @Override
    public void actionNewTab(File file, ResultFileTree resultFileTree) {
        if (file.isFile()) {
            TabLogic.addTab(file, resultFileTree.getSearchText(), finderInstance);
        }
    }

    @Override
    public void setTextArea(TextArea textArea) {
        finderInstance.setTextArea(textArea);
    }

    @Override
    public void setTree(TreeView<String> treeView) {
        finderInstance.setFileTree(treeView);
    }

    @Override
    public void setTableData(ObservableList<String> loadedTableData) {
        finderInstance.setTableData(loadedTableData);
    }

    @Override
    public void setTabPane(TabPane tabPane) {
        finderInstance.setResultTabPane(tabPane);
    }

    @Override
    public void setResultTree(ResultFileTree<String> fileTree) {
        finderInstance.setResultFileTree(fileTree);
    }

    @Override
    public void setSearchOptionsBlock(AnchorPane searchOptionsBlock) {
        finderInstance.setSearchOptionsBlock(searchOptionsBlock);
    }

    @Override
    public void setFileTreePane(SplitPane fileTreePane) {
        finderInstance.setFileTreePane(fileTreePane);
    }

}
