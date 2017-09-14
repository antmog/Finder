package finder.view.SplitRight;

import finder.model.ActionsInterface;
import finder.util.Resources;
import finder.view.SplitRight.SplitBottom.SplitBottomController;
import finder.view.SplitRight.SplitTop.SplitTopController;
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

    private ActionsInterface actionsInterface;

    @FXML
    private SplitPane splitRight;

    public SplitRightController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private void initialize() {
        this.actionsInterface = actionsInterface;
        try{
            // Initialising RIGHT part of interface.

            // Loading TOP part of interface.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Resources.FXMLtop + "SplitTop.fxml"));
            loader.setController(new SplitTopController(this.actionsInterface));
            SplitPane SplitTop = loader.load();

            // Loading BOTTOM part of interface.
            SplitPane SplitBottom = actionsInterface.load(this.getClass().getResource(Resources.FXMLbot + "SplitBottom.fxml"),new SplitBottomController(this.actionsInterface));

            splitRight.getItems().addAll(SplitTop, SplitBottom);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
