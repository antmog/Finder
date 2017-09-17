package finder.util;

import finder.model.FinderInstance;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.ArrayList;

public class ResultTreeCreateLogic {
    /**
     * Generate and show result tree.
     *
     * @param listOfDirs List of directories containing files we search for (and files).
     * @param sb         Text to search.
     * @param extensions Extensions of target files.
     */
    public static void showResultFileTree(StringBuffer sb, ArrayList<File> listOfDirs, Object[] extensions, FinderInstance finderInstance) {
        // creating new tree from root of main tree (where folders to search are selected)
        // suppress warning? i swear finderInstance.getFileTree().getRoot() is a tree item
        TreeItem<String> resultRootItem = new TreeItem<String>(((CheckBoxTreeItem<String>) finderInstance.getFileTree().getRoot()).getValue());
        generateResultTree(resultRootItem, listOfDirs);
        finderInstance.getResultFileTree().setSearchText(sb.toString());
        finderInstance.getResultFileTree().setRoot(resultRootItem);
    }

    /**
     * Adding search results one by one INCLUDING FILEPATH as tree nodes (if we have D:\folder1\file1 and D:\folder1\file2 then manually adding
     * full file path as tree nodes: D:\ D:\folder1 D:\folder1\file1/2( + checking if tree node alrdy exists) ).
     *
     * @param resultRootItem Result root item for result tree.
     * @param listOfDirs     Result of search.
     */
    private static void generateResultTree(TreeItem<String> resultRootItem, ArrayList<File> listOfDirs) {
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
            addItem(listOfItems, resultRootItem);
        }
    }

    /**
     * Adding item to result tree.
     *
     * @param listOfItems    Structure of item: for example for file "D:\folder1\file1" its : "D:\" + "D:\folder1" + "D:\folder1\file1".
     * @param resultRootItem Root item for result tree.
     */
    private static void addItem(ArrayList<CheckBoxTreeItem<String>> listOfItems, TreeItem<String> resultRootItem) {
        boolean noRepeat = true; // variable to check if tree node alrdy exists
        if (!listOfItems.isEmpty()) {
            // another item to check/add (compare with current items and check if it already exists)
            CheckBoxTreeItem<String> compareItem = listOfItems.remove(listOfItems.size() - 1);
            File compareItemFile = new File(compareItem.getValue());
            if (resultRootItem.getChildren().isEmpty()) {
                // if tree node has no children - the tree node we are going to add 100% doesn't exist
                addSmartItem(resultRootItem, compareItemFile);
            } else {
                // otherwise we check it manually
                for (TreeItem<String> item : resultRootItem.getChildren()) {
                    if (item.getValue().equals(OtherLogic.getShortFileName(compareItemFile))) {
                        noRepeat = false;
                        break;
                    }
                }
                // and if it doesn't exist
                if (noRepeat) {
                    addSmartItem(resultRootItem, compareItemFile);
                }
            }
            resultRootItem = resultRootItem.getChildren().get(resultRootItem.getChildren().size() - 1);
            addItem(listOfItems, resultRootItem);
        }
    }

    /**
     * Adding tree node as file name (not full file path) and highlighting files ; expanding folders containing files.
     *
     * @param resultRootItem Root item for result tree.
     * @param item           Item to be added.
     */
    private static void addSmartItem(TreeItem<String> resultRootItem, File item) {
        TreeItem<String> treeItem = new TreeItem<>(OtherLogic.getShortFileName(item));
        resultRootItem.getChildren().add(treeItem);
        if (item.isFile()) {
            //Highlight? tbd

            // expands all parent directories of file found
            while (treeItem.getParent() != null) {
                treeItem = treeItem.getParent();
                treeItem.setExpanded(true);
            }
        }
    }


}
