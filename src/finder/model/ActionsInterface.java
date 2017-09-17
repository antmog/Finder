package finder.model;


import java.io.IOException;
import java.net.URL;

public interface ActionsInterface {
    /**
     * Method, loading elements of application.
     *
     * @param url        urL of the element.
     * @param controller Controller for that element.
     * @param <T>        Type of loaded element.
     * @return Element.
     * @throws IOException
     */
    <T> T load(URL url, Object controller) throws IOException;

    /**
     * Action performed on button "Search" click.
     */
    void actionClickSearch();

}
