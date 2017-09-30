package finder.util;

import javafx.scene.control.TreeItem;

import java.io.File;

/**
 * Functions to work with file paths and files.
 */
public class FileSystemLogic {
    /**
     * Getting filepath from the tree element (item).
     *
     * @param item item of the tree
     * @return filepath
     */
    public static String getFilePath(TreeItem<String> item) {
        StringBuffer filePath = new StringBuffer();
        try {
            filePath.append(item.getValue());
            while ((item = item.getParent() )!= null) {
                if (!item.getValue().endsWith("\\")) {
                    filePath.insert(0, item.getValue() + "\\");
                } else if (!item.getValue().equals("Roots:" + File.separator)) {
                    filePath.insert(0, item.getValue());
                }
            }
        } catch (Exception e) {
            //} catch (NullPointerException npe) {
            // warning - tree changed etc
            e.printStackTrace();
        }
        return filePath.toString();
    }

    /**
     * Getting short filename (created cause root.getName() returns empty String).
     *
     * @param file file to be checked.
     * @return file.getAbsolutePath() if File file is root directory, otherwise: file.getName();
     */
    public static String getShortFileName(File file) {
        if(CheckIfRoot(file)){
            return file.getAbsolutePath();
        }
        return file.getName();
    }

    /**
     * Checking if file is a root item of current file system.
     * @param file file
     * @return true if its root, false if its not.
     */
    public static boolean CheckIfRoot(File file){
        for (File root : File.listRoots()) {
            if (root.getAbsolutePath().equals(file.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }
}
