package structure;

/**
 * Node in Binary Search Tree
 * @param <T> data stored in node
 */
public class BSTNode<T extends IBSTData<T>> {
    private BSTNode<T> parent;
    private BSTNode<T> leftSon;
    private BSTNode<T> rightSon;
    private T data;
    private boolean isLeftSon;

    public BSTNode(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public BSTNode<T> getLeftSon() {
        return this.leftSon;
    }

    public BSTNode<T> getRightSon() {
        return this.rightSon;
    }

    public BSTNode<T> getParent() {
        return this.parent;
    }

    // ak by som rovno tu skontroloval ci je parent null, ak nie tak priradit a zrusila by sa duplicita?
    protected void setLeftSon(BSTNode<T> leftSon) {
        this.leftSon = leftSon;
    }

    protected void setRightSon(BSTNode<T> rightSon) {
        this.rightSon = rightSon;
    }

    protected void setParent(BSTNode<T> parent) {
        this.parent = parent;
    }

    public boolean isLeftSon() {
        return this.isLeftSon;
    }
    // mozno namiesto atributu iba zistit ci je lavy
    //    public boolean isLeftSon() { return getParent() != null && getParent().getLeftSon() == this; }

    protected void setIsLeftSon(boolean isLeftSon) {
        this.isLeftSon = isLeftSon;
    }
}