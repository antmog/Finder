package finder.view;

import finder.model.Extension;
import finder.util.Resources;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.scene.control.TableColumn;



public class CommonController {

    @FXML
    private TabPane tabPane ;

    @FXML
    private void addTab() {
        int numTabs = tabPane.getTabs().size();
        try {
            Tab tab = new Tab("Tab "+(numTabs+1));
            tabPane.getTabs().add(tab);
            tab.setContent(FXMLLoader.load(this.getClass().getResource(Resources.FXML + "TabTemplate.fxml")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}