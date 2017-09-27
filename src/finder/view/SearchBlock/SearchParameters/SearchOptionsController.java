package finder.view.SearchBlock.SearchParameters;

import finder.util.FinderActionInterface;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Controller for SearchOptions part of application.
 */
public class SearchOptionsController {
    private ObservableList<String> tableData = FXCollections.observableArrayList();


    private FinderActionInterface finderActionInterface;

    @FXML
    private Button searchFilesButton;

    // Text field where user enters extension.
    @FXML
    private TextField addExtensionText;

    // Linking table.
    @FXML
    private TableView<String> tableExtensions;

    @FXML
    private TableColumn<String, String> extensionsColumn;


    public SearchOptionsController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }


    /**
     * Initialising table.
     */
    @FXML
    private void initialize() {
        initData();
        // Selecting column type.
        extensionsColumn.setCellValueFactory(p -> new ReadOnlyObjectWrapper(p.getValue()));

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
    }

    /**
     * Deleting selected extension from search list and refreshing table.
     */
    @FXML
    private void delSelectedExtension() {
        String selectedItem = tableExtensions.getSelectionModel().getSelectedItem();
        tableExtensions.getItems().remove(selectedItem);
    }

    /**
     * Preparing initial data for table
     */
    private void initData() {
        tableData.add("log");
    }

    /**
     * Searching files (button action).
     */
    @FXML
    private void searchFiles() {
        finderActionInterface.actionSetTableData(tableData);
        finderActionInterface.actionClickSearch();
    }
}
