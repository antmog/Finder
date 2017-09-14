package finder.view.SplitRight.SplitBottom;

import finder.model.ActionsInterface;
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

public class TabsController {

    private ActionsInterface actionsInterface;

    public TabsController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }

    @FXML
    private TabPane tabPane;


    @FXML
    private void initialize() {
        actionsInterface.actionSetTabPane(tabPane);
    }
}