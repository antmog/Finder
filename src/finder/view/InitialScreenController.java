package finder.view;

import finder.util.Resources;
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

/**
 * Controller of main layout (initial screen).
 * Created by antmog on 07.09.2017.
 */
public class InitialScreenController {



    private Button searchFilesButton;

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


            SplitPane splitTop = (SplitPane) SplitRight.getItems().get(0);
            AnchorPane anchor = (AnchorPane) splitTop.getItems().get(1);
            AnchorPane anchor1 = (AnchorPane) anchor.getChildren().get(0);
            searchFilesButton = (Button) anchor1.getChildren().get(6);


            // Adding top and bottom parts to main layout.
            mainSplitPane.getItems().addAll(SplitLeft, SplitRight);
        }catch(Exception e){
            e.printStackTrace();
        }


        searchFilesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("CLICK CLICK");
            }
        });
    }


    @FXML
    private void searchFiles() {
        System.out.println("Searching...");
    }
}

