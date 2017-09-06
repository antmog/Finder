package finder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.application.Platform;

import finder.util.Resources;
import org.apache.commons.io.FilenameUtils;
import sun.plugin.javascript.navig.Anchor;

import java.io.File;

import static java.lang.Thread.sleep;

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

            // Initialising TOP part of interface.
            SplitPane SplitTop = new SplitPane();

            // Elements of top part of interface.
            // Search text area.
            TextArea textArea = FXMLLoader.load(this.getClass().getResource(Resources.FXMLtop + "TextArea.fxml"));
            // Search options block.
            Node SearchOptionsBlock = FXMLLoader.load(this.getClass().getResource(Resources.FXMLtop + "SearchOptionsBlock.fxml"));
            // Wrapping search options block into Anchor Pane (for better visualisation).
            AnchorPane rightAnchor = new AnchorPane();
            rightAnchor.getChildren().add(SearchOptionsBlock);
            AnchorPane.setTopAnchor(SearchOptionsBlock,5.0);

            // Link style ID's for sub-elements of top part.
            SplitTop.getStyleClass().add("splitSearch");
            textArea.getStyleClass().add("searchText");
            SearchOptionsBlock.getStyleClass().add("SearchOptionsBlock");

            // Filling top part of interface with sub-elements.
            SplitTop.setOrientation(Orientation.HORIZONTAL);
            SplitTop.getItems().addAll(textArea,rightAnchor);

            // Initialising BOTTOM part of interface.
            SplitPane SplitBottom = new SplitPane();
            SplitBottom.setOrientation(Orientation.HORIZONTAL);

            // Elements of bottom part of interface.
            // Initialize file tree.
            TreeView<String> treeView = FXMLLoader.load(this.getClass().getResource(Resources.FXMLbot + "FileTree.fxml"));

            Button tt = new Button();
            // Filling bottom part of interface with sub-elements.
            SplitBottom.getItems().addAll(treeView, tt);

            // Adding top and bottom barts to main layout.
            view.getItems().addAll(SplitTop, SplitBottom);
            view.getStylesheets().add(Resources.CSS + "InitialScreen.css");

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

    private static void createTree(File file, CheckBoxTreeItem<String> parent) {
        if (file.isDirectory()) {
            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(file.getName());
            parent.getChildren().add(treeItem);
            if(file.listFiles()!=null){
                for (File f : file.listFiles()) {
                  createTree(f, treeItem);
                }
            }
        } else if (".php".equals(FilenameUtils.getExtension(file.toString()))) {
            parent.getChildren().add(new CheckBoxTreeItem<>(file.getName()));
        }
    }
}
