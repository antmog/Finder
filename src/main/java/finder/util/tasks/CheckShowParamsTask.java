package finder.util.tasks;

import finder.model.FileTab;
import finder.model.OptimizedRandomAccessFile;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Task:
 * Executed before ShowTask.
 * If needed (for ShowTask) elements are not loaded to buffer (hashMap lines) of tab , loads it.
 */
public class CheckShowParamsTask extends Task<Void> {

    private FileTab tab;
    private CountDownLatch countDownLatch;


    public CheckShowParamsTask(FileTab tab){
        this.tab = tab;
    }
    public CheckShowParamsTask(FileTab tab, CountDownLatch countDownLatch){
        this(tab);
        this.countDownLatch = countDownLatch;
    }

    @Override
    protected Void call() throws Exception {
        try(OptimizedRandomAccessFile cRaf = new OptimizedRandomAccessFile(tab.getFile(),"r",8192)){
            String line;
            Long position;
            Long lineNumber = tab.getStartLineNumber();
            // setting position
            position = lineNumber+tab.getShowLinesCount();
            // if needed position is not loaded to buffer (null)
            if(tab.getLinePos(position)==null){
                Long linePos;
                // loading elements from last loaded element key (from last loaded lineNumber)
                cRaf.seek(tab.getLinePos(tab.getLastLineKey()));
                lineNumber = tab.getLastLineKey();
                while (tab.getLinePos(position) == null) {
                    if ((line = cRaf.readLineCustom()) != null) {
                        // set contains flag  = true if text found in line
                        if(line.contains(tab.getSearchText())){
                            tab.lineSetContains(lineNumber, tab.getLinePos(lineNumber), true);
                        }
                        linePos = tab.getLinePos(lineNumber) + line.length() + System.lineSeparator().length();
                        // keeping file length up to date
                        //tab.setFileLength(tab.getFileLength() + line.length() + System.lineSeparator().length());
                        // add next element line number and position
                        tab.addLine(++lineNumber, linePos);
                    } else {
                        // end of file - write amount of lines in file to tab
                        // shown as label in tab window
                        tab.setLineCount(lineNumber);

                        // correcting startLine of displayed part of file
                        if(lineNumber<tab.getShowLinesCount()){
                            tab.setShowLinesCountDefault();
                            if(lineNumber<tab.getShowLinesCount()){
                                tab.setShowLinesCount(lineNumber);
                            }
                            tab.setStartLineNumber(0);
                        }

                        if(lineNumber<tab.getStartLineNumber()+tab.getShowLinesCount()){
                            tab.setStartLineNumber(lineNumber-tab.getShowLinesCount());
                        }
                        break;
                    }
                }
            }
            // if task was initiated with CountDownLatch counter -> countdown.
            if(this.countDownLatch != null){
                this.countDownLatch.countDown();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
