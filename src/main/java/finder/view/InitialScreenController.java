package finder.view;

import finder.util.FinderAction;
import finder.util.Resources;
import finder.view.filetree.FileTreeController;
import finder.view.searchblock.SearchBlockController;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;

/**
 * Controller of main layout (initial screen).
 * Created by antmog on 07.09.2017.
 */
public class InitialScreenController {

    ;

    @FXML
    private SplitPane mainSplitPane;

    public InitialScreenController() {

    }

    @FXML
    private void initialize() {

        try {
            // Main layout:
            mainSplitPane.setOrientation(Orientation.HORIZONTAL);

            // LEFT pane + RIGHT pane [TOP pane + BOT pane].
            // Creating sub-elements for the scene.

            // Loading left pane.
            SplitPane SplitLeft = FinderAction.getInstance().load(this.getClass().getResource(
                    Resources.FXMLFileTree + "file_tree.fxml"),
                    new FileTreeController());
            // Loading right pane.
            SplitPane SplitRight = FinderAction.getInstance().load(this.getClass().getResource(
                    Resources.FXMLSearchBlock + "search_block.fxml"),
                    new SearchBlockController());

            // Adding top and bottom parts to main layout.
            mainSplitPane.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

