package finder.view.SearchBlock.SearchParameters;

import finder.util.FinderActionInterface;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TextAreaController {

    @FXML
    private TextArea textSearchArea;

    private FinderActionInterface finderActionInterface;

    public TextAreaController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private void initialize() {
        textSearchArea.getStylesheets().add(Resources.CSS + "TextSearchArea.css");
        finderActionInterface.actionSetTextArea(textSearchArea);
    }
}