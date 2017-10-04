package finder.model;

/**
 * Object, containing line position (in bytes) and contains flag (true if line contains search text, else:false).
 */
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
