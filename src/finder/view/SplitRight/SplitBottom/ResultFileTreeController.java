package finder.view.SplitRight.SplitBottom;

import finder.model.ActionsInterface;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import java.io.File;

public class ResultFileTreeController {

    private ActionsInterface actionsInterface;

    public ResultFileTreeController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private TreeView fileTree;

    @FXML
    private void initialize() {
        actionsInterface.setResultTree(fileTree);
        fileTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            actionsInterface.actionNewTab(new File(actionsInterface.getFilePath((TreeItem<String>) newValue)));
        });
    }
}