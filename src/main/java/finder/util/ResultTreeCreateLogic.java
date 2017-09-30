package finder.util;

import finder.model.FinderInstance;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ResultTreeCreateLogic {
    /**
     * Generate and show result tree.
     *
     * @param listOfDirs List of directories containing files we search for (and files).
     * @param sb         Text to search.
     */
    public static void showResultFileTree(StringBuffer sb, List<File> listOfDirs, FinderInstance finderInstance) {
        // creating new tree from root of main tree (where folders to search are selected)
        // suppress warning? i swear finderInstance.getFileTree().getRoot() is a tree item
        TreeItem<String> resultRootItem = new TreeItem<>((finderInstance.getFileTree().getRoot()).getValue());
        generateResultTree(resultRootItem, listOfDirs);
        finderInstance.getResultFileTree().setSearchText(sb.toString());
        // clear result tree
        finderInstance.getResultFileTree().setRoot(resultRootItem);
    }

    /**
     * Adding search results one by one INCLUDING FILEPATH as tree nodes (if we have D:\folder1\file1 and
     * D:\folder1\file2 then manually adding
     * full file path as tree nodes: D:\ D:\folder1 D:\folder1\file1/2( + checking if tree node alrdy exists) ).
     *
     * @param resultRootItem Result root item for result tree.
     * @param listOfDirs     Result of search.
     */
    private static void generateResultTree(TreeItem<String> resultRootItem, List<File> listOfDirs) {
        ArrayList<CheckBoxTreeItem<String>> listOfItems = new ArrayList<>(); // temp list to keep full path to file
        // for example for file  "D:\folder1\file1" : "D:\" + "D:\folder1" + "D:\folder1\file1"

        for (File file : listOfDirs) { // for each file (folder/file) in listOfDirs (result of search)
            // filling temp list with values
            listOfItems.add(new CheckBoxTreeItem<>(file.getAbsolutePath()));
            while (file.getParent() != null) {
                file = file.getParentFile();
                listOfItems.add(new CheckBoxTreeItem<>(file.getAbsolutePath()));
            }
            // adding current item
            addItems(listOfItems, resultRootItem);
        }
    }

    /**
     * Adding items to result tree.
     *
     * @param listOfItems    Structure of item: for example for file "D:\folder1\file1" its :
     *                      "D:\" + "D:\folder1" + "D:\folder1\file1".
     * @param resultRootItem Root item for result tree.
     */
    private static void addItems(List<CheckBoxTreeItem<String>> listOfItems, TreeItem<String> resultRootItem) {
        boolean noRepeat = true; // variable to check if tree node alrdy exists
        if (!listOfItems.isEmpty()) {
            // another item to check/add (compare with current items and check if it already exists)
            CheckBoxTreeItem<String> compareItem = listOfItems.remove(listOfItems.size() - 1);
            File compareItemFile = new File(compareItem.getValue());
            if (resultRootItem.getChildren().isEmpty()) {
                // if tree node has no children - the tree node we are going to add 100% doesn't exist
                addItem(resultRootItem, compareItemFile);
            } else {
                // otherwise we check it manually
                for (TreeItem<String> item : resultRootItem.getChildren()) {
                    if (item.getValue().equals(FileSystemLogic.getShortFileName(compareItemFile))) {
                        noRepeat = false;
                        break;
                    }
                }
                // and if it doesn't exist
                if (noRepeat) {
                    addItem(resultRootItem, compareItemFile);
                }
            }
            resultRootItem = resultRootItem.getChildren().get(resultRootItem.getChildren().size() - 1);
            addItems(listOfItems, resultRootItem);
        }
    }

    /**
     * Adding tree node as file name (not full file path) and highlighting files ; expanding folders containing files.
     *
     * @param resultRootItem Root item for result tree.
     * @param item           Item to be added.
     */
    private static void addItem(TreeItem<String> resultRootItem, File item) {
        TreeItem<String> treeItem = new TreeItem<>(FileSystemLogic.getShortFileName(item));
        resultRootItem.getChildren().add(treeItem);
        if (item.isFile()) {
            // Any custom visualisation for tree node wich is not folder (file) (for better view).
            // ->>
            // expands all parent directories of file found
            while (treeItem.getParent() != null) {
                treeItem = treeItem.getParent();
                treeItem.setExpanded(true);
            }
        }
    }


}
