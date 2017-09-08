package finder.model;

/**
 * Table content type.
 * Created by antmog on 04.09.2017.
 */
public class Extension {

    private String content;

    public Extension(){

    }

    public Extension( String content) {
        this.content = content;
    }

    public String getExtension() {
        return content;
    }

    public void setExtension(String content) {
        this.content = content;
    }
}

