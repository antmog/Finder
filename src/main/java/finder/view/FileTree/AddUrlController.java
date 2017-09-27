package finder.view.FileTree;

import finder.model.WarningWindow;
import finder.util.FinderActionInterface;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;


public class AddUrlController {
    private FinderActionInterface finderActionInterface;

    @FXML
    private SplitPane addUrlPane;

    public AddUrlController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {

        try {
            // Main layout:
            addUrlPane.setOrientation(Orientation.HORIZONTAL);
            addUrlPane.getStylesheets().add(Resources.CSS + "add_url.css");

            // Elements:
            TextField addUrlText = new TextField();
            Button addUrlButton = new Button("Add URL");
            addUrlButton.setOnAction(event -> new WarningWindow(finderActionInterface.addUrlRoot(addUrlText.getText())));
            Button delNodeButton = new Button("Remove");
            delNodeButton.setOnAction(event -> finderActionInterface.deleteSelectedTreeNode());
            // Adding elements to main layout.
            addUrlPane.getItems().addAll(addUrlText, addUrlButton,delNodeButton);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
