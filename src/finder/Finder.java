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
    private FinderInstance finderInstance;
    private FinderActionInterface finderActionInterface;


    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("qq");
        Finder.stage = stage;
        Finder.stage.setTitle("Finder");
        Finder.stage.initStyle(StageStyle.UNIFIED);
        Finder.stage.getIcons().add(new Image(getClass().getResource(Resources.IMG + "Icon.png").toString()));
        Finder.stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        finderInstance = new FinderInstance();
        finderActionInterface = new FinderActionInterface(finderInstance);
        // doesnt work :(( WTF :( shock
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            //searchButton.setText("Search");
            //searchButton.arm();
            //for tab.setText("TabName"): add ThreadId field to CustromTab => find tab where ThreadID = exception t.ID =>
            //and set name of tab to normal instead of "Loading" or just close it
            System.out.println("got it");
            e.printStackTrace();
        });
        try {
            // Load main layout from fxml file.
            SplitPane view = finderActionInterface.load(getClass().getResource(Resources.FXML + "InitialScreen.fxml"), new InitialScreenController(finderActionInterface));
            view.getStylesheets().add(Resources.CSS + "Finder.css");
            // Shows the scene containing the main layout.
            Scene scene = new Scene(view);
            scene.getStylesheets().add(Resources.CSS + "MainWindow.css");
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(Finder.class);
    }

}
