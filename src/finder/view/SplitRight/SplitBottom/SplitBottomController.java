package finder.view.SplitRight.SplitBottom;

import finder.model.ActionsInterface;
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

    private ActionsInterface actionsInterface;

    public SplitBottomController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private SplitPane splitBottom;

    @FXML
    private void initialize() {

        try {
            splitBottom.setOrientation(Orientation.HORIZONTAL);

            // Loading left pane.
            TreeView SplitLeft = actionsInterface.load(this.getClass().getResource(Resources.FXMLbot + "ResultFileTree.fxml"),new ResultFileTreeController(this.actionsInterface));
            // Loading right pane.
            TabPane SplitRight = actionsInterface.load(this.getClass().getResource(Resources.FXMLbot + "TabPane.fxml"),new TabsController(this.actionsInterface));

            splitBottom.getItems().addAll(SplitLeft, SplitRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
