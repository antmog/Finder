package finder.view.searchblock.searchresult;

import finder.model.ResultFileTree;
import finder.util.FinderActionInterface;
import finder.util.FileSystemLogic;
import finder.util.Resources;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;

import java.io.File;

public class ResultFileTreeController {

    private FinderActionInterface finderActionInterface;

    public ResultFileTreeController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private ResultFileTree<String> resultFileTree;

    @FXML
    private void initialize() {

        resultFileTree.getStylesheets().add(Resources.CSS + "result_tree_view.css");
        finderActionInterface.setResultTree(resultFileTree);
        finderActionInterface.addSelectionListener(resultFileTree);
    }
}