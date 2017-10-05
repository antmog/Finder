package finder.util.tasks;

import finder.model.FinderInstance;
import finder.util.FinderAction;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Search text (sb) in File (file).
 *
 */
public class SearchInFile extends Task<Boolean> {
    private StringBuffer sb;
    private File file;

    public SearchInFile(StringBuffer sb, File file) {
        this.sb = sb;
        this.file = file;
    }

    public Boolean call()  {
        boolean find = false;
        try (BufferedReader searchInFileReader = new BufferedReader(new FileReader(file))) {
            FinderInstance finderInstance = FinderAction.getInstance().getFinderInstance();
            finderInstance.setSearchInFileReader(searchInFileReader);
            String line;
            while ((line = searchInFileReader.readLine()) != null) {
                if (line.contains(sb.toString())) {
                    find = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());// button STOP SEARCH causes stream to be closed.
        }
        return find;
    }
}
