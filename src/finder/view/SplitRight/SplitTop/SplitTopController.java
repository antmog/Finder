package finder.view.SplitRight.SplitTop;

import finder.model.ActionsInterface;
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

    private ActionsInterface actionsInterface;

    @FXML
    private SplitPane splitTop;

    private TextArea textArea;

    public SplitTopController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private void initialize() {
        try {
            // Elements of top part of interface.

            // Search text area.
            FXMLLoader textLoader = new FXMLLoader(this.getClass().getResource(Resources.FXMLtop + "TextArea.fxml"));
            textLoader.setController(new TextAreaController(this.actionsInterface));
            textArea = textLoader.load();
            // Search options block.
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(Resources.FXMLtop + "SearchOptionsBlock.fxml"));
            loader.setController(new SearchOptionsController(this.actionsInterface));
            Node SearchOptionsBlock = loader.load();

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
    public void searchFiles() {
        System.out.println(textArea.getParagraphs());
    }
}
