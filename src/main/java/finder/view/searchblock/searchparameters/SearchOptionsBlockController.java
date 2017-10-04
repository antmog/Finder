package finder.view.searchblock.searchparameters;

import finder.util.FinderAction;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;



/**
 * Controller for SearchOptions part of application.
 */
public class SearchOptionsBlockController {
    private ObservableList<String> tableData = FXCollections.observableArrayList();

    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button delButton;

    // Text field where user enters extension.
    @FXML
    private TextField addExtensionText;

    // Linking table.
    @FXML
    private TableView<String> tableExtensions;

    @FXML
    private TableColumn<String, String> extensionsColumn;


    public SearchOptionsBlockController() {

    }


    /**
     * Initialising table.
     */
    @FXML
    private void initialize() {
        FinderAction.getInstance().getFinderInstance().setAddButton(addButton);
        FinderAction.getInstance().getFinderInstance().setDelButton(delButton);
        FinderAction.getInstance().getFinderInstance().setSearchButton(searchButton);
        FinderAction.getInstance().getFinderInstance().setAddExtensionTextField(addExtensionText);
        initData();
        FinderAction.getInstance().setTableData(tableData);
        // Selecting column type.
        extensionsColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        // Adding data to table.
        tableExtensions.setItems(tableData);
        tableExtensions.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Adding extension to search parameters and refreshing table with extensions in search block.
     */
    @FXML
    private void addExtension() {
        if (!tableData.contains(addExtensionText.getText())) {
            tableData.add(addExtensionText.getText());
        }
        FinderAction.getInstance().setTableData(tableData);
    }

    /**
     * Deleting selected extension from search list and refreshing table.
     */
    @FXML
    private void delSelectedExtension() {
        String selectedItem = tableExtensions.getSelectionModel().getSelectedItem();
        tableExtensions.getItems().remove(selectedItem);
        FinderAction.getInstance().setTableData(tableData);
    }

    /**
     * Preparing initial data for table
     */
    private void initData() {
        tableData.add("log");
        tableData.add("txt");
    }

    /**
     * Searching files (button action).
     */
    @FXML
    private void searchFiles() {
        FinderAction.getInstance().actionClickSearch();
    }
}
