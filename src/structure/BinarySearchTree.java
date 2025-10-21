package structure;

import java.util.ArrayList;
import java.util.LinkedList;

// uvedomit si ktore metody/atributy su public/private TODO
public class BinarySearchTree<T extends IBSTData<T>> {
    private BSTNode<T> root;
    private int size = 0;
    private int level;

    public BinarySearchTree() {
    }

    public BSTNode<T> getRoot() {
        return this.root;
    }

    protected void setRoot(BSTNode<T> newRoot) {
//        if (this.size == 0) {
        this.root = newRoot;
//        } else {
//            System.out.println("Throw exception for incorrect setting root!");
//        }
    }

    protected void increaseSize() {
        ++this.size;
    }

    protected void decreaseSize() {
        --this.size;
    }

    public int getSize() {
        return this.size;
    }

    /**
     * Find node in BST (and AVL) by data given as argument.
     * @param findData data which I want to find
     * @return node with data which was found
     */
    public BSTNode<T> find(T findData) {
        if (this.size == 0) {
            return null;
        }
        BSTNode<T> currentNode = root;
        boolean isNotFound = true;
//        System.out.print("Hladany vrchol: " + findData);

        while (isNotFound) {
//            System.out.println("; Porovnavany aktualny vrchol: " + currentNode.getData());
            int result = findData.compareTo(currentNode.getData());
//            System.out.println(" -> " + result);

            if (result < 0) {
//                System.out.println("hladany je mensi ako aktualny = idem dolava");
                if (currentNode.getLeftSon() != null) {
                    currentNode = currentNode.getLeftSon();
                } else {
                    isNotFound = false;
//                    System.out.println("chcel som ist dolava ale uz nie je ziadny lavy syn");
                }
            } else if (result > 0) {
//                System.out.println("hladany je vacsi ako aktualny = idem doprava");
                if (currentNode.getRightSon() != null) {
                    currentNode = currentNode.getRightSon();
                } else {
                    isNotFound = false;
//                    System.out.println("chcel som ist doprava ale uz nie je ziadny pravy syn");
                }
            } else { // mozno hned else vetva
//                System.out.println("hladany je rovnaky ako aktualny = nasiel som vrchol " + currentNode.getData());
//                isNotFound = false;
                return currentNode;//.getData();
            } //else {
//                System.out.println("Nieco je velmi zle pri find metode!");
//                return null;
//            }
        }
//        System.out.println("NENAJDENY");
        return null;
    }

    /**
     * Insert node to BST if possible (duplicity is not available).
     * @param insertedData data which is going to be inserted
     * @return true if insert is successfull, false if there is duplicity or insertedData is null TODO Null data??
     */
    public boolean insert(T insertedData) {
        BSTNode<T> insertedNode = new BSTNode<>(insertedData);
//        System.out.println("INSERTEEEEED " + insertedNode.getData());
        if (this.size == 0) {
            this.setRoot(insertedNode);
//            System.out.println("Setting Root: " + this.root.getData());
            ++this.size;
            return true;
        }
        BSTNode<T> currentNode = root;
        boolean isNotInserted = true;
//        System.out.print("Vkladany vrchol: " + insertedNode.getData());

        while (isNotInserted) {
//            System.out.println("; Porovnavany aktualny vrchol: " + currentNode.getData());
            int result = insertedNode.getData().compareTo(currentNode.getData());
//            System.out.println(" -> " + result);

            if (result < 0) {
//                System.out.println("vkladany je mensi ako aktualny = idem dolava");
                if (currentNode.getLeftSon() != null) {
                    currentNode = currentNode.getLeftSon();
                } else {
                    //vlozim
//                    isNotInserted = false;
                    currentNode.setLeftSon(insertedNode);
//                    BSTNode<T> hlpNode = currentNode.getLeftSon(); // mozno bez premennej
                    insertedNode.setParent(currentNode);
                    insertedNode.setIsLeftSon(true);
                    ++this.size;
//                    System.out.println("vlozeny vrchol " + hlpNode.getData() + " je lavy syn " + currentNode.getData());
                    return true;
                }
            } else if (result > 0) {
//                System.out.println("vkladany je vacsi ako aktualny = idem doprava");
                if (currentNode.getRightSon() != null) {
                    currentNode = currentNode.getRightSon();
                } else {
                    //vlozim
//                    isNotInserted = false;
                    currentNode.setRightSon(insertedNode);
//                    BSTNode<T> hlpNode = currentNode.getRightSon(); // mozno bez premennej
                    insertedNode.setParent(currentNode);
                    insertedNode.setIsLeftSon(false);
                    ++this.size;
//                    System.out.println("vlozeny vrchol " + hlpNode.getData() + " je pravy syn " + currentNode.getData());
                    return true;
                }
            } else {
//                System.out.println("Vkladane data su uz v strome: DUPLICITA");
                isNotInserted = false;
//                return false;
//            } else {
//                System.out.println("Daco je velmi zle pri inserte");
            }
        }
        return false;
    }

    /**
     * Deleting node with data given as argument
     * @param deletedData data which I am trying to remove
     * @return true if node was removed, false if not (data was not found)
     */
    public boolean delete(T deletedData) {
        BSTNode<T> deletedNode = this.find(deletedData);
//        System.out.println("DELETE");
        if (deletedNode == null) {
//            System.out.println("Nemozno zmazat vrchol kedze nie je v strome!");
            return false;
        }
        if (this.size == 1) { // alebo iba size == 1, + radsej getroot ako this root?
//            System.out.println("Mazem koren, a jediny vrchol");
            this.root = null;
            --this.size;
            return true;
        }
        BSTNode<T> deletedNodeParent = deletedNode.getParent();
        BSTNode<T> deletedNodeLeftSon = deletedNode.getLeftSon();
        BSTNode<T> deletedNodeRightSon = deletedNode.getRightSon();
        boolean hasLeft = deletedNodeLeftSon != null;
        boolean hasRight = deletedNodeRightSon != null;
        boolean deletedIsLeftSon = false;
        if (this.root != deletedNode) {
            deletedIsLeftSon = deletedNodeParent.getLeftSon() == deletedNode;//root
        }

        if (!hasLeft && !hasRight) {
            if (deletedIsLeftSon) {
                deletedNodeParent.setLeftSon(null);
            } else {
                deletedNodeParent.setRightSon(null);
            }
        } else if (hasLeft && !hasRight) { // iba lavy syn
            // nastavit parentovi noveho syna (laveho syna mazaneho), lavemu synovi noveho parenta (mazaneho) + zmazat mazanemu atributy
            if (this.root == deletedNode) { // alebo deletedNodeParent == null)
                this.root = deletedNodeLeftSon;
            } else {
                if (deletedIsLeftSon) {
                    deletedNodeParent.setLeftSon(deletedNodeLeftSon);
                } else {
                    deletedNodeParent.setRightSon(deletedNodeLeftSon);
                }
            }
            deletedNodeLeftSon.setParent(deletedNodeParent); // v pripade root je to null
        } else if (!hasLeft && hasRight) {
            if (this.root == deletedNode) {
                this.root = deletedNodeRightSon;
            } else {
                if (deletedIsLeftSon) {
                    deletedNodeParent.setLeftSon(deletedNodeRightSon);
                } else {
                    deletedNodeParent.setRightSon(deletedNodeRightSon);
                }
            }
            deletedNodeRightSon.setParent(deletedNodeParent);
        } else {
            // treba aj druhy pristup alebo nie? ak ma 2 synov tak ma nasledovnika
            // z P podstromu najlavejsi (idem napravo a potom stale nalavo az kym nemam laveho)
            BSTNode<T> nextInOrder = this.nextInOrder(deletedNode);
            BSTNode<T> nextInOrderRightSon = nextInOrder.getRightSon();
            BSTNode<T> nextInOrderParent = nextInOrder.getParent();

            // ak nie je nasledovnik priamy pravy syn
            // ak je nasledovnik jeho syn a nema dalsich lavych tak iba posunut...
            if (nextInOrderParent != deletedNode) {
                nextInOrderParent.setLeftSon(nextInOrderRightSon);
                nextInOrder.setRightSon(deletedNodeRightSon);
                deletedNodeRightSon.setParent(nextInOrder);
                if (nextInOrderRightSon != null) {
                    nextInOrderRightSon.setParent(nextInOrderParent);
                }
            }
            nextInOrder.setParent(deletedNodeParent);
            nextInOrder.setLeftSon(deletedNodeLeftSon);
            deletedNodeLeftSon.setParent(nextInOrder);
            if (this.root == deletedNode) {
                this.root = nextInOrder;
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
        --this.size;
        return true;
    }

    /**
     * Find succesor? to node given as argument
     * @param currentNode finding successor
     * @return successor
     */
    // najst nasledovnika of vrcholu, pouzitie v delete, pripadne aj inde ale to potom treba upravit pre list ktory nema dvoch synov
    // upravit ze ak nema synov tak idem hore az kym nenarazim na prveho predka ktory siel dolava (jeho syn z ktoreho idem je pravy syn)
    public BSTNode<T> nextInOrder(BSTNode<T> currentNode) {
        if (currentNode == null) {
            return null;
        }
        BSTNode<T> next;
        // UNIVERZALNE PRE AKEHOKOLVEK, pozor na konecny prvok, ten vrati null
        if (currentNode.getRightSon() != null) {
            next = currentNode.getRightSon();
            while (next.getLeftSon() != null) {
                next = next.getLeftSon();
            }
        } else {
            next = currentNode;
            while (!next.isLeftSon() && next != this.root) { //ak sa rovna next korenu tak vrati next.getParent co je null
                next = next.getParent();
            }
            next = next.getParent();
        }
        return next;
    }

    /**
     * Finding all data withing some interval
     * @param min from
     * @param max to
     * @return list of all found data
     */
    public ArrayList<T> findInterval(T min, T max) {
        ArrayList<T> interval = new ArrayList<>();
        if (this.size == 0 || min == null || max == null) { // mozno ak je min alebo max definovane ako null tak to beriem ze od zaciatku alebo do konca
            return interval;
        }
        BSTNode<T> currentNode = root;
        boolean isNotFound = true;

        while (isNotFound) {
            int result = min.compareTo(currentNode.getData());

            if (result < 0) {
                if (currentNode.getLeftSon() != null) {
                    currentNode = currentNode.getLeftSon();
                } else {
                    // ukoncene hladanie, ak je vysledok mensi tak current je tiez mensi a odtialto idem vzostupne
                    // current je uz prvy prvok, pridat hned?
//                    interval.add(currentNode.getData());
                    isNotFound = false;
                }
            } else if (result > 0) {
                if (currentNode.getRightSon() != null) {
                    currentNode = currentNode.getRightSon();
                } else {
                    // RAZ ZAVOLAT NASLEDOVNIKA a ukoncit cyklus
                    currentNode = this.nextInOrder(currentNode);
                    // lenze pozor na to ze treba aj nasledovnika porovnat s minimom aby som naozaj zacal od neho, TAK mozno ani nie kedze nasledonik by fakt mal byt prvy predok ktory ide dolava
                    isNotFound = false;
                }
            } else {
                isNotFound = false;
                // ulozim hned minimum a idem na nasledovnika
//                interval.add(currentNode.getData());
            }
        }
        if (max.compareTo(currentNode.getData()) < 0) {
            return interval;
        }
        interval.add(currentNode.getData());
        BSTNode<T> next = this.nextInOrder(currentNode);
        // pridavanie az do maxima
        while (next != null) {
            if (max.compareTo(next.getData()) < 0) {
                break;
            }
            interval.add(next.getData());
            next = this.nextInOrder(next);
        }
        return interval;
    }

    /**
     * In order traverse of subtree starting with node given as argument
     * @param startingNode node where I want to start traverse (only in its subtree)
     * @return list with data in order from lowest to highest
     */
//    TODO porozmyslat ci nie je lepsie definovet inorder pre zaciatok a koniec, ak by boli oba ako null tak z root cely strom, ak nie tak pouzit aj na vyhladanie intervalove
//    TODO kedze som spravil metodu nextinorder, mozno by bolo fajn ju vyuzit aby nebola duplicita kodu?, lenze to by bola mozno duplicita?
    public ArrayList<T> inOrder(BSTNode<T> startingNode) {
//        System.out.println("IN ORDER");
        ArrayList<T> inOrderList = new ArrayList<>();
        if (startingNode == null) {
            System.out.println("Null starting node inorder");
            return inOrderList;
        }
        BSTNode<T> currentNode = startingNode;
        BSTNode<T> previousNode = null;

        while (currentNode != startingNode.getParent()) {
            BSTNode<T> newCurrent;
            if (previousNode == currentNode.getParent()) { // ak idem stale dole na synov, predchodca je parent aktualneho
                if (currentNode.getLeftSon() != null) {
                    newCurrent = currentNode.getLeftSon();
                } else {
                    inOrderList.add(currentNode.getData());
                    if (currentNode.getRightSon() != null) {
                        newCurrent = currentNode.getRightSon();
                    } else {
                        newCurrent = currentNode.getParent();
                    }
                }
            } else if (previousNode == currentNode.getLeftSon()) { // ak je previous lavy syn aktualneho
                inOrderList.add(currentNode.getData());
                if (currentNode.getRightSon() != null) {
                    newCurrent = currentNode.getRightSon();
                } else {
                    newCurrent = currentNode.getParent();
                }
            } else { // ak je previous pravy syn
                newCurrent = currentNode.getParent();
            }
            previousNode = currentNode;
            currentNode = newCurrent;
        }
        return inOrderList;
    }

    /**
     * Level order of Tree
     * @param startingNode node from where I want to run level order, root of subtree
     * @return list of nodes in level order
     */
    public ArrayList<BSTNode<T>> levelOrder(BSTNode<T> startingNode) {
        if (this.size == 0) {
            System.out.println("Strom je prazdny.");
            return null;
        }
//        System.out.println("LEVEL ORDER");
        ArrayList<BSTNode<T>> levelOrderList = new ArrayList<>();
        LinkedList<BSTNode<T>> sons = new LinkedList<>();
        sons.addLast(startingNode);
        while (!sons.isEmpty()) {
            BSTNode<T> first = sons.getFirst();
            levelOrderList.add(first);
            BSTNode<T> leftSon = first.getLeftSon();
            BSTNode<T> rightSon = first.getRightSon();
            if (leftSon != null) { sons.addLast(leftSon); }
            if (rightSon != null) { sons.addLast(rightSon); }
            sons.removeFirst();
        }
        return levelOrderList;
    }

    /**
     * Find minimal data in tree
     * @return data with the lowest key in tree
     */
    public T findMinimum() {
        BSTNode<T> current = this.root;
        if (current == null) {
            return null;
        }
        while (current.getLeftSon() != null) {
            current = current.getLeftSon();
        }
        return current.getData();
    }

    /**
     * Find maximal data in tree
     * @return data with the highest key in tree
     */
    public T findMaximum() {
        BSTNode<T> current = this.root;
        if (current == null) {
            return null;
        }
        while (current.getRightSon() != null) {
            current = current.getRightSon();
        }
        return current.getData();
    }
}
