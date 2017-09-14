package finder.view.SplitRight.SplitTop;

import finder.model.ActionsInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TextAreaController {

    @FXML
    private TextArea textSearchArea;

    private ActionsInterface actionsInterface;

    public TextAreaController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private void initialize()
    {
        actionsInterface.actionSetTextArea(textSearchArea);
    }
}