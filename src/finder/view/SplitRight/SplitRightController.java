package finder.view.SplitRight;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import finder.view.SplitRight.SplitBottom.SplitBottomController;
import finder.view.SplitRight.SplitTop.SplitTopController;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;


/**
 * Controller of right part (top+bottom).
 * Created by antmog on 06.09.2017.
 */
public class SplitRightController {

    private FinderActionInterface finderActionInterface;

    @FXML
    private SplitPane splitRight;

    public SplitRightController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {
        try {
            // Initialising RIGHT part of interface.

            // Loading TOP part of interface.
            SplitPane SplitTop = finderActionInterface.load(this.getClass().getResource(Resources.FXMLtop + "SplitTop.fxml"), new SplitTopController(this.finderActionInterface));

            // Loading BOTTOM part of interface.
            SplitPane SplitBottom = finderActionInterface.load(this.getClass().getResource(Resources.FXMLbot + "SplitBottom.fxml"), new SplitBottomController(this.finderActionInterface));

            splitRight.getItems().addAll(SplitTop, SplitBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
