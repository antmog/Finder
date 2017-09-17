package finder.view;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import finder.view.SplitLeft.FileTreeController;
import finder.view.SplitRight.SplitRightController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;

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
            FXMLLoader treeLoader = new FXMLLoader(this.getClass().getResource(Resources.FXMLleft + "FileTree.fxml"));
            treeLoader.setController(new FileTreeController(this.finderActionInterface));
            TreeView SplitLeft = treeLoader.load();
            // Loading right pane.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Resources.FXMLright + "SplitRight.fxml"));
            loader.setController(new SplitRightController(this.finderActionInterface));
            SplitPane SplitRight = loader.load();

            // Adding top and bottom parts to main layout.
            mainSplitPane.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

