package finder.model;
import finder.util.Resources;
import finder.view.WarningWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Warning window class. Default constructor creates warning window with specified text.
 */
public class WarningWindow {
    public WarningWindow(String warningText) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Resources.FXML + "warning_window.fxml"));
            loader.setController(new WarningWindowController(warningText, stage));

            Scene scene = new Scene(loader.load());

            stage.setTitle("Warning window");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println("Failed to create new Window.");
            e.printStackTrace();
        }
    }
}
