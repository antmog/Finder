package finder.view.SplitRight.SplitBottom;

import finder.model.ResultFileTree;
import finder.util.FinderActionInterface;
import finder.util.OtherLogic;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public class ResultFileTreeController {

    private FinderActionInterface finderActionInterface;
    private ResultFileTree resultFileTree;

    public ResultFileTreeController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private TreeView fileTree;

    @FXML
    private void initialize() {
        // creating new ResultFileTree object (containing current fileTree)
        resultFileTree = new ResultFileTree(fileTree);
        finderActionInterface.setResultTree(resultFileTree);
        // selection listener - opens file from result tree
        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            finderActionInterface.actionNewTab(new File(OtherLogic.getFilePath((TreeItem<String>) newValue)), resultFileTree);
            // exception usually happens when loading new ResultTree instead of old and smth is selected in old one.
            // solution - change to OnClick listener
        });
    }
}