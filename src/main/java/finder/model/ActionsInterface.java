package finder.model;


import javafx.scene.control.TreeView;

import java.io.IOException;
import java.net.URL;

public interface ActionsInterface {
    /**
     * Method, loading elements of application.
     *
     * @param controller Controller for that element.
     * @param <T>        Type of loaded element.
     * @param url        urL of the element.
     * @return Element.
     * @throws IOException
     */
     <T,T1> T load(URL url, T1 controller) throws IOException;

    /**
     * Action performed on button "Search" click.
     */
    void actionClickSearch();

    /**
     * Delete selected tree node from main tree.
     */
    void deleteSelectedTreeNode();

    /**
     * @param tree to listen
     */
    void deleteSelectionListener(ResultFileTree<String> tree);

    /**
     * @param tree to listen
     */
    void addSelectionListener(ResultFileTree<String> tree);
}
