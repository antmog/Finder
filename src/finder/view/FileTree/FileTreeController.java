package finder.view.FileTree;

import com.sun.org.apache.xpath.internal.operations.Or;
import finder.util.FinderActionInterface;
import finder.util.OtherLogic;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.AnchorPane;

import java.io.File;

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
            SplitPane addUrl = finderActionInterface.load(this.getClass().getResource(Resources.FXMLFileTree + "AddUrl.fxml"), new AddUrlController(this.finderActionInterface));

            // Loading bot pane.
            TreeView SplitBottom = finderActionInterface.load(this.getClass().getResource(Resources.FXMLFileTree + "Tree.fxml"), new TreeController(this.finderActionInterface));
            SplitBottom.getStylesheets().add(Resources.CSS + "TreeView.css");

            // Adding top and bottom parts to main layout.
            fileTreePane.getItems().addAll(addUrl, SplitBottom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}