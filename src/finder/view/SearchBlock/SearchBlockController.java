package finder.view.SearchBlock;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import finder.view.SearchBlock.SearchResult.SearchResultController;
import finder.view.SearchBlock.SearchParameters.SearchParametersController;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;


/**
 * Controller of right part (top+bottom).
 * Created by antmog on 06.09.2017.
 */
public class SearchBlockController {

    private FinderActionInterface finderActionInterface;

    @FXML
    private SplitPane splitRight;

    public SearchBlockController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {
        try {
            // Initialising RIGHT part of interface.

            // Loading TOP part of interface.
            SplitPane SplitTop = finderActionInterface.load(this.getClass().getResource(Resources.FXMLtop + "SearchParameters.fxml"), new SearchParametersController(this.finderActionInterface));

            // Loading BOTTOM part of interface.
            SplitPane SplitBottom = finderActionInterface.load(this.getClass().getResource(Resources.FXMLbot + "SearchResult.fxml"), new SearchResultController(this.finderActionInterface));

            splitRight.getItems().addAll(SplitTop, SplitBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
