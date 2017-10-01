package finder.view.searchblock.searchresult;

import finder.util.FinderAction;
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

    ;

    public SearchResultController() {

    }

    @FXML
    private SplitPane splitBottom;

    @FXML
    private void initialize() {
        try {
            splitBottom.setOrientation(Orientation.HORIZONTAL);

            // Loading left pane.
            TreeView SplitLeft = FinderAction.getInstance().load(this.getClass().getResource(Resources.FXMLSearchResult
                    + "result_file_tree.fxml"), new ResultFileTreeController());
            // Loading right pane.
            TabPane SplitRight = FinderAction.getInstance().load(this.getClass().getResource(Resources.FXMLSearchResult
                    + "tab_pane.fxml"), new TabsController());
            SplitRight.getStylesheets().add(Resources.CSS + "tab_pane.css");

            // setting loaded elements as content of bottom part
            splitBottom.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
