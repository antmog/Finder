package finder.view.filetree;

import finder.model.WarningWindow;
import finder.util.FinderAction;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;


public class AddUrlController {
    ;

    @FXML
    private SplitPane addUrlPane;

    public AddUrlController() {

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
            addUrlButton.setOnAction(event -> new WarningWindow(FinderAction.getInstance().addUrlRoot(addUrlText.getText())));
            Button delNodeButton = new Button("Remove");
            delNodeButton.setOnAction(event -> FinderAction.getInstance().deleteSelectedTreeNode());
            // Adding elements to main layout.
            addUrlPane.getItems().addAll(addUrlText, addUrlButton,delNodeButton);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
