package finder.view.searchblock.searchresult;

import finder.util.FinderAction;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;

public class TabsController {

    ;

    public TabsController() {

    }

    @FXML
    private TabPane tabPane;


    @FXML
    private void initialize() {
        FinderAction.getInstance().setTabPane(tabPane);
    }
}