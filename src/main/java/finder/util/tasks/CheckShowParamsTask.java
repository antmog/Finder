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
            System.out.println("GLOBAL START: " + System.currentTimeMillis());
            position = lineNumber+tab.getShowLinesCount();
            System.out.println("Position:" + position);
            if(tab.getLinePos(position)==null){
                System.out.println("hashmapwork start: " + System.currentTimeMillis());
                Long linePos;
                cRaf.seek(tab.getLinePos(tab.getLastLineKey()));
                lineNumber = tab.getLastLineKey();
                System.out.println("hashmapwork end: " + System.currentTimeMillis());
                System.out.println("start check: " + System.currentTimeMillis());
                while (tab.getLinePos(position) == null) {

                    if ((line = cRaf.readLineCustom()) != null) {
                        if(line.contains(tab.getSearchText())){
                            tab.lineSetContains(lineNumber, tab.getLinePos(lineNumber), true);
                        }
                        linePos = tab.getLinePos(lineNumber) + line.length() + System.lineSeparator().length();
                        tab.setFileLength(tab.getFileLength() + line.length() + System.lineSeparator().length());
                        tab.addLine(++lineNumber, linePos);
                    } else {
                        System.out.println("Length of file: " + lineNumber);
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
                System.out.println("end check: " + System.currentTimeMillis());
            }
            System.out.println("GLOBAL END: " + System.currentTimeMillis());
            if(this.countDownLatch != null){
                this.countDownLatch.countDown();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
