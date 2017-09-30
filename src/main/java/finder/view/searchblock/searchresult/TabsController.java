package finder.view.searchblock.searchresult;

import finder.util.FinderActionInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class TabsController {

    private FinderActionInterface finderActionInterface;

    public TabsController(FinderActionInterface finderActionInterface) {
        this.finderActionInterface = finderActionInterface;
    }

    @FXML
    private TabPane tabPane;


    @FXML
    private void initialize() {
        finderActionInterface.setTabPane(tabPane);
    }
}