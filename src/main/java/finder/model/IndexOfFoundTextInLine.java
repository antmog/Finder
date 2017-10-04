package finder.model;

public class IndexOfFoundTextInLine {
    private int index;
    private int entryNumber = 0;

    public int getIndex(){
        return index;
    }
    public int getEntryNumber(){
        return entryNumber;
    }
    public void setIndexOfFoundTextInLine(int index){
        this.index = index;
        this.entryNumber = 0;
    }

    public void setIndexOfFoundTextInLine(int index, boolean increaseEntryNumber, SearchDirection searchDirection){
        setIndexOfFoundTextInLine(index);
        if(increaseEntryNumber){
            if(searchDirection == SearchDirection.FORWARD)
            {
                this.entryNumber++;
            }else{
                this.entryNumber--;
            }

        }

    }
}
