package finder.view.SplitRight;

import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;


/**
 * Controller of right part (top+bottom).
 * Created by antmog on 06.09.2017.
 */
public class SplitRightController {

    @FXML
    private SplitPane splitRight;

    @FXML
    private void initialize() {

        try{
            // Initialising RIGHT part of interface.

            // Loading TOP part of interface.
            SplitPane SplitTop = FXMLLoader.load(this.getClass().getResource(Resources.FXMLtop + "SplitTop.fxml"));

            // Loading BOTTOM part of interface.
            SplitPane SplitBottom = FXMLLoader.load(this.getClass().getResource(Resources.FXMLbot + "SplitBottom.fxml"));

            splitRight.getItems().addAll(SplitTop, SplitBottom);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
