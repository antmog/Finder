package finder;

import finder.model.ActionsInterface;
import finder.view.InitialScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Platform;
import finder.util.Resources;

import java.awt.*;
import java.util.Enumeration;
import java.util.ResourceBundle;


public class Finder extends Application{

    private static Stage stage;
    private TextArea textArea;
    private ActionsInterface actionsInterface = new ActionsInterface() {
        @Override
        public void actionClick() {
            searchFiles();
        }

        @Override
        public void actionGetTextArea(TextArea text) {
            textArea = text;
        }


    };

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
            loader.setController(new InitialScreenController(actionsInterface));

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

    public void searchFiles() {
        // данные из дерева +
        // данные из options block +
        // данные из text area
        //
        System.out.println(textArea.getText());
    }
}
