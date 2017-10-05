package finder.util;

import finder.model.FinderInstance;
import finder.model.TaskExecutor;
import finder.util.tasks.SearchInFile;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Phaser;

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
        exec.execute(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // searching files and making form for result tree
                // creating phaser to wait for every file loaded
                Phaser phaser = new Phaser(1);
                searchInTree(finderInstance.getFileTree().getRoot(), listOfDirs, sb, extensions, exec, phaser);
                phaser.arriveAndAwaitAdvance();
                Platform.runLater(() -> {
                    // generating result tree if search wasn't stopped
                    if(finderInstance.getSearchButton().getText().equals("Stop")){
                        // sort search data for correct result tree generating
                        TreeSet<File> SortedListOfDirs = new TreeSet<>(listOfDirs);
                        ResultTreeCreateLogic.showResultFileTree(sb, SortedListOfDirs);
                    }
                    stopSearch(exec);
                });
                return null;
            }
        });
    }

    /**
     * Stops current search in fileTree (closes stream).
     * @param exec ExecutorService which runs the task which is going to be stopped.
     */
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
        } catch(NullPointerException e){
            // no reader to close
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
                                     Object[] extensions, ExecutorService exec,Phaser phaser) {
        for (TreeItem<String> child : parentItem.getChildren()) {
            CheckBoxTreeItem<String> checkbox = (CheckBoxTreeItem<String>) child;
            if (checkbox.isSelected()) {
                // if folder is selected - search in this folder + subfolders
                File dir = new File(FileSystemLogic.getFilePath(checkbox));
                search(dir, listOfDirs, sb, extensions, exec, phaser);
            } else {
                // if folder is not selected - search for selected folders in main fileTree
                searchInTree(child, listOfDirs, sb, extensions, exec, phaser);
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
    private static void search
    (File dir, ArrayList<File> listOfDirs, StringBuffer sb, Object[] extensions, ExecutorService exec, Phaser phaser) {
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    for (Object object : extensions) {
                        String extension = (String) object;
                        if (FilenameUtils.getExtension(file.getName()).equals(extension)) {
                            // check if file contains search text (in task)
                            SearchInFile task = new SearchInFile(sb,file);
                            task.setOnSucceeded(event -> {
                                // add file to result list if text found, "countdown" phaser
                                if (task.getValue()) {
                                    listOfDirs.add(file);
                                }
                                phaser.arriveAndDeregister();
                            });
                            // 'countup' phaser and start task
                            phaser.register();
                            exec.execute(task);
                        }
                    }
                }
                search(file, listOfDirs, sb, extensions, exec, phaser);
            }
            listOfDirs.add(dir);
        }
    }



}
