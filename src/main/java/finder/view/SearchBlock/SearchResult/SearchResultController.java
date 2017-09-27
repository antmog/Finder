package finder.view.SearchBlock.SearchResult;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeView;

/**
 * Controller of bottom part (result file tree + file content).
 */
public class SearchResultController {

    private FinderActionInterface finderActionInterface;

    public SearchResultController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private SplitPane splitBottom;

    @FXML
    private void initialize() {
        try {
            splitBottom.setOrientation(Orientation.HORIZONTAL);

            // Loading left pane.
            TreeView SplitLeft = finderActionInterface.load(this.getClass().getResource(Resources.FXMLSearchResult
                    + "result_file_tree.fxml"), new ResultFileTreeController(this.finderActionInterface));
            // Loading right pane.
            TabPane SplitRight = finderActionInterface.load(this.getClass().getResource(Resources.FXMLSearchResult
                    + "tab_pane.fxml"), new TabsController(this.finderActionInterface));
            SplitRight.getStylesheets().add(Resources.CSS + "tab_pane.css");

            // setting loaded elements as content of bottom part
            splitBottom.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
