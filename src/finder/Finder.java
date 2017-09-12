package finder;

import finder.model.ActionsInterface;
import finder.model.Extension;
import finder.view.InitialScreenController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.application.Platform;
import finder.util.Resources;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class Finder extends Application {

    private static Stage stage;
    private TextArea textArea;
    private TreeView fileTree, resultFileTree;
    private ObservableList<Extension> tableData;
    private ActionsInterface actionsInterface = new ActionsInterface() {
        @Override
        public Object load(URL url, Object controller) throws IOException {
            FXMLLoader loader = new FXMLLoader(url);
            loader.setController(controller);
            return loader.load();
        }

        @Override
        public void actionClick() {
            searchFiles();
        }

        @Override
        public void actionGetTextArea(TextArea text) {
            textArea = text;
        }

        @Override
        public void actionGetTree(TreeView tree) {
            fileTree = tree;
        }

        @Override
        public void actionGetTableData(ObservableList<Extension> loadedTableData) {
            tableData = loadedTableData;
        }

        @Override
        public void getResultTree(TreeView fileTree) {
            resultFileTree = fileTree;
        }

       /* @Override
        public void createResultTree(File file, CheckBoxTreeItem<String> parent) {
            if (file.isDirectory()) {
                boolean isSelected = parent.isSelected();
                if (file.listFiles() != null) {
                    for (File f : file.listFiles()) {
                        if (f.isDirectory()) {
                            CheckBoxTreeItem<String> treeItem = new CheckBoxTreeItem<>(f.getName(), null, isSelected);
                            //treeItem.setIndependent(true); uncomment to be able to select independent folder search (no subfolders selected etc - only direct folder.
                            parent.getChildren().add(treeItem);
                            createResultTree(f, treeItem);
                        }
                    }
                }
            }
        }*/

        @Override
        public String getFilePath(CheckBoxTreeItem<String> item) {
            StringBuffer filePath = new StringBuffer();
            filePath.append(item.getValue());
            while (item.getParent() != null) {
                item = (CheckBoxTreeItem<String>) item.getParent();
                if (!item.getValue().endsWith("\\")) {
                    filePath.insert(0, item.getValue() + "\\");
                } else if (!item.getValue().equals("Roots:" + File.separator)) {
                    filePath.insert(0, item.getValue());
                }
            }
            return filePath.toString();
        }

    };

    @Override
    public void start(Stage stage) throws Exception {
        Finder.stage = stage;
        Finder.stage.setTitle("Finder");
        Finder.stage.getIcons().add(new Image(getClass().getResource(Resources.IMG + "Icon.png").toString()));
        Finder.stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        try {
            // Load main layout from fxml file.
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Resources.FXML + "InitialScreen.fxml"));
            loader.setController(new InitialScreenController(actionsInterface));

            SplitPane view = loader.load();

            // Shows the scene containing the main layout.
            Scene scene = new Scene(view);
            stage.setScene(scene);
            stage.show();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }

    }

    public static void main(String[] args) {
        launch(Finder.class);
    }

    /**
     * Searching files full action.
     **/
    public void searchFiles() {
        // text from textArea
        StringBuffer sb = new StringBuffer(textArea.getText());
        // list for keeping search results, contains:
        // - Selected folders in main tree (where to search)
        // - Files that meet search requirements
        ArrayList<File> listOfDirs = new ArrayList<>();
        // array of extensions of files program is searching for
        Object[] extensions = tableData.toArray();

        // searching files and making form for result tree
        searchInTree(fileTree.getRoot(), listOfDirs, sb, extensions);

        // generating result tree
        showResultFileTree(sb, listOfDirs, extensions);
    }


    /**
     * Searching selected folders and searching files in selected folders (+subfolders) (result -> ListOfDirs).
     *
     * @param parentItem Parent root item.
     * @param listOfDirs List of directories containing files we search for (and files).
     * @param sb         Text to search.
     * @param extensions Extensions of target files.
     */
    private void searchInTree(TreeItem<String> parentItem, ArrayList<File> listOfDirs, StringBuffer sb, Object[] extensions) {
        for (TreeItem<String> child : parentItem.getChildren()) {
            CheckBoxTreeItem<String> checkbox = (CheckBoxTreeItem<String>) child;
            if (checkbox.isSelected()) {
                // if folder is selected - search in this folder + subfolders
                File dir = new File(actionsInterface.getFilePath(checkbox));
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
    private void search(File dir, ArrayList<File> listOfDirs, StringBuffer sb, Object[] extensions) {
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (!file.isDirectory()) {
                    //System.out.println(FilenameUtils.removeExtension(file.getName()));
                    if (FilenameUtils.removeExtension(file.getName()).equals(sb.toString())) {
                        for (Object object : extensions) {
                            Extension extension = (Extension) object;
                            if (FilenameUtils.getExtension(file.getName()).equals(extension.getExtension())) {
                                //System.out.println(file.getName() + " == " + sb.toString());
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
     * Generate and show result tree.
     *
     * @param listOfDirs List of directories containing files we search for (and files).
     * @param sb         Text to search.
     * @param extensions Extensions of target files.
     */
    private void showResultFileTree(StringBuffer sb, ArrayList<File> listOfDirs, Object[] extensions) {
        // creating new tree from root of main tree (where folders to search are selected)
        TreeItem<String> resultRootItem = new TreeItem<String>(((CheckBoxTreeItem<String>) fileTree.getRoot()).getValue());
        generateResultTree(resultRootItem, listOfDirs);
        resultFileTree.setRoot(resultRootItem);
    }


    /**
     * Adding search results one by one INCLUDING FILEPATH as tree nodes (if we have D:\folder1\file1 and D:\folder1\file2 then manually adding
     * full file path as tree nodes: D:\ D:\folder1 D:\folder1\file1/2( + checking if tree node alrdy exists) ).
     *
     * @param resultRootItem Result root item for result tree.
     * @param listOfDirs     Result of search.
     */
    private void generateResultTree(TreeItem<String> resultRootItem, ArrayList<File> listOfDirs) {
        ArrayList<CheckBoxTreeItem<String>> listOfItems = new ArrayList<>(); // temp list to keep full path to file
        // for example for file  "D:\folder1\file1" : "D:\" + "D:\folder1" + "D:\folder1\file1"

        for (File file : listOfDirs) { // for each file (folder/file) in listOfDirs (result of search)
            // filling temp list with values
            listOfItems.add(new CheckBoxTreeItem<String>(file.getAbsolutePath()));
            while (file.getParent() != null) {
                file = file.getParentFile();
                listOfItems.add(new CheckBoxTreeItem<String>(file.getAbsolutePath()));
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
    private void addItem(ArrayList<CheckBoxTreeItem<String>> listOfItems, TreeItem<String> resultRootItem) {
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
                    if (item.getValue().equals(getShortFileName(compareItemFile))){
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
    private void addSmartItem(TreeItem<String> resultRootItem, File item) {
        if(item.isFile()){
            System.out.println(item);
        }
        resultRootItem.getChildren().add(new TreeItem<>(getShortFileName(item)));
    }

    /**
     * Getting short filename.
     *
     * @param file file to be checked.
     * @return file.getAbsolutePath() if File file is root directory, otherwise: file.getName();
     */
    private String getShortFileName(File file) {
        for (File root : File.listRoots()) {
            if (file.equals(root)) {
                return file.getAbsolutePath();
            }
        }
        return file.getName();
    }


}
