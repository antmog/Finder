package finder.view.SplitRight.SplitBottom;

import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of bottom part (result file tree + file content).
 * Created by antmog on 07.09.2017.
 */
public class SplitBottomController {

    @FXML
    private SplitPane splitBottom;

    @FXML
    private void initialize() {

        try {
            splitBottom.setOrientation(Orientation.HORIZONTAL);

            // Loading left pane.
            TreeView SplitLeft = FXMLLoader.load(this.getClass().getResource(Resources.FXMLbot + "ResultFileTree.fxml"));
            // Loading right pane.
            TabPane SplitRight = FXMLLoader.load(this.getClass().getResource(Resources.FXMLbot + "TabPane.fxml"));

            splitBottom.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
