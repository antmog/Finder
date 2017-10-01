package finder.util;

import java.io.File;

/**
 * Resources for the project.
 */
public final class Resources {

    public static final String FXML = File.separator + "fxml" + File.separator;
    public static final String FXMLSearchParameters = File.separator + "fxml"+File.separator+
            "search_block"+File.separator+"search_parameters" + File.separator;
    public static final String FXMLSearchResult = File.separator + "fxml"+
            File.separator+"search_block"+File.separator+"search_result" + File.separator;
    public static final String FXMLFileTree = File.separator + "fxml"+
            File.separator+"file_tree" + File.separator;
    public static final String FXMLSearchBlock = File.separator + "fxml"+File.separator
            +"search_block" + File.separator;

    public static final String IMG =  File.separator + "img" + File.separator;
    public static final String CSS =  File.separator + "css" + File.separator;

    private Resources() {
    }
}