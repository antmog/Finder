package finder.util;

import finder.model.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * Implementation of Action interface, Set finder interface + keeps finderInstance.
 */
public class FinderActionInterface implements ActionsInterface, SetFinderInstance {
    private FinderInstance finderInstance;

    public FinderActionInterface(FinderInstance finderInstance) {
        this.finderInstance = finderInstance;
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
            finderInstance.getFileTree().setDisable(true);
            finderInstance.getSearchOptionsBlock().setDisable(true);
            FileSearchLogic.searchFiles(finderInstance);
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

}
