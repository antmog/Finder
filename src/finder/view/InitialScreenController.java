package finder.view;

import finder.util.Resources;
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

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private void initialize() {

        try{
            // Main layout:
            mainSplitPane.setOrientation(Orientation.HORIZONTAL);

            // LEFT pane + RIGHT pane [TOP pane + BOT pane].
            // Creating sub-elements for the scene.

            // Loading left pane.
            TreeView SplitLeft = FXMLLoader.load(this.getClass().getResource(Resources.FXMLleft + "FileTree.fxml"));
            // Loading right pane.
            SplitPane SplitRight = FXMLLoader.load(this.getClass().getResource(Resources.FXMLright + "SplitRight.fxml"));

            // Adding top and bottom parts to main layout.
            mainSplitPane.getItems().addAll(SplitLeft, SplitRight);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

