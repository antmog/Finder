package finder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Platform;
import finder.util.Resources;


public class Finder extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception{
        Finder.stage = stage;
        Finder.stage.setTitle("Finder");
        Finder.stage.getIcons().add(new Image(getClass().getResource(Resources.IMG + "Icon.png").toString()));
        Finder.stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        try{
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Resources.FXML + "InitialScreen.fxml"));
            SplitPane view = loader.load();

            // Shows the scene containing the main layout.
            Scene scene = new Scene(view);
            stage.setScene(scene);
            stage.show();

        }catch(Exception ex){
            ex.printStackTrace();
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        launch(Finder.class);
    }
}
