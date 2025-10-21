package structure;

/**
 * AVL Node (from BST node) in AVL tree
 * @param <T> type of data stored in node
 */
public class AVLNode<T extends IBSTData<T>> extends BSTNode<T> {
    private int balanceFactor;
//    ake vsetky atributy treba znova definovat a ktore pouzit z BST, tak isto metody

    public AVLNode(T data) {
        super(data);
    }

    public int getBalanceFactor() {
        return balanceFactor;
    }

    public void increaseBalanceFactor() {
        ++this.balanceFactor;
    }

    public void decreaseBalanceFactor() {
        --this.balanceFactor;
    }

    public void setBalanceFactor(int balanceFactor) {
        this.balanceFactor = balanceFactor;
    }
    // TODO: porozmyslat ci takto je to najlepsie alebo ci neskusit inak
    public AVLNode<T> getLeftSon() {
        return (AVLNode<T>) super.getLeftSon();
    }

    public AVLNode<T> getRightSon() {
        return (AVLNode<T>) super.getRightSon();
    }

    public AVLNode<T> getParent() {
        return (AVLNode<T>) super.getParent();
    }
}
