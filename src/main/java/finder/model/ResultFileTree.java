package finder.model;
import javafx.scene.control.TreeView;

/**
 * ResultFileTree object, contains search text from main TextArea.
 * Usefull when many files opened in tabs and each tab opened from different search results
 * so all tabs opened from exact ResultFileTree has same searchText.
 */
public class ResultFileTree<T> extends TreeView<T>{
    private String searchText;

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

}
