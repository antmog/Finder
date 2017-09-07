package finder.view;

import com.sun.org.apache.xml.internal.security.Init;
import finder.model.ActionsInterface;
import finder.util.Resources;
import finder.view.SplitRight.SplitRightController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;

/**
 * Controller of main layout (initial screen).
 * Created by antmog on 07.09.2017.
 */
public class InitialScreenController {

    private ActionsInterface actionsInterface;

    @FXML
    private SplitPane mainSplitPane;

    public InitialScreenController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

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
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Resources.FXMLright + "SplitRight.fxml"));
            loader.setController(new SplitRightController(this.actionsInterface));
            SplitPane SplitRight = loader.load();

            // Adding top and bottom parts to main layout.
            mainSplitPane.getItems().addAll(SplitLeft, SplitRight);
        }catch(Exception e){
            e.printStackTrace();
        }


       /* searchFilesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("CLICK CLICK");
            }
        });*/
    }

}

