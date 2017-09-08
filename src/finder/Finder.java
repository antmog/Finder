package finder;

import com.sun.org.apache.bcel.internal.generic.CASTORE;
import finder.model.ActionsInterface;
import finder.model.Extension;
import finder.view.InitialScreenController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Platform;
import finder.util.Resources;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.ResourceBundle;


public class Finder extends Application{

    private static Stage stage;
    private TextArea textArea;
    private TreeView fileTree;
    private ObservableList<Extension> tableData;
    private ActionsInterface actionsInterface = new ActionsInterface() {
        @Override
        public void actionClick() {
            searchFiles();
        }

        @Override
        public void actionGetTextArea(TextArea text) {
            textArea = text;
        }

        @Override
        public void actionGetTree(TreeView tree) {
            fileTree = tree;
        }

        @Override
        public void actionGetTableData(ObservableList<Extension> loadedTableData) {
            tableData = loadedTableData;
        }

        @Override
        public String getFilePath(CheckBoxTreeItem<String> item) {
            StringBuffer filePath = new StringBuffer();
            filePath.append(item.getValue());
            while(item.getParent()!=null){
                item = (CheckBoxTreeItem<String>) item.getParent();
                if(!item.getValue().endsWith("\\")){
                    filePath.insert(0,item.getValue() + "\\");
                }else if(!item.getValue().equals("Roots:" + File.separator)){
                    filePath.insert(0,item.getValue());
                }
            }
            return filePath.toString();
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
        StringBuffer sb = new StringBuffer(textArea.getText());
        ArrayList<String> searchDirList = new ArrayList<>();
        searchInTree(fileTree.getRoot(),searchDirList);
        System.out.println(searchDirList.toString());

        for (Object extension:tableData.toArray()){
            Extension ext = (Extension) extension;
            System.out.println(ext.getExtension());
        }

        System.out.println(sb);
    }
    private void searchInTree(TreeItem<String> rootItem, ArrayList<String> listOfDirs){
        castAndSearch(rootItem,listOfDirs);
        for(TreeItem<String> child : rootItem.getChildren()){
            if(child.getChildren().isEmpty()){
                castAndSearch(child,listOfDirs);
            } else {
                searchInTree(child,listOfDirs);
            }
        }
    }

    private void castAndSearch(TreeItem<String> treeItem, ArrayList<String> listOfDirs){
        CheckBoxTreeItem<String> checkbox = (CheckBoxTreeItem<String>) treeItem;
        if(checkbox.isSelected()){
            listOfDirs.add(actionsInterface.getFilePath(checkbox));
        }
    }
}
