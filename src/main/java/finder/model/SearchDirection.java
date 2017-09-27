package finder.model;
/**
 * IDs: 1 / -1 are used to inecrement/decrement.
 */
public enum SearchDirection {
    FORWARD(1),
    BACK(-1);
    private int id;

    SearchDirection(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
