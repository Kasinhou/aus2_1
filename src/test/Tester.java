package test;

import structure.AVLNode;
import structure.AVLTree;
import structure.BSTNode;
import structure.BinarySearchTree;

import java.util.*;

/**
 * This class is testing both BST and AVL trees (functionality and performance of methods)
 * Also there are tested other structures.
 */
public class Tester {
    public static void main(String[] args) {
//        testRandomOperations();
        testOperationsOneByOne();
//        measuringOperationsTime();
    }

    public static void testRandomOperations() {
        Random random = new Random();//TODO meranie rychlosti, min, max, intervalove hladanie
        for (int s = 0; s < 1; ++s) {
            BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
            AVLTree<MyInteger> avltree = new AVLTree<>();
            ArrayList<Integer> insertedToBST = new ArrayList<>();
            ArrayList<Integer> insertedToAVL = new ArrayList<>();
            random.setSeed(s);
            System.out.println(s);
            for (int i = 0; i < 1000000; ++i) {//20mil
                double r = random.nextDouble();
                int data = random.nextInt(Integer.MAX_VALUE);
                MyInteger key = new MyInteger(data);
                if (r < 0.3) { // hladat aj take co sa tam nenachazaju? Nema zmysel stale, otestovanych par predtym
                    if (!insertedToBST.isEmpty() && !insertedToAVL.isEmpty()) {
                        int findIndex = random.nextInt(insertedToBST.size());
                        MyInteger findData = new MyInteger(insertedToBST.get(findIndex));
                        //merat cas
                        BSTNode<MyInteger> findBST = bstree.find(findData);
                        if (findBST == null) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in BST: " + insertedToBST.get(findIndex) + " was unsuccessfull! :(");
                        }
                        //cas
                        BSTNode<MyInteger> findAVL = avltree.find(findData);
                        if (findAVL == null) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in AVL: " + insertedToAVL.get(findIndex) + " was unsuccessfull! :(");
                        }
                    } else {
                        if (insertedToBST.size() != insertedToAVL.size()) {
                            System.out.println("BST a AVL are not the same size (one is empty)");
                        }
                    }
                } else if (r < 0.45) { // mazat len take ktore su v strukture, inak to nema zmysel, take co nie su stacilo otestovat par pred tym
                    if (!insertedToBST.isEmpty() && !insertedToAVL.isEmpty()) {
                        int deletedIndex = random.nextInt(insertedToBST.size());
                        MyInteger deletedData = new MyInteger(insertedToBST.get(deletedIndex));
                        // merat cas
                        boolean deletedBST = bstree.delete(deletedData);
                        if (!deletedBST) {
                            System.out.println("Seed " + s + ", index " + i + ": DELETED data in BST: " + insertedToBST.get(deletedIndex) + " was unsuccessfull! :(");
                        } else {
                            int lastIndex = insertedToBST.size() - 1;
                            Collections.swap(insertedToBST, deletedIndex, lastIndex);
                            insertedToBST.remove(lastIndex);
                        }
                        //cas
                        boolean deletedAVL = avltree.delete(deletedData);
                        if (!deletedAVL) {
                            System.out.println("Seed " + s + ", index " + i + ": DELETED data in AVL: " + insertedToAVL.get(deletedIndex) + " was unsuccessfull! :(");
                        } else {
                            int lastIndex = insertedToAVL.size() - 1;
                            Collections.swap(insertedToAVL, deletedIndex, lastIndex);
                            insertedToAVL.remove(lastIndex);
                        }
                    } else {
                        if (insertedToBST.size() != insertedToAVL.size()) {
                            System.out.println("BST a AVL are not the same size (one is empty)");
                        }
                    }
                } else {
                    // merat cas
                    boolean insertedBST = bstree.insert(key);
                    if (!insertedBST) {
                        if (bstree.find(key) == null) {
                            System.out.println("Seed " + s + ", index " + i + ": INSERT data to BST: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        insertedToBST.add(data);
                    }
                    boolean insertedAVL = avltree.insert(key);
                    if (!insertedAVL) {
                        if (avltree.find(key) == null) {
                            System.out.println("Seed " + s + ", index " + i + ": INSERT data to AVL: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        insertedToAVL.add(data);
                    }
                }
            }
            // kontrola na konci, velkost rovnaka pri vlozenych a level order
            if (!insertedToBST.isEmpty()) {
                if (insertedToBST.size() != bstree.levelOrder(bstree.getRoot()).size()) {
                    System.out.println("NOT EQUAL SIZE OF INSERTED AND LEVEL ORDER IN BST!");
                }
            }
            if (!insertedToAVL.isEmpty()) {
                if (insertedToAVL.size() != avltree.levelOrder(avltree.getRoot()).size()) {
                    System.out.println("NOT EQUAL SIZE OF INSERTED AND LEVEL ORDER IN AVL!");
                }
                // kontrola BF pri AVL
                ArrayList<BSTNode<MyInteger>> levelAVL = avltree.levelOrder(avltree.getRoot());
                for (BSTNode<MyInteger> mi : levelAVL) {
                    if (mi instanceof AVLNode<MyInteger>) {
                        int bf = ((AVLNode<MyInteger>) mi).getBalanceFactor();
                        if (Math.abs(bf) >= 2) {
                            System.out.println("BALANCE FACTOR >= 2!!!");
                        }
                    }
                }
            }
        }
        System.out.println("SUCCESS - IF NONE INCORRECT MESSAGE IS SHOWN :)");
    }

    public static void testOperationsOneByOne() {
        Random random = new Random();// ak zvysovat nasadu v cykle tak zrusit vypisy
        BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
        AVLTree<MyInteger> avltree = new AVLTree<>();
        ArrayList<Integer> insertedToBST = new ArrayList<>();
        ArrayList<Integer> insertedToAVL = new ArrayList<>();
        int limit = 0;
        System.out.println("TESTING INSERT ===========================");
        while (limit < 20000000) {//kazdym cyklom sa zvysi o 2, cize spolu 10mil prvkov v kazdej strukture
            int data = random.nextInt(Integer.MAX_VALUE);
            MyInteger key = new MyInteger(data);
            // cas
            boolean bstInsert = bstree.insert(key);
            if (bstInsert) {
                insertedToBST.add(data);
                ++limit;
            } else {
                if (bstree.find(key) == null) {
                    System.out.println("False insert in BST! index: " + limit);
                }
            }
            // cas
            boolean avlInsert = avltree.insert(key);
            if (avlInsert) {
                insertedToAVL.add(data);
                ++limit;
            } else {
                if (avltree.find(key) == null) {
                    System.out.println("False insert in AVL! index: " + limit);
                }
            }
        }
        System.out.println("TESTING DELETE ===========================");
        for (int i = 0; i < 2000000; ++i) {
            int deletedIndex = random.nextInt(insertedToBST.size());
            MyInteger deletedData = new MyInteger(insertedToBST.get(deletedIndex));
            // cas
            boolean bstDelete = bstree.delete(deletedData);
            if (bstDelete) {
                int lastIndex = insertedToBST.size() - 1;
                Collections.swap(insertedToBST, deletedIndex, lastIndex);
                insertedToBST.remove(lastIndex);
            } else {
                System.out.println("False delete in BST! i: " + i + ". Deleted data: " + insertedToBST.get(deletedIndex));
            }
            // cas
            boolean avlDelete = avltree.delete(deletedData);
            if (avlDelete) {
                int lastIndex = insertedToAVL.size() - 1;
                Collections.swap(insertedToAVL, deletedIndex, lastIndex);
                insertedToAVL.remove(lastIndex);
            } else {
                System.out.println("False delete in AVL! i: " + i + ". Deleted data: " + insertedToAVL.get(deletedIndex));
            }
        }
        System.out.println("TESTING FIND ===========================");
        for (int i = 0; i < 5000000; ++i) {
            int findIndex = random.nextInt(insertedToBST.size());
            MyInteger findData = new MyInteger(insertedToBST.get(findIndex));
            // cas
            BSTNode<MyInteger> bstFind = bstree.find(findData);
            if (bstFind == null) {
                System.out.println("Null find in BST! i: " + i + ". Find data: " + insertedToBST.get(findIndex));
            }
            // cas
            BSTNode<MyInteger> avlFind = avltree.find(findData);
            if (avlFind == null) {
                System.out.println("Null find in AVL! i: " + i + ". Find data: " + insertedToAVL.get(findIndex));
            }
        }

        System.out.println("Size should be 8000000 in BST: " + insertedToBST.size());
        System.out.println("Size should be 8000000 in AVL: " + insertedToAVL.size());
        System.out.println("Number of nodes in BST from level order: " + bstree.levelOrder(bstree.getRoot()).size());
        System.out.println("Number of nodes in AVL from level order: " + avltree.levelOrder(bstree.getRoot()).size());

        System.out.println("SUCCESS - IF NONE INCORRECT MESSAGE IS SHOWN :)");
    }

    public static void measuringOperationsTime() {
        ArrayList<Long> timesBST = new ArrayList<>();
        ArrayList<Long> timesAVL = new ArrayList<>();
        ArrayList<Long> timesTreeSet = new ArrayList<>();
        ArrayList<Long> timesHashSet = new ArrayList<>(); //hashmap, set,map,...
        long start, end;
        BinarySearchTree<MyInteger> bst = new BinarySearchTree<>();
        AVLTree<MyInteger> avl = new AVLTree<>();
        TreeSet<Integer> tSet = new TreeSet<>();
        HashSet<MyInteger> hSet = new HashSet<>();
        ArrayList<Integer> inserted = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000000; ++i) {
            int data = random.nextInt(Integer.MAX_VALUE);
            MyInteger key = new MyInteger(data);
            // BST
            start = System.nanoTime();
            bst.insert(key);
            end = System.nanoTime();
            timesBST.add(end - start);
            // AVL
            start = System.nanoTime();
            avl.insert(key);
            end = System.nanoTime();
            timesAVL.add(end - start);
            // TreeSet
            start = System.nanoTime();
            tSet.add(data);
            end = System.nanoTime();
            timesTreeSet.add(end - start);
            // HashSet
            start = System.nanoTime();
            hSet.add(key);
            end = System.nanoTime();
            timesHashSet.add(end - start);
        }
        long result = 0;
        for (long l : timesBST) {
            result += l;
        }
        result /= 1000;
        System.out.println("INSERT BST: " + result + " nanoseconds");
        result = 0;
        for (long l : timesAVL) {
            result += l;
        }
        result /= 1000;
        System.out.println("INSERT AVL: " + result + " nanoseconds");
        result = 0;
        for (long l : timesTreeSet) {
            result += l;
        }
        result /= 1000;
        System.out.println("INSERT TreeSet: " + result + " nanoseconds");
        result = 0;
        for (long l : timesHashSet) {
            result += l;
        }
        result /= 1000;
        System.out.println("INSERT HashSet: " + result + " nanoseconds");
    }
}
