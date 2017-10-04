package finder.model;

import javax.sound.sampled.Line;

public class LineInfo {
    private Long linePos;
    private boolean contains = false;

    public LineInfo(Long linePos){
        this.linePos = linePos;
    }
    public LineInfo(Long linePos, boolean contains){
        this(linePos);
        this.contains = contains;
    }

    public Long getLinePos() {
        return linePos;
    }

    public void setLinePos(Long linePos) {
        this.linePos = linePos;
    }

    public boolean isContains() {
        return contains;
    }

    public void setContains(boolean contains) {
        this.contains = contains;
    }

    public String toString(){
        return "["+linePos+","+contains+"]";
    }


}
