package finder.util;

import finder.model.FinderInstance;
import finder.model.TaskExecutor;
import finder.view.searchblock.searchparameters.SearchOptionsBlockController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class FileSearchLogic {
    /**
     * Searching files full action.
     **/
    public static void searchFiles(ExecutorService exec) {
        FinderInstance finderInstance = FinderAction.getInstance().getFinderInstance();
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
                    if(finderInstance.getSearchButton().getText().equals("Stop")){
                        ResultTreeCreateLogic.showResultFileTree(sb, listOfDirs);
                    }
                    stopSearch(exec);
                });
                return null;
            }
        });
    }

    public static void stopSearch(ExecutorService exec){
        FinderInstance finderInstance = FinderAction.getInstance().getFinderInstance();
        finderInstance.getAddButton().setDisable(false);
        finderInstance.getDelButton().setDisable(false);
        finderInstance.getAddExtensionTextField().setDisable(false);
        finderInstance.getFileTreePane().setDisable(false);
        finderInstance.getSearchButton().setText("Search");
        finderInstance.getSearchButton().setOnAction(event -> FinderAction.getInstance().actionClickSearch());
        try {
            finderInstance.getSearchInFileReader().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exec.shutdown();

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
                            // MTT HERE

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
            System.out.println(e.getMessage()); // button STOP SEARCH causes stream to be closed.
        }
        return find;
    }
}
