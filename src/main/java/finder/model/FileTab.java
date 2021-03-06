package finder.model;

import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Tab with file opened for navigation and search.
 */
public class FileTab {
    private static final long DEFAULT_STEP = 10;

    private int id;                                         // may be used in later versions of app
    private String name;
    private TabPane parentTabPane;
    private File file;                                      // file opened in that tab
    private String searchText;                              // text to search in file (from main application textArea)

    private Tab tab;

    private boolean loading = false;              // loading flag (active when operations with file are being performed)

    private StringBuffer tabStringBuffer = new StringBuffer();        // Tab's own stringbuffer

    private long showLinesCount = DEFAULT_STEP;             // number of displayed lines
    private long startLineNumber;                           // first line of displayed part of file

    private long lineLength;                      // length of line (see also OptimizedRandomAccessFile.java.readLine())
    private long lineCount;                                 // count of lines in file
    private long fileLength;                                // length of file
    private HashMap<Long,LineInfo> lines = new HashMap<>();     // <LineNumber,LineInfo>
    // line number 0..N:start position   <=> line number 1..N (means as we get 0 line as 1): line after this position
    private Long lastKey; // last key of 'lines' hashMap.

    private SearchDirection direction = SearchDirection.FORWARD;
    private long searchPosition = 0;                // additional position pointer (current line) for search operations
                                                    // needed for change direction logic
    private long searchPointer;                             // position pointer (line to be checked next)
    private long findElementLine = -1;                      // line where the last searched item was found

    private int indexOfFoundTextInLine;
    private boolean successSearch = false;

    private Label lineCountLabel;                           // Label: number of lines in file
    private StringBuffer rowNumbersBuffer;                  // buffer for number of rows displayed
    private TextArea rowNumbers;                            // TextArea where numbers of rows displayed
    private TextArea textArea;                              // TextArea with file content
    private TextField showLinesCountField;                  // TextFiled where displayed count of displayed lines of file

    /**
     * @param resultTabPane Parent tab pane for this tab
     * @param searchText    Text to search (from main Text Area)
     */
    public FileTab(int tabId, String tabName, TabPane resultTabPane, File file, String searchText) {
        this.id = tabId;
        this.name = tabName;
        this.parentTabPane = resultTabPane;
        this.file = file;
        this.searchText = searchText;

        tab = new Tab(this.name);
        parentTabPane.getTabs().add(tab);
    }

    /**
     * Setting tab scene elements.
     *
     * @param textArea textArea
     * @param showLinesCountField textField with count of lines to show (showLinesCountField)
     * @param rowNumbers textArea with rowNumbers
     * @param lineCountLabel label with lineCountLabel
     */
    public void setElements(TextArea textArea, TextField showLinesCountField, TextArea rowNumbers, Label lineCountLabel) {
        this.textArea = textArea;
        this.rowNumbers = rowNumbers;
        this.showLinesCountField = showLinesCountField;
        this.lineCountLabel = lineCountLabel;
        this.showLinesCountField.setText(String.valueOf(showLinesCount));
    }

    public void setContent(Node node) {
        tab.setContent(node);
    }

    public File getFile() {
        return file;
    }

    public Tab getTab() {
        return this.tab;
    }

    public TabPane getTabPane() {
        return parentTabPane;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    /**
     * Setting text to Label (number of lines in file).
     */
    public void setLineCount() {
        if(lineCount - 1<0){
            this.lineCountLabel.setText("Undefined.");
        }else{
            this.lineCountLabel.setText(String.valueOf(lineCount - 1));
        }
    }

    public long getShowLinesCount() {
        return showLinesCount;
    }

    public void setShowLinesCount(long showLinesCount) {
        this.showLinesCount = showLinesCount;
    }

    public void setShowLinesCountDefault() {
        this.showLinesCount = DEFAULT_STEP;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    /**
     * Returns position of lineNumber line (in bytes). Returns null if line with lineNumber key doesn't exist.
     * @param lineNumber lineNumber
     * @return position of lineNumber line (in bytes).
     */
    public Long getLinePos(Long lineNumber) {
        return lines.get(lineNumber)==null?
                null
                :lines.get(lineNumber).getLinePos();
    }

    /**
     * Returns true if line lineNumber contains search text.
     * @param lineNumber
     * @return true/false
     */
    public boolean getLineContains(Long lineNumber) {
        return lines.get(lineNumber) != null && lines.get(lineNumber).isContains();
    }

    /**
     * Add line to hashmap and mark last added line number as lastKey (sorting hashMap and taking last = much time).
     * @param lineNumber
     * @param linePosition
     */
    public void addLine(Long lineNumber, Long linePosition) {
        this.lines.put(lineNumber, new LineInfo(linePosition));
        lastKey=lineNumber;
    }

    /**
     * Set true/false flag, true if line contains text, else:false.
     * @param lineNumber
     * @param linePosition
     * @param contains true/false
     */
    public void lineSetContains(Long lineNumber, Long linePosition, boolean contains) {
        this.lines.put(lineNumber, new LineInfo(linePosition,contains));
    }

    /**
     * Get last entry key which is manually marked while adding line. Doesn't take much time.
     * @return returns last key of hashMap lines.
     */
    public Long getLastLineKey(){
        return lastKey;
    }


    /**
     * Writing FileTab showLines value to showLinesCount TextField
     */
    public void setShowLinesCount() {
        this.showLinesCountField.setText(String.valueOf(showLinesCount));
    }

    public void setShowLinesCountFromField(String content) {
        try {
            showLinesCount = Long.parseLong(content);
        } catch (Exception e) {
            showLinesCount = DEFAULT_STEP;
            new WarningWindow("You have to enter a number in \"rows count\" text field.");
        }
    }

    /**
     * Writing to TextArea (file content) from FileTab string buffer.
     * (clears string buffer)
     */
    public void writeFromTabStringBuffer() {
        this.textArea.setText(tabStringBuffer.toString());
        clearTabStringBuffer();
    }

    public void clearTabStringBuffer(){
        this.tabStringBuffer = new StringBuffer();
    }
    public void writeToTabStringBuffer(String content) {
        this.tabStringBuffer.append(content);
    }

    public boolean isFree() {
        return !loading;
    }

    public void setLoading() {
        this.loading = true;
        this.tab.setText("Loading...");
    }

    public void setLoaded() {
        this.loading = false;
        this.tab.setText(this.name);
    }

    public long getLineCount() {
        return lineCount;
    }

    public void setLineCount(long lineCount) {
        this.lineCount = lineCount;
    }

    public long getLineLength() {
        return lineLength;
    }

    public void setLineLength(long lineLength) {
        this.lineLength = lineLength;
    }

    public StringBuffer getRowNumbersBuffer() {
        return rowNumbersBuffer;
    }

    public void clearRowNumbersBuffer() {
        this.rowNumbersBuffer = new StringBuffer();
    }

    /**
     * @return TextArea with row numbers.
     */
    public TextArea getRowNumbers() {
        return rowNumbers;
    }

    public long getStartLineNumber() {
        return startLineNumber;
    }

    public void setStartLineNumber(long startLineNumber) {
        this.startLineNumber = startLineNumber;
    }

    /**
     * True when text found in file.
     */
    public void searchSucceed() {
        this.successSearch = true;
    }

    /**
     * Setting this flag manually after search fnished.
     */
    public void searchFinished() {
        this.successSearch = false;
    }

    public boolean searchResult() {
        return successSearch;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public long getSearchPosition() {
        return searchPosition;
    }

    public void setSearchPosition(long searchPosition) {
        this.searchPosition = searchPosition;
    }

    public long getSearchPointer() {
        return searchPointer;
    }

    public void setSearchPointer(long searchPointer) {
        this.searchPointer = searchPointer;
    }

    public long getFindElementLine() {
        return findElementLine;
    }

    public void setFindElementLine(long findElementLine) {
        this.findElementLine = findElementLine;
    }

    public int getIndexOfFoundTextInLine() {
        return indexOfFoundTextInLine;
    }

    public void setIndexOfFoundTextInLine(int index) {
        this.indexOfFoundTextInLine = index;
    }

    // inc if direction = FORWARD ; dec if = BACK
    public void incSearchPointer() {
        this.setSearchPointer(this.getSearchPointer() + direction.getId());
    }

    public void setDirection(SearchDirection direction) {
        // check if direction changed
        if (!this.direction.equals(direction)) {
            // updating pointer
            this.setSearchPointer(this.getSearchPosition() + direction.getId());
            this.direction = direction;
        }
    }

    public SearchDirection getDirection() {
        return this.direction;
    }

}
