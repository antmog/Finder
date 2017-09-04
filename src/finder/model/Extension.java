package finder.model;

/**
 * Created by antmog on 04.09.2017.
 */
public class Extension {

    private String content;

    public Extension(){

    }

    public Extension( String content) {
        this.content = content;
    }



    public String getLogin() {
        return content;
    }

    public void setLogin(String content) {
        this.content = content;
    }
}

