package finder;

import finder.model.FinderInstance;
import finder.util.FinderActionInterface;
import finder.util.Resources;
import finder.view.InitialScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Finder extends Application {

    private static Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        Finder.stage = stage;
        Finder.stage.setTitle("Finder");
        Finder.stage.initStyle(StageStyle.UNIFIED);
        Finder.stage.getIcons().add(new Image(getClass().getResource(Resources.IMG + "icon.png").toString()));
        Finder.stage.setOnCloseRequest(event -> {
            Platform.exit();
            //System.exit(0);
        });
        FinderInstance finderInstance = new FinderInstance();
        FinderActionInterface finderActionInterface = new FinderActionInterface(finderInstance);
        //
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.err.println("Got exception in thread.");
            e.printStackTrace();
        });
        try {
            // Load main layout from fxml file.
            SplitPane view = finderActionInterface.load(getClass().getResource
                    (Resources.FXML + "initial_screen.fxml"), new InitialScreenController(finderActionInterface));
            view.getStylesheets().add(Resources.CSS + "finder.css");
            // Shows the scene containing the main layout.
            Scene scene = new Scene(view);
            scene.getStylesheets().add(Resources.CSS + "main_window.css");
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            Platform.exit();
            //System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(Finder.class);
    }

}
