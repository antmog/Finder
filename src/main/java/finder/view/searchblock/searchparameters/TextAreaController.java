package finder.view.searchblock.searchparameters;

import finder.util.FinderAction;
import finder.util.Resources;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TextAreaController {

    @FXML
    private TextArea textSearchArea;

    ;

    public TextAreaController() {

    }

    @FXML
    private void initialize() {
        textSearchArea.getStylesheets().add(Resources.CSS + "text_search_area.css");
        FinderAction.getInstance().setTextArea(textSearchArea);
    }
}