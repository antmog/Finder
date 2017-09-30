package finder.view.filetree;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;

public class FileTreeController {

    private FinderActionInterface finderActionInterface;

    @FXML
    private SplitPane fileTreePane;

    public FileTreeController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {
        finderActionInterface.setFileTreePane(fileTreePane);
        try {
            // Main layout:
            fileTreePane.setOrientation(Orientation.VERTICAL);
            fileTreePane.setDividerPosition(0,0.0);

            // Loading top pane.
            SplitPane addUrl = finderActionInterface.load(this.getClass().getResource
                    (Resources.FXMLFileTree + "add_url.fxml"), new AddUrlController(this.finderActionInterface));

            // Loading bot pane.
            TreeView SplitBottom = finderActionInterface.load(this.getClass().getResource
                    (Resources.FXMLFileTree + "tree.fxml"), new TreeController(this.finderActionInterface));
            SplitBottom.getStylesheets().add(Resources.CSS + "tree_view.css");

            // Adding top and bottom parts to main layout.
            fileTreePane.getItems().addAll(addUrl, SplitBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}