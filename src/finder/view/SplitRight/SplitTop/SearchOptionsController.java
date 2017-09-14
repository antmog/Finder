package finder.view.SplitRight.SplitTop;

import finder.model.ActionsInterface;
import finder.model.Extension;
import finder.view.InitialScreenController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller for SearchOptions part of application.
 * Created by antmog on 05.09.2017.
 */
public class SearchOptionsController {
    private ObservableList<Extension> tableData = FXCollections.observableArrayList();

    private ActionsInterface actionsInterface;

    // Text field where user enters extension.
    @FXML
    private TextField addExtensionText;

    // Linking table.
    @FXML
    private TableView<Extension> tableExtensions;

    @FXML
    private TableColumn<Extension, String> extensionsColumn;


    public SearchOptionsController(ActionsInterface actionsInterface){
        this.actionsInterface = actionsInterface;
    }


    /**
     * Initialising table.
     */
    @FXML
    private void initialize() {
        initData();

        // Selecting column type.
        extensionsColumn.setCellValueFactory(new PropertyValueFactory<Extension, String>("extension"));

        // Adding data to table.
        tableExtensions.setItems(tableData);
    }

    /**
     * Adding extension to search parameters and refreshing table with extensions in search block.
     */
    @FXML
    private void addExtension() {
        tableData.add(new Extension(addExtensionText.getText()));
    }

    /**
     * Deleting selected extension from search list and refreshing table.
     */
    @FXML
    private void delSelectedExtension() {
        Extension selectedItem = tableExtensions.getSelectionModel().getSelectedItem();
        tableExtensions.getItems().remove(selectedItem);
    }

    /**
     * Preparing initial data for table
     */
    private void initData() {
        tableData.add(new Extension("log"));
    }

    /**
     * Searching files (button action).
     */
    @FXML
    private void searchFiles() {
        actionsInterface.actionSetTableData(tableData);
        actionsInterface.actionClick();
    }
}
