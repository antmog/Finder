package finder.view.SplitRight.SplitTop;

import finder.util.FinderActionInterface;
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
        finderActionInterface.actionSetTextArea(textSearchArea);
    }
}