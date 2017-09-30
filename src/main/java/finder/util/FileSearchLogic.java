package finder.util;

import finder.model.FinderInstance;
import finder.model.TaskExecutor;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class FileSearchLogic {
    /**
     * Searching files full action.
     **/
    public static void searchFiles(FinderActionInterface finderActionInterface) {
        FinderInstance finderInstance = finderActionInterface.getFinderInstance();
        // text from textArea
        StringBuffer sb = new StringBuffer(finderInstance.getTextArea().getText());
        // list for keeping search results, contains:
        // - Selected folders in main tree (where to search)
        // - Files that meet search requirements
        ArrayList<File> listOfDirs = new ArrayList<>();
        // array of extensions of files program is searching for
        Object[] extensions = finderInstance.getTableData().toArray();
        TaskExecutor.getInstance().executeTask(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // searching files and making form for result tree
                searchInTree(finderInstance.getFileTree().getRoot(), listOfDirs, sb, extensions);
                Platform.runLater(() -> {
                    // generating result tree
                    ResultTreeCreateLogic.showResultFileTree(sb, listOfDirs, finderActionInterface);
                    finderInstance.getSearchOptionsBlock().setDisable(false);
                    finderInstance.getFileTreePane().setDisable(false);
                });
                return null;
            }
        });
    }

    /**
     * Searching selected folders and searching files in selected folders (+subfolders) (result -> ListOfDirs).
     *
     * @param parentItem Parent root item.
     * @param listOfDirs List of directories containing files we search for (and files).
     * @param sb         Text to search.
     * @param extensions Extensions of target files.
     */
    private static void searchInTree(TreeItem<String> parentItem, ArrayList<File> listOfDirs, StringBuffer sb,
                                     Object[] extensions) {
        for (TreeItem<String> child : parentItem.getChildren()) {
            CheckBoxTreeItem<String> checkbox = (CheckBoxTreeItem<String>) child;
            if (checkbox.isSelected()) {
                // if folder is selected - search in this folder + subfolders
                File dir = new File(FileSystemLogic.getFilePath(checkbox));
                search(dir, listOfDirs, sb, extensions);
            } else {
                // if folder is not selected - search for selected folders in main fileTree
                searchInTree(child, listOfDirs, sb, extensions);
            }
        }
    }

    /**
     * Searching files (result -> ListOfDirs) (including subfolders).
     *
     * @param dir        Directory mathing one of checkboxes.
     * @param listOfDirs List of directories containing files we search for (and files).
     * @param sb         Text to search.
     * @param extensions Extensions of target files.
     */
    private static void search(File dir, ArrayList<File> listOfDirs, StringBuffer sb, Object[] extensions) {
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    for (Object object : extensions) {
                        String extension = (String) object;
                        if (FilenameUtils.getExtension(file.getName()).equals(extension)) {
                            //Check if there is "sb" text in file
                            if (searchInFile(file, sb)) {
                                //add file if found text
                                listOfDirs.add(file);
                            }
                        }
                    }
                }
                search(file, listOfDirs, sb, extensions);
            }
            listOfDirs.add(dir);
        }
    }

    /**
     * Search text (sb) in File (file).
     *
     * @param file File.
     * @param sb   Text.
     * @return true if text found/false if didn't.
     */
    private static boolean searchInFile(File file, StringBuffer sb) {
        boolean find = false;
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains(sb.toString())) {
                    find = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return find;
    }
}
