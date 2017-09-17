package finder.model;

import finder.util.Resources;
import finder.view.WarningWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by antmog on 14.09.2017.
 */
public class WarningWindow {
    public WarningWindow(String warningText) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Resources.FXML + "WarningWindow.fxml"));
            loader.setController(new WarningWindowController(warningText, stage));

            Scene scene = new Scene(loader.load());

            stage.setTitle("Warning window");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Failed to create new Window.");
            e.printStackTrace();
        }
    }
}
