package finder.util.tasks;

import finder.model.FileTab;
import finder.model.OptimizedRandomAccessFile;
import javafx.concurrent.Task;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

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
            if(tab.getLinePos(position)==null){
                Long linePos;
                cRaf.seek(tab.getLinePos(tab.getLastLineKey()));
                lineNumber = tab.getLastLineKey();
                while (tab.getLinePos(position) == null) {

                    if ((line = cRaf.readLineCustom()) != null) {
                        if(line.contains(tab.getSearchText())){
                            tab.lineSetContains(lineNumber, tab.getLinePos(lineNumber), true);
                        }
                        linePos = tab.getLinePos(lineNumber) + line.length() + System.lineSeparator().length();
                        tab.setFileLength(tab.getFileLength() + line.length() + System.lineSeparator().length());
                        tab.addLine(++lineNumber, linePos);
                    } else {
                        tab.setLineCount(lineNumber);
                        if(lineNumber<tab.getShowLinesCount()){
                            tab.setShowLinesCountDefault();
                            if(lineNumber<tab.getShowLinesCount()){
                                tab.setShowLinesCount(lineNumber);
                            }
                            tab.setStartLineNumber(0);
                        }
                        // correcting startLine of displayed part of file
                        if(lineNumber<tab.getStartLineNumber()+tab.getShowLinesCount()){
                            tab.setStartLineNumber(lineNumber-tab.getShowLinesCount());
                        }
                        break;
                    }
                }
            }
            if(this.countDownLatch != null){
                this.countDownLatch.countDown();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
