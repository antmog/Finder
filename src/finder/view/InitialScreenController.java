package finder.view;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import finder.view.FileTree.FileTreeController;
import finder.view.SearchBlock.SearchBlockController;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of main layout (initial screen).
 * Created by antmog on 07.09.2017.
 */
public class InitialScreenController {

    private FinderActionInterface finderActionInterface;

    @FXML
    private SplitPane mainSplitPane;

    public InitialScreenController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {

        try {
            // Main layout:
            mainSplitPane.setOrientation(Orientation.HORIZONTAL);

            // LEFT pane + RIGHT pane [TOP pane + BOT pane].
            // Creating sub-elements for the scene.

            // Loading left pane.
            SplitPane SplitLeft = finderActionInterface.load(this.getClass().getResource(Resources.FXMLFileTree + "FileTree.fxml"), new FileTreeController(this.finderActionInterface));
            // Loading right pane.
            SplitPane SplitRight = finderActionInterface.load(this.getClass().getResource(Resources.FXMLSearchBlock + "SearchBlock.fxml"), new SearchBlockController(this.finderActionInterface));

            // Adding top and bottom parts to main layout.
            mainSplitPane.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

