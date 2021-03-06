package finder.util;

import finder.model.FileTab;
import finder.model.FinderInstance;
import finder.view.searchblock.searchresult.TabTemplateController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.File;

public class TabLogic {
    public static void addTab(File file, String searchText, FinderInstance finderInstance) {
        int numTabs = 1;
        // calculating next tab number
        if (finderInstance.getResultTabPane().getTabs() != null) {
            numTabs = finderInstance.getResultTabPane().getTabs().size() + 1;
        }
        // creating customTab object with following parameters
        FileTab tab = new FileTab(numTabs, file.getName(), finderInstance.getResultTabPane(), file, searchText);
        try {
            // loading TabTemplate (view)
            FXMLLoader anchorLoader = new FXMLLoader(tab.getClass().getResource
                    (Resources.FXMLSearchResult + "tab_template.fxml"));
            anchorLoader.setController(new TabTemplateController(tab));
            AnchorPane anchor = anchorLoader.load();
            // setting that view for created tab
            tab.setContent(anchor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
