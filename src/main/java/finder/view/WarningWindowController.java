package finder.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WarningWindowController {

    private Stage stage;
    private String warningText;

    @FXML
    private Label warningMessage;

    public WarningWindowController(String warningText, Stage stage) {
        this.warningText = warningText;
        this.stage = stage;
    }


    @FXML
    private void initialize() {
        warningMessage.setText(warningText);
    }

    @FXML
    private void close() {
        stage.close();
    }
}
