package finder.view.searchblock.searchresult;

import finder.model.ResultFileTree;
import finder.util.FinderAction;
import finder.util.Resources;
import javafx.fxml.FXML;

public class ResultFileTreeController {

    ;

    public ResultFileTreeController() {

    }

    @FXML
    private ResultFileTree<String> resultFileTree;

    @FXML
    private void initialize() {

        resultFileTree.getStylesheets().add(Resources.CSS + "result_tree_view.css");
        FinderAction.getInstance().setResultTree(resultFileTree);
        FinderAction.getInstance().addSelectionListener(resultFileTree);
    }
}