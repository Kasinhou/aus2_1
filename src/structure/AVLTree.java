package structure;

import java.util.ArrayList;

public class AVLTree<T extends IBSTData<T>> extends BinarySearchTree<T> {
    private int comparator;

    public AVLTree(int comparator) {
        this.comparator = comparator;
//        super();
    }
//    kolko metod a atributov je lepsie overridnut a kolko nechat z bst???

    @Override
    protected BSTNode<T> newNode(T data) {
        return new AVLNode<>(data);
    }

    @Override
    public boolean insert(T insertedData) {
        boolean isInserted = super.insert(insertedData);
        if (!isInserted) {
            return false;
        }
        AVLNode<T> insertedNode = (AVLNode<T>) this.findNode(insertedData);
        // zmena balance faktorov
        AVLNode<T> currentNode = insertedNode.getParent();
        AVLNode<T> previousNode = insertedNode;
        while (currentNode != null) {
            if (previousNode.isLeftSon()) {
                currentNode.decreaseBalanceFactor();
            } else {
                currentNode.increaseBalanceFactor();
            }

            if (currentNode.getBalanceFactor() == 0) {
                return true;
            }
            if (currentNode.getBalanceFactor() == -2) {
                if (previousNode.getBalanceFactor() == -1) { // R rotation - R(current)
                    this.rightRotation(currentNode);
                    currentNode.setBalanceFactor(0);
                    previousNode.setBalanceFactor(0);
                } else { // LR rotation - L(previous), R(current)
                    AVLNode<T> previousRightSon = previousNode.getRightSon();
                    int previousRightSonBF = previousRightSon.getBalanceFactor();
                    this.leftRotation(previousNode);
                    this.rightRotation(currentNode);
                    if (previousRightSonBF == 0) { // R=0,S=0,Sr=0
                        currentNode.setBalanceFactor(0);
                        previousNode.setBalanceFactor(0);
                    } else if (previousRightSonBF == 1) { // R=0,S=-1,Sr=0
                        currentNode.setBalanceFactor(0);
                        previousNode.setBalanceFactor(-1);
                        previousRightSon.setBalanceFactor(0);
                    } else { // R=1,S=0,Sr=0
                        currentNode.setBalanceFactor(1);
                        previousNode.setBalanceFactor(0);
                        previousRightSon.setBalanceFactor(0);
                    }
                }
//                System.out.println("BF: CURRENT, PREVIOUS " + currentNode.getBalanceFactor() + previousNode.getBalanceFactor());
                return true;
            } else if (currentNode.getBalanceFactor() == 2) {
                if (previousNode.getBalanceFactor() == 1) { // L rotation - L(current)
                    this.leftRotation(currentNode);
                    currentNode.setBalanceFactor(0);
                    previousNode.setBalanceFactor(0);
                } else { // RL rotation - R(previous), L(current)
                    AVLNode<T> previousLeftSon = previousNode.getLeftSon();
                    int previousLeftSonBF = previousLeftSon.getBalanceFactor();
                    this.rightRotation(previousNode);
                    this.leftRotation(currentNode);
                    if (previousLeftSonBF == 0) { // R=0,S=0,Sl=0
                        currentNode.setBalanceFactor(0);
                        previousNode.setBalanceFactor(0);
                    } else if (previousLeftSonBF == 1) { // R=-1,S=0,Sl=0
                        currentNode.setBalanceFactor(-1);
                        previousNode.setBalanceFactor(0);
                        previousLeftSon.setBalanceFactor(0);
                    } else { // R=0,S=1,Sl=0
                        currentNode.setBalanceFactor(0);
                        previousNode.setBalanceFactor(1);
                        previousLeftSon.setBalanceFactor(0);
                    }
                }
//                System.out.println("BF: CURRENT, PREVIOUS " + currentNode.getBalanceFactor() + previousNode.getBalanceFactor());
                return true;
            }
            previousNode = currentNode;
            currentNode = currentNode.getParent();
        }
        return true;
    }

    @Override
    public boolean delete(T deletedData) {
        AVLNode<T> deletedNode = (AVLNode<T>) this.findNode(deletedData);
        if (deletedNode == null) {
            return false;
        }
        int deletedNodeBF = deletedNode.getBalanceFactor();
        // odkial zacnem cestu k vrcholu na vyvazovanie a zmenu bf
        AVLNode<T> startingNodeToBalance = deletedNode.getParent();//TODO ROOT ci to nie je
        boolean startingToBalanceFromLeft = deletedNode.isLeftSon();
        if (deletedNode.getLeftSon() != null && deletedNode.getRightSon() != null) {
            AVLNode<T> nextInOrder = (AVLNode<T>) super.nextInOrder(deletedNode);
            if (nextInOrder.getParent() != deletedNode) {
                startingNodeToBalance = nextInOrder.getParent();
                startingToBalanceFromLeft = true;//najlavejsi
            } else {//ak je nasledovnik syn mazaneho
                startingNodeToBalance = nextInOrder;
                startingToBalanceFromLeft = false;//prvy pravy syn
            }
            nextInOrder.setBalanceFactor(deletedNodeBF);

        }
        boolean isDeleted = super.delete(deletedData);
        if (!isDeleted) {
            return false;
        }

        // kontrola BF a potrebne vyvazovanie
        AVLNode<T> currentNode = startingNodeToBalance;//pri liste a jednom synovi je to parent mazaneho
        boolean currentFromLeft = startingToBalanceFromLeft;
        while (currentNode != null) {
            int currentBF = currentNode.getBalanceFactor();
            if (currentFromLeft) {// ak je lavy syn
                currentNode.increaseBalanceFactor();
            } else {// ak je pravy
                currentNode.decreaseBalanceFactor();
            }
            if (currentBF == 0) {
                return true; //ak bola 0 pred zmenou tak koncim
            }
            // definovat premenne kvoli zmene bf po rotacii a pamataniu si cesty hore
            AVLNode<T> rotated = currentNode;
            int rotatedBF = rotated.getBalanceFactor();
            currentFromLeft = currentNode.isLeftSon();
            currentNode = currentNode.getParent();
            if (rotatedBF == -2) {
                AVLNode<T> rotatedLeftSon = rotated.getLeftSon();
                int rotatedLeftSonBF = rotatedLeftSon.getBalanceFactor();
                if (rotatedLeftSonBF <= 0) {// R(rotated)
                    this.rightRotation(rotated);
                    if (rotatedLeftSonBF == -1) {//R(0);Rl(0)
                        rotated.setBalanceFactor(0);
                        rotatedLeftSon.setBalanceFactor(0);
                    } else {//R(-1);Rl(1)
                        rotated.setBalanceFactor(-1);
                        rotatedLeftSon.setBalanceFactor(1);
                        return true;
                    }
                } else {//L(rotatedLeftSon), R(rotated)
                    AVLNode<T> rotatedLeftSonRightSon = rotatedLeftSon.getRightSon();
                    int rotatedLeftSonRightSonBF = rotatedLeftSonRightSon.getBalanceFactor();
                    this.leftRotation(rotatedLeftSon);
                    this.rightRotation(rotated);
                    rotated.setBalanceFactor(0);
                    rotatedLeftSon.setBalanceFactor(0);
                    rotatedLeftSonRightSon.setBalanceFactor(0);
                    if (rotatedLeftSonRightSonBF == -1) {//R(1) inak vsetko na 0
                        rotated.setBalanceFactor(1);
                    } else if (rotatedLeftSonRightSonBF == 1) {//Rl(-1) inak vsetko na 0
                        rotatedLeftSon.setBalanceFactor(-1);
                    }
                }
            } else if (rotatedBF == 2) {
                AVLNode<T> rotatedRightSon = rotated.getRightSon();
                int rotatedRightSonBF = rotatedRightSon.getBalanceFactor();
                if (rotatedRightSonBF >= 0) {// L(rotated)
                    this.leftRotation(rotated);
                    if (rotatedRightSonBF == 1) {//R(0);Rr(0)
                        rotated.setBalanceFactor(0);
                        rotatedRightSon.setBalanceFactor(0);
                    } else {//R(1);Rr(-1)
                        rotated.setBalanceFactor(1);
                        rotatedRightSon.setBalanceFactor(-1);
                        return true;
                    }
                } else {//R(rotatedRightSon) a L(rotated)
                    AVLNode<T> rotatedRightSonLeftSon = rotatedRightSon.getLeftSon();
                    int rotatedRightSonLeftSonBF = rotatedRightSonLeftSon.getBalanceFactor();
                    this.rightRotation(rotatedRightSon);
                    this.leftRotation(rotated);
                    rotated.setBalanceFactor(0);
                    rotatedRightSon.setBalanceFactor(0);
                    rotatedRightSonLeftSon.setBalanceFactor(0);
                    if (rotatedRightSonLeftSonBF == 1) {//R(-1) inak vsetko na 0
                        rotated.setBalanceFactor(-1);
                    } else if (rotatedRightSonLeftSonBF == -1) {//Rr(1) inak vsetko na 0
                        rotatedRightSon.setBalanceFactor(1);
                    }
                }
            }
        }
        return true;
    }

    // TODO: trosku upratat
    public void leftRotation(AVLNode<T> rotatedNode) {
        if (rotatedNode == null || rotatedNode.getRightSon() == null) { //treba right son check???
            System.out.println("Left rotation unsuccessfull");
            return;
        }
//        System.out.println("LEFT ROTATION");
        AVLNode<T> rotatedNodeRightSon = rotatedNode.getRightSon(); // musim mat praveho syna ak idem rotovat
        AVLNode<T> rotatedNodeParent = rotatedNode.getParent();
        boolean isRotatedLeftSon = rotatedNode.isLeftSon();
        if (rotatedNodeRightSon.getLeftSon() != null) {
            AVLNode<T> rotatedNodeRightSonLeftSubtree = rotatedNodeRightSon.getLeftSon();
            rotatedNodeRightSonLeftSubtree.setParent(rotatedNode);
            rotatedNodeRightSonLeftSubtree.setIsLeftSon(false);
            rotatedNode.setRightSon(rotatedNodeRightSonLeftSubtree);
        } else {
            rotatedNode.setRightSon(null);
        }
        if (rotatedNodeParent != null) { // pripad ze je koren
            if (isRotatedLeftSon) {
                rotatedNodeParent.setLeftSon(rotatedNodeRightSon);
            } else {
                rotatedNodeParent.setRightSon(rotatedNodeRightSon);
            }
        } else {
            super.setRoot(rotatedNodeRightSon);
        }
        rotatedNodeRightSon.setParent(rotatedNodeParent);
        rotatedNode.setParent(rotatedNodeRightSon);
        rotatedNodeRightSon.setLeftSon(rotatedNode);
        rotatedNode.setIsLeftSon(true); // nastavenie ci je lavy syn, zmena pri rotacii
        if (rotatedNodeParent != null) {
            rotatedNodeRightSon.setIsLeftSon(isRotatedLeftSon);
        } else {
            rotatedNodeRightSon.setIsLeftSon(false); //osetrene inde
        }
    }

    public void rightRotation(AVLNode<T> rotatedNode) {
        if (rotatedNode == null || rotatedNode.getLeftSon() == null) {
            System.out.println("Right rotation unsuccessfull");
            return;
        }
//        System.out.println("RIGHT ROTATION");
        AVLNode<T> rotatedNodeLeftSon = rotatedNode.getLeftSon(); // musim mat laveho syna ak idem rotovat
        AVLNode<T> rotatedNodeParent = rotatedNode.getParent();
        boolean isRotatedLeftSon = rotatedNode.isLeftSon();
        if (rotatedNodeLeftSon.getRightSon() != null) {
            AVLNode<T> rotatedNodeLeftSonRightSubtree = rotatedNodeLeftSon.getRightSon();
            rotatedNodeLeftSonRightSubtree.setParent(rotatedNode);
            rotatedNodeLeftSonRightSubtree.setIsLeftSon(true);
            rotatedNode.setLeftSon(rotatedNodeLeftSonRightSubtree);
        } else {
            rotatedNode.setLeftSon(null);
        }
        if (rotatedNodeParent != null) { // koren
            if (isRotatedLeftSon) {
                rotatedNodeParent.setLeftSon(rotatedNodeLeftSon);
            } else {
                rotatedNodeParent.setRightSon(rotatedNodeLeftSon);
            }
        } else {
            super.setRoot(rotatedNodeLeftSon);
        }
        rotatedNodeLeftSon.setParent(rotatedNodeParent);
        rotatedNode.setParent(rotatedNodeLeftSon);
        rotatedNodeLeftSon.setRightSon(rotatedNode);
        rotatedNode.setIsLeftSon(false);
        if (rotatedNodeParent != null) {
            rotatedNodeLeftSon.setIsLeftSon(isRotatedLeftSon);
        } else {
            rotatedNodeLeftSon.setIsLeftSon(false);// zas koren, vyriesene na inych urovnach
        }
    }
}
