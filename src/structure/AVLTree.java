package structure;

import java.util.ArrayList;
import java.util.Stack;

public class AVLTree<T extends IBSTData<T>> extends BinarySearchTree<T> {

    public AVLTree() {
//        super();
    }
//    kolko metod a atributov je lepsie overridnut a kolko nechat z bst???

    @Override
    public boolean insert(T insertedData) {
        if (insertedData == null) {
            return false;
        }
        AVLNode<T> insertedNode = new AVLNode<>(insertedData);
//        System.out.println(insertedNode.getData());
        if (super.getSize() == 0) {
            this.setRoot(insertedNode);
            super.increaseSize();
            return true;
        }
        AVLNode<T> currentNode = (AVLNode<T>) super.getRoot();
        Stack<Integer> pathToInserted = new Stack<>();
        boolean isNotInserted = true;

        while (isNotInserted) {
            int result = insertedNode.getData().compareTo(currentNode.getData());
//            System.out.println(" -> " + result);

            if (result < 0) {
                pathToInserted.push(-1); // idem dolava, zavazuje na tu stranu
                if (currentNode.getLeftSon() != null) {
                    currentNode = currentNode.getLeftSon();
                } else {
                    isNotInserted = false;
                    currentNode.setLeftSon(insertedNode);
                    insertedNode.setParent(currentNode);
                    insertedNode.setIsLeftSon(true);
//                    System.out.println(insertedNode.getData());
                }
            } else if (result > 0) {
                pathToInserted.push(1); // zavazujem vpravo
                if (currentNode.getRightSon() != null) {
                    currentNode = currentNode.getRightSon();
                } else {
                    isNotInserted = false;
                    currentNode.setRightSon(insertedNode);
                    insertedNode.setParent(currentNode);
                    insertedNode.setIsLeftSon(false);
                }
            } else {
//                System.out.println("vkladany je rovnaky ako aktualny = nasiel som duplicitny vrchol");
                return false;
            }
        }
        super.increaseSize();
//        for (Integer i : pathToInserted) {
//            System.out.print(i + "; ");
//        }
//        System.out.println();
        // zmena balance faktorov podla potreby v pathToInserted
        currentNode = insertedNode.getParent();
        AVLNode<T> previousNode = insertedNode;
        while (!pathToInserted.isEmpty()) {
//        for (int i = pathToInserted.size() - 1; i >= 0; --i) { //using array backwards
            if (pathToInserted.pop() == -1) { // nalavo nakolneny podstrom pod vrcholom
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
        AVLNode<T> deletedNode = (AVLNode<T>) this.find(deletedData);
        if (deletedNode == null) {
            System.out.println("Nemozno zmazat vrchol kedze nie je v strome!");
            return false;
        }
        if (super.getSize() == 1) {
            //System.out.println("Mazem koren, a jediny vrchol");
            super.setRoot(null);
            super.decreaseSize();
            return true;
        }
        //Stack<Integer> pathToDeleted = new Stack<>(); // mozno ani nebude treba, namiesto tho skontrolujem ci je lavy syn alebo pravy
        AVLNode<T> deletedNodeParent = deletedNode.getParent();
        AVLNode<T> deletedNodeLeftSon = deletedNode.getLeftSon();
        AVLNode<T> deletedNodeRightSon = deletedNode.getRightSon();
        boolean hasLeft = deletedNodeLeftSon != null;
        boolean hasRight = deletedNodeRightSon != null;
        boolean deletedIsLeftSon = deletedNode.isLeftSon();
        int deletedNodeBF = deletedNode.getBalanceFactor();
        // odkial zacnem cestu k vrcholu na vyvazovanie a zmenu bf
        AVLNode<T> startingNodeToBalance = deletedNodeParent;
        boolean startingToBalanceFromLeft = deletedIsLeftSon;

        if (!hasLeft && !hasRight) {
            if (deletedIsLeftSon) {
                deletedNodeParent.setLeftSon(null);
            } else {
                deletedNodeParent.setRightSon(null);
            }
        } else if (hasLeft && !hasRight) {
            // nastavit parentovi noveho syna (laveho syna mazaneho), lavemu synovi noveho parenta (mazaneho) + zmazat mazanemu atributy
            if (super.getRoot() == deletedNode) { // alebo deletedNodeParent == null)
                super.setRoot(deletedNodeLeftSon);
            } else {
                if (deletedIsLeftSon) {
                    deletedNodeParent.setLeftSon(deletedNodeLeftSon);
                    deletedNodeLeftSon.setIsLeftSon(true);
                } else {
                    deletedNodeParent.setRightSon(deletedNodeLeftSon);
                    deletedNodeLeftSon.setIsLeftSon(false);
                }
            }
            deletedNodeLeftSon.setParent(deletedNodeParent); // v pripade root je to null
        } else if (!hasLeft && hasRight) {
            if (super.getRoot() == deletedNode) {
                super.setRoot(deletedNodeRightSon);
            } else {
                if (deletedIsLeftSon) {
                    deletedNodeParent.setLeftSon(deletedNodeRightSon);
                    deletedNodeRightSon.setIsLeftSon(true);
                } else {
                    deletedNodeParent.setRightSon(deletedNodeRightSon);
                    deletedNodeRightSon.setIsLeftSon(false);
                }
            }
            deletedNodeRightSon.setParent(deletedNodeParent);
        } else {
            // z P podstromu najlavejsi (idem napravo a potom stale nalavo az kym nema laveho - nasledovnik)
            AVLNode<T> nextInOrder = (AVLNode<T>) this.nextInOrder(deletedNode);
            AVLNode<T> nextInOrderRightSon = nextInOrder.getRightSon();
            AVLNode<T> nextInOrderParent = nextInOrder.getParent();

            if (nextInOrderParent != deletedNode) {//ak je nasledovnik nizsie ako jedna uroven
                nextInOrderParent.setLeftSon(nextInOrderRightSon);
                nextInOrder.setRightSon(deletedNodeRightSon);
                deletedNodeRightSon.setParent(nextInOrder);
                if (nextInOrderRightSon != null) {
                    nextInOrderRightSon.setParent(nextInOrderParent);
                    nextInOrderRightSon.setIsLeftSon(true);
                }
                startingNodeToBalance = nextInOrderParent;
                startingToBalanceFromLeft = true;//najlavejsi
            } else {//ak je nasledovnik syn mazaneho
                startingNodeToBalance = nextInOrder;
                startingToBalanceFromLeft = false;//prvy pravy syn
            }
            nextInOrder.setBalanceFactor(deletedNodeBF);
            nextInOrder.setParent(deletedNodeParent);
            nextInOrder.setLeftSon(deletedNodeLeftSon);
            nextInOrder.setIsLeftSon(deletedIsLeftSon);
            deletedNodeLeftSon.setParent(nextInOrder);
            if (super.getRoot() == deletedNode) {
                super.setRoot(nextInOrder);
            } else {
                if (deletedIsLeftSon) {
                    deletedNodeParent.setLeftSon(nextInOrder);
                } else {
                    deletedNodeParent.setRightSon(nextInOrder);
                }
            }
        }
        deletedNode.setLeftSon(null);
        deletedNode.setRightSon(null);
        deletedNode.setParent(null);
        deletedNode = null;
        super.decreaseSize();

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

    //co ak budem chciet robit rotaciu aj inde ako pri insert a delete???, potom tam treba osetrit bf
    public void leftRotation(AVLNode<T> rotatedNode) {
        if (rotatedNode == null || rotatedNode.getRightSon() == null) { //treba???
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
