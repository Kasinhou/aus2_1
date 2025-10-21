package test;

import structure.AVLNode;
import structure.AVLTree;
import structure.BSTNode;
import structure.BinarySearchTree;

import java.awt.print.Book;
import java.util.*;

public class Tester {
    public static void main(String[] args) {
//        testRandomOperations();
//        testOperationsOneByOne();
        testRandomOperationsAVL();
//        measuringOperationsTime();
    }

    // chcem merat cas vsetkych, ale chcem priemerny cas insertu alebo priemerny cas insertu pre 10000000 prvkov???
    public static void testRandomOperations() {
        for (int s = 0; s < 100; ++s) {
            BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
            AVLTree<MyInteger> avltree = new AVLTree<>(); // aj ostatne tu? TODO
            ArrayList<Integer> insertedToBST = new ArrayList<>();
            Random random = new Random(s); //setseed
//            System.out.println(s);
            for (int i = 0; i < 100000; ++i) {
                double r = random.nextDouble();
                int data = random.nextInt(Integer.MAX_VALUE);
                MyInteger key = new MyInteger(data);
                if (r < 0.4) {
                    if (!insertedToBST.isEmpty()) {//hladat take co sa nachadzaju
                        int findIndex = random.nextInt(insertedToBST.size());
                        MyInteger findData = new MyInteger(insertedToBST.get(findIndex));
                        //merat cas
                        BSTNode<MyInteger> findBST = bstree.find(findData);
                        if (findBST == null) {
                            System.out.println("Seed " + s + " - " + i + ": FIND THIS DATA: " + insertedToBST.get(findIndex) + " was unsuccessfull! :(");
                        }
                    } // aj take co nie???
                } else if (r < 0.6) {
                    if (r < 0.6) { // take co su vlozene
                        if (!insertedToBST.isEmpty()) {
                            int deletedIndex = random.nextInt(insertedToBST.size());
                            MyInteger deletedData = new MyInteger(insertedToBST.get(deletedIndex));
                            // merat cas
                            boolean deletedBST = bstree.delete(deletedData);
                            if (!deletedBST) {
                                System.out.println("Seed " + s + " - " + i + ": DELETED THIS DATA: " + insertedToBST.get(deletedIndex) + " was unsuccessfull! :(");
                            } else {
                                //   insertedInts.remove(deletedIndex);
                                int lastIndex = insertedToBST.size() - 1;
                                Collections.swap(insertedToBST, deletedIndex, lastIndex);
                                insertedToBST.remove(lastIndex);
                            }
                        }
                    } else { // mazat take co nie su?
                    }
                } else {
                    // merat cas
                    boolean insertedBST = bstree.insert(key);
                    if (!insertedBST) {
                        if (bstree.find(key) == null) {
                            System.out.println("Seed " + s + " - " + i + ": INSERT THIS DATA: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        insertedToBST.add(data);
                    }
                }
            }
//TODO dorobit min a max, intervalove
            ArrayList<BSTNode<MyInteger>> level = bstree.levelOrder(bstree.getRoot());
            int insertedSize = insertedToBST.size();
            int levelSize = (level == null) ? 0 : level.size();
            if (insertedSize != levelSize) {
                System.out.println("INCORRECT");
            }
//            System.out.println("Number of inserted data: " + insertedInts.size());
//            System.out.println("Number of nodes in structure: " + structure.levelOrder(structure.getRoot()).size());
        }
    }

    public static void testOperationsOneByOne() {
        for (int s = 0; s < 1; ++s) {
            BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
            ArrayList<Integer> insertedInts = new ArrayList<>();
            Random random = new Random(s);
            int limit = 0;
            System.out.println("TESTING INSERT ===========================");
            while (limit < 10000000) {
                int data = random.nextInt(Integer.MAX_VALUE);
                MyInteger key = new MyInteger(data);
                if (bstree.insert(key)) {
                    insertedInts.add(data);
                    ++limit;
                } else {
                    if (bstree.find(key) == null) {
                        System.out.println("Insert vratil false a kluc sa nenasiel. Seed: " + s + " a limit: " + limit);
                    }
                }
            }
            System.out.println("TESTING DELETE ===========================");
            for (int i = 0; i < 2000000; ++i) {
//                System.out.println(i);
                int deletedIndex = random.nextInt(insertedInts.size());
                MyInteger deletedData = new MyInteger(insertedInts.get(deletedIndex));
                if (bstree.delete(deletedData)) {
                    int lastIndex = insertedInts.size() - 1;
                    Collections.swap(insertedInts, deletedIndex, lastIndex);
                    insertedInts.remove(lastIndex);
//                    insertedInts.remove(deletedIndex);
                    --limit;
                } else {
                    System.out.println("Delete vratil false aj ked by mal true. Seed: " + s + " a i: " + i + ". Mazane data: " + insertedInts.get(deletedIndex));
                }
            }
            System.out.println("TESTING FIND ===========================");
            for (int i = 0; i < 5000000; ++i) {
                int findIndex = random.nextInt(insertedInts.size());
                MyInteger deletedData = new MyInteger(insertedInts.get(findIndex));
                if (bstree.find(deletedData) == null) {
                    System.out.println("Find vratil false aj ked by mal true. Seed: " + s + " a i: " + i + ". Hladane data: " + insertedInts.get(findIndex));
                }
            }

            System.out.println("Size of insertedInts (should be 8000000): " + insertedInts.size());
            System.out.println("Number of nodes in structure from level order: " + bstree.levelOrder(bstree.getRoot()).size());
            System.out.println("Number of nodes in structure from in order: " + bstree.inOrder(bstree.getRoot()).size());
        }
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

    public static void testRandomOperationsAVL() {
        Random random = new Random();
        for (int s = 0; s < 1000; ++s) {
            AVLTree<MyInteger> avltree = new AVLTree<>();
            ArrayList<Integer> insertedToAVL = new ArrayList<>();
            random.setSeed(s);
//            System.out.println(s);
            for (int i = 0; i < 100000; ++i) {
                double r = random.nextDouble();
//                int data = random.nextInt(100);
                int data = random.nextInt(Integer.MAX_VALUE);
                MyInteger key = new MyInteger(data);
//                System.out.println(r + " + " + i + " = " + data);
                if (r < 0.3) {
                    if (!insertedToAVL.isEmpty()) {
                        int findIndex = random.nextInt(insertedToAVL.size());
                        MyInteger findData = new MyInteger(insertedToAVL.get(findIndex));
//                        System.out.println("find " + findData.getI());
                        if (avltree.find(findData) == null) {
                            System.out.println("Seed " + s + " - " + i + ": FIND THIS DATA IN AVL: " + insertedToAVL.get(findIndex) + " was unsuccessfull! :(");
                        }
                    }
                } else if (r < 0.5) {
                    if (r < 0.5) {
                        if (!insertedToAVL.isEmpty()) {
                            int deletedIndex = random.nextInt(insertedToAVL.size());
                            MyInteger deletedData = new MyInteger(insertedToAVL.get(deletedIndex));
//                            System.out.println("deleted " + deletedData.getI());
                            if (!avltree.delete(deletedData)) {
                                System.out.println("Seed " + s + " - " + i + ": DELETED THIS DATA IN AVL: " + insertedToAVL.get(deletedIndex) + " was unsuccessfull! :(");
                            } else {
                                int lastIndex = insertedToAVL.size() - 1;
                                Collections.swap(insertedToAVL, deletedIndex, lastIndex);
                                insertedToAVL.remove(lastIndex);
                            }
                        }

                    } else {

                    }
                } else {
                    if (!avltree.insert(key)) {
                        if (avltree.find(key) == null) {
                            System.out.println("Seed " + s + " - " + i + ": INSERT THIS DATA TO AVL: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        insertedToAVL.add(data);
                    }
                }
            }
            if (!insertedToAVL.isEmpty()) {
                if (insertedToAVL.size() != avltree.levelOrder(avltree.getRoot()).size()) {
                    System.out.println("INCORRECT");
                }

                ArrayList<BSTNode<MyInteger>> level = avltree.levelOrder(avltree.getRoot());
                for (BSTNode<MyInteger> mi : level) {
                    if (mi instanceof AVLNode<MyInteger>) {
                        int bf = ((AVLNode<MyInteger>) mi).getBalanceFactor();
                        if (Math.abs(bf) >= 2) {
                            System.out.println("BALANCE FACTOR >= 2!!!");
                        }
                    }
                }
            }
        }
        System.out.println("SUCCESS");
    }
}
