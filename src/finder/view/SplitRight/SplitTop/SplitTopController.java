package finder.view.SplitRight.SplitTop;

import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of top block (search text area + search options).
 * Created by antmog on 07.09.2017.
 */
public class SplitTopController {

    @FXML
    private SplitPane splitTop;

    @FXML
    private void initialize() {

        try {
            // Elements of top part of interface.

            // Search text area.
            TextArea textArea = FXMLLoader.load(this.getClass().getResource(Resources.FXMLtop + "TextArea.fxml"));
            // Search options block.

            Node SearchOptionsBlock = FXMLLoader.load(this.getClass().getResource(Resources.FXMLtop + "SearchOptionsBlock.fxml"));
            // Wrapping search options block into Anchor Pane (for better visualisation).
            AnchorPane rightAnchor = new AnchorPane();
            rightAnchor.getChildren().add(SearchOptionsBlock);
            AnchorPane.setTopAnchor(SearchOptionsBlock, 5.0);


            // Filling top part of interface with sub-elements.
            splitTop.setOrientation(Orientation.HORIZONTAL);
            splitTop.getItems().addAll(textArea, rightAnchor);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
