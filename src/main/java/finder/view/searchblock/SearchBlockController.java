package finder.view.searchblock;

import finder.util.FinderAction;
import finder.util.Resources;
import finder.view.searchblock.searchresult.SearchResultController;
import finder.view.searchblock.searchparameters.SearchParametersController;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;


/**
 * Controller of right part (top+bottom).
 * Created by antmog on 06.09.2017.
 */
public class SearchBlockController {

    ;

    @FXML
    private SplitPane splitRight;

    public SearchBlockController() {

    }

    @FXML
    private void initialize() {
        try {
            // Initialising RIGHT part of interface.

            // Loading TOP part of interface.
            SplitPane SplitTop = FinderAction.getInstance().load(this.getClass().getResource(
                    Resources.FXMLSearchParameters + "search_parameters.fxml"),
                    new SearchParametersController());

            // Loading BOTTOM part of interface.
            SplitPane SplitBottom = FinderAction.getInstance().load(this.getClass().getResource(
                    Resources.FXMLSearchResult + "search_result.fxml"),
                    new SearchResultController());

            splitRight.getItems().addAll(SplitTop, SplitBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
