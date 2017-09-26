package finder.view.SplitRight.SplitTop;

import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * Controller of top block (search text area + search options).
 */
public class SplitTopController {

    private FinderActionInterface finderActionInterface;

    @FXML
    private SplitPane splitTop;

    public SplitTopController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {
        try {
            // Elements of top part of interface.


            // Search text area.
            TextArea textArea = finderActionInterface.load(this.getClass().getResource(Resources.FXMLtop + "TextArea.fxml"), new TextAreaController(this.finderActionInterface));
            // Search options block.
            AnchorPane searchOptionsBlock = finderActionInterface.load(this.getClass().getResource(Resources.FXMLtop + "SearchOptionsBlock.fxml"), new SearchOptionsController(this.finderActionInterface));
            searchOptionsBlock.getStylesheets().add(Resources.CSS + "SearchOptionsBlock.css");

            // Wrapping search options block into Anchor Pane (for better visualisation).
            //AnchorPane rightAnchor = new AnchorPane();
            //rightAnchor.getChildren().add(SearchOptionsBlock);
            //AnchorPane.setTopAnchor(SearchOptionsBlock, 5.0);


            // Setting searchOptionsBlock for FinderInstance
            finderActionInterface.setSearchOptionsBlock(searchOptionsBlock);

            // Filling top part of interface with sub-elements.
            splitTop.setOrientation(Orientation.HORIZONTAL);
            splitTop.getItems().addAll(textArea, searchOptionsBlock);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
