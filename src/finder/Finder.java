package finder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.application.Platform;

import finder.util.Resources;
import sun.plugin.javascript.navig.Anchor;

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

            // Creating sub-elements for the scene.
            // First (top) part of interface.
            SplitPane SplitSearch = new SplitPane();
            TextArea textArea = new TextArea();
            Node SearchOptionsBlock = FXMLLoader.load(this.getClass().getResource(Resources.FXML + "SearchOptionsBlock.fxml"));
            AnchorPane rightAnchor = new AnchorPane();

            rightAnchor.getChildren().add(SearchOptionsBlock);
            SplitSearch.setOrientation(Orientation.HORIZONTAL);
            AnchorPane.setTopAnchor(SearchOptionsBlock,5.0);

            // Link style ID's for sub-elements.
            SplitSearch.getStyleClass().add("splitSearch");
            textArea.getStyleClass().add("searchText");
            SearchOptionsBlock.getStyleClass().add("SearchOptionsBlock");
            // Filling top part of interface with sub-elements.
            SplitSearch.getItems().addAll(textArea,rightAnchor);

            // Bottom part of interface.
            Button qq = new Button();

            // Making full main scene.
            view.getItems().addAll(SplitSearch, qq);
            view.getStylesheets().add(Resources.CSS + "InitialScreen.css");

            // Shows the scene containing the layout.
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
