package finder.model;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

/**
 * ResultFileTree object, contains search text from main TextArea.
 * Usefull when many files opened in tabs and each tab opened from different search results
 * so all tabs opened from exact ResultFileTree has same searchText.
 */
public class ResultFileTree {
    private TreeView resultTree;
    private String searchText;

    public ResultFileTree(TreeView resultTree) {
        this.resultTree = resultTree;
    }

    public void setRoot(TreeItem<String> root) {
        this.resultTree.setRoot(root);
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}
