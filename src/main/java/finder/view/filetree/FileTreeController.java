package finder.view.filetree;

import finder.util.FinderAction;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;

public class FileTreeController {

    ;

    @FXML
    private SplitPane fileTreePane;

    public FileTreeController() {

    }

    @FXML
    private void initialize() {
        FinderAction.getInstance().setFileTreePane(fileTreePane);
        try {
            // Main layout:
            fileTreePane.setOrientation(Orientation.VERTICAL);
            fileTreePane.setDividerPosition(0,0.0);

            // Loading top pane.
            SplitPane addUrl = FinderAction.getInstance().load(this.getClass().getResource
                    (Resources.FXMLFileTree + "add_url.fxml"), new AddUrlController());

            // Loading bot pane.
            TreeView SplitBottom = FinderAction.getInstance().load(this.getClass().getResource
                    (Resources.FXMLFileTree + "tree.fxml"), new TreeController());
            SplitBottom.getStylesheets().add(Resources.CSS + "tree_view.css");

            // Adding top and bottom parts to main layout.
            fileTreePane.getItems().addAll(addUrl, SplitBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}