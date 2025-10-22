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
        testRandomOperations();
//        testOperationsOneByOne();
    }

    public static void testRandomOperations() {
        Random random = new Random();//TODO min, max, intervalove hladanie
        for (int s = 0; s < 1; ++s) {
            long timesBSTInsert = 0, timesBSTDelete = 0, timesBSTFind = 0, timesBSTMin = 0, timesBSTMax = 0;
            long timesAVLInsert = 0, timesAVLDelete = 0, timesAVLFind = 0, timesAVLMin = 0, timesAVLMax = 0;
            long timesTreeSetInsert = 0, timesTreeSetDelete = 0, timesTreeSetFind = 0;
            long timesHashSetInsert = 0, timesHashSetDelete = 0, timesHashSetFind = 0;
            int countInsert = 0, countDelete = 0, countFind = 0, countMinMax = 0;
            long start, end;
            int max = 0, min = Integer.MAX_VALUE;
            BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
            AVLTree<MyInteger> avltree = new AVLTree<>();
            TreeSet<Integer> tSet = new TreeSet<>();
            HashSet<MyInteger> hSet = new HashSet<>();
            ArrayList<Integer> insertedToBST = new ArrayList<>();
            ArrayList<Integer> insertedToAVL = new ArrayList<>();
            random.setSeed(s);
            for (int i = 0; i < 20000000; ++i) {//20mil
                double r = random.nextDouble();
                int data = random.nextInt(Integer.MAX_VALUE);
                MyInteger key = new MyInteger(data);
                if (r < 0.1) {
                    if (!insertedToBST.isEmpty()) {
                        ++countMinMax;
                        start = System.nanoTime();
                        int minBST = bstree.findMinimum().getI();
                        end = System.nanoTime();
                        timesBSTMin += ((end - start) / 1000000);
                        start = System.nanoTime();
                        int minAVL = avltree.findMinimum().getI();
                        end = System.nanoTime();
                        timesAVLMin += ((end - start) / 1000000);
                        start = System.nanoTime();
                        int maxBST = bstree.findMaximum().getI();
                        end = System.nanoTime();
                        timesBSTMax += ((end - start) / 1000000);
                        start = System.nanoTime();
                        int maxAVL = avltree.findMaximum().getI();
                        end = System.nanoTime();
                        timesAVLMax += ((end - start) / 1000000);
                        if (minBST != min || minAVL != min) {
                            System.out.println("Seed " + s + ", index " + i + ": MINIMUM data in BST a AVL: " + minBST + " " + minAVL + " was unsuccessfull! :(");
                        }
                        if (maxBST != max || maxAVL != max) {
                            System.out.println("Seed " + s + ", index " + i + ": MAXIMUM data in BST a AVL: " + maxBST + " " + maxAVL + " was unsuccessfull! :(");
                        }
                    }
                } else if (r < 0.35) { // hladat aj take co sa tam nenachazaju? Nema zmysel stale, otestovanych par predtym
                    if (!insertedToBST.isEmpty() && !insertedToAVL.isEmpty()) {
                        ++countFind;
                        int findIndex = random.nextInt(insertedToBST.size());
                        MyInteger findData = new MyInteger(insertedToBST.get(findIndex));
                        //BST
                        start = System.nanoTime();
                        BSTNode<MyInteger> findBST = bstree.find(findData);
                        end = System.nanoTime();
                        timesBSTFind += ((end - start) / 1000000);
                        if (findBST == null) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in BST: " + insertedToBST.get(findIndex) + " was unsuccessfull! :(");
                        }
                        // AVL
                        start = System.nanoTime();
                        BSTNode<MyInteger> findAVL = avltree.find(findData);
                        end = System.nanoTime();
                        timesAVLFind += ((end - start) / 1000000);
                        if (findAVL == null) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in AVL: " + insertedToAVL.get(findIndex) + " was unsuccessfull! :(");
                        }
                        // TreeSet
                        start = System.nanoTime();
                        tSet.remove(insertedToBST.get(findIndex));
                        end = System.nanoTime();
                        timesTreeSetFind += ((end - start) / 1000000);
                        // HashSet
                        start = System.nanoTime();
                        hSet.add(findData);
                        end = System.nanoTime();
                        timesHashSetFind += ((end - start) / 1000000);
                    } else {
                        if (insertedToBST.size() != insertedToAVL.size()) {
                            System.out.println("BST a AVL are not the same size (one is empty)");
                        }
                    }
                } else if (r < 0.45) { // mazat len take ktore su v strukture, inak to nema zmysel, take co nie su stacilo otestovat par pred tym
                    if (!insertedToBST.isEmpty() && !insertedToAVL.isEmpty()) {
                        ++countDelete;
                        int deletedIndex = random.nextInt(insertedToBST.size());
                        int dData = insertedToBST.get(deletedIndex);
                        MyInteger deletedData = new MyInteger(dData);
                        // BST
                        start = System.nanoTime();
                        boolean deletedBST = bstree.delete(deletedData);
                        end = System.nanoTime();
                        timesBSTDelete += ((end - start) / 1000000);
                        if (!deletedBST) {
                            System.out.println("Seed " + s + ", index " + i + ": DELETED data in BST: " + insertedToBST.get(deletedIndex) + " was unsuccessfull! :(");
                        } else {
                            int lastIndex = insertedToBST.size() - 1;
                            Collections.swap(insertedToBST, deletedIndex, lastIndex);
                            insertedToBST.remove(lastIndex);
                            if (dData == min) {
                                min = Integer.MAX_VALUE;
                                for (int inserted : insertedToBST) {
                                    if (inserted < min) {
                                        min = inserted;
                                    }
                                }
                            }
                            if (dData == max) {
                                max = 0;
                                for (int inserted : insertedToBST) {
                                    if (inserted > max) {
                                        max = inserted;
                                    }
                                }
                            }
                        }
                        // AVL
                        start = System.nanoTime();
                        boolean deletedAVL = avltree.delete(deletedData);
                        end = System.nanoTime();
                        timesAVLDelete += ((end - start) / 1000000);
                        if (!deletedAVL) {
                            System.out.println("Seed " + s + ", index " + i + ": DELETED data in AVL: " + insertedToAVL.get(deletedIndex) + " was unsuccessfull! :(");
                        } else {
                            int lastIndex = insertedToAVL.size() - 1;
                            Collections.swap(insertedToAVL, deletedIndex, lastIndex);
                            insertedToAVL.remove(lastIndex);
                        }
                        // TreeSet
                        start = System.nanoTime();
                        tSet.remove(dData);
                        end = System.nanoTime();
                        timesTreeSetDelete += ((end - start) / 1000000);
                        // HashSet
                        start = System.nanoTime();
                        hSet.add(deletedData);
                        end = System.nanoTime();
                        timesHashSetDelete += ((end - start) / 1000000);
                    } else {
                        if (insertedToBST.size() != insertedToAVL.size()) {
                            System.out.println("BST a AVL are not the same size (one is empty)");
                        }
                    }
                } else {
                    ++countInsert;
                    // BST
                    start = System.nanoTime();
                    boolean insertedBST = bstree.insert(key);
                    end = System.nanoTime();
                    timesBSTInsert += ((end - start) / 1000000);
                    if (!insertedBST) {
                        if (bstree.find(key) == null) {
                            System.out.println("Seed " + s + ", index " + i + ": INSERT data to BST: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        insertedToBST.add(data);
                        if (data < min) {
                            min = data;
                        }
                        if (data > max) {
                            max = data;
                        }
                    }
                    // AVL
                    start = System.nanoTime();
                    boolean insertedAVL = avltree.insert(key);
                    end = System.nanoTime();
                    timesAVLInsert += ((end - start) / 1000000);
                    if (!insertedAVL) {
                        if (avltree.find(key) == null) {
                            System.out.println("Seed " + s + ", index " + i + ": INSERT data to AVL: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        insertedToAVL.add(data);
                    }
                    // TreeSet
                    start = System.nanoTime();
                    tSet.add(data);
                    end = System.nanoTime();
                    timesTreeSetInsert += ((end - start) / 1000000);
                    // HashSet
                    start = System.nanoTime();
                    hSet.add(key);
                    end = System.nanoTime();
                    timesHashSetInsert += ((end - start) / 1000000);
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
            System.out.println("Performance:");
            System.out.println("===============INSERT: count = " + countInsert);
            System.out.println("BST total time: " + timesBSTInsert + " ms.");
            System.out.println("BST average time: " + ((double) timesBSTInsert / countInsert) + " ms.");
            System.out.println("AVL total time: " + timesAVLInsert + " ms.");
            System.out.println("AVL average time: " + ((double) timesAVLInsert / countInsert) + " ms.");
            System.out.println("TreeSet total time: " + timesTreeSetInsert + " ms.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetInsert / countInsert) + " ms.");
            System.out.println("HashSet total time: " + timesHashSetInsert + " ms.");
            System.out.println("HashSet average time: " + ((double) timesHashSetInsert / countInsert) + " ms.");
            System.out.println("===============DELETE: count = " + countDelete);
            System.out.println("BST total time: " + timesBSTDelete + " ms.");
            System.out.println("BST average time: " + ((double) timesBSTDelete / countDelete) + " ms.");
            System.out.println("AVL total time: " + timesAVLDelete + " ms.");
            System.out.println("AVL average time: " + ((double) timesAVLDelete / countDelete) + " ms.");
            System.out.println("TreeSet total time: " + timesTreeSetDelete + " ms.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetDelete / countDelete) + " ms.");
            System.out.println("HashSet total time: " + timesHashSetDelete + " ms.");
            System.out.println("HashSet average time: " + ((double) timesHashSetDelete / countDelete) + " ms.");
            System.out.println("===============FIND: count = " + countFind);
            System.out.println("BST total time: " + timesBSTFind + " ms.");
            System.out.println("BST average time: " + ((double) timesBSTFind / countFind) + " ms.");
            System.out.println("AVL total time: " + timesAVLFind + " ms.");
            System.out.println("AVL average time: " + ((double) timesAVLFind / countFind) + " ms.");
            System.out.println("TreeSet total time: " + timesTreeSetFind + " ms.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetFind / countFind) + " ms.");
            System.out.println("HashSet total time: " + timesHashSetFind + " ms.");
            System.out.println("HashSet average time: " + ((double) timesHashSetFind / countFind) + " ms.");
            System.out.println("===============MINIMUM: count = " + countMinMax);
            System.out.println("BST total time: " + timesBSTMin + " ms.");
            System.out.println("BST average time: " + ((double) timesBSTMin / countMinMax) + " ms.");
            System.out.println("AVL total time: " + timesAVLMin + " ms.");
            System.out.println("AVL average time: " + ((double) timesAVLMin / countMinMax) + " ms.");
            System.out.println("===============MAXIMUM: count = " + countMinMax);
            System.out.println("BST total time: " + timesBSTMax + " ms.");
            System.out.println("BST average time: " + ((double) timesBSTMax / countMinMax) + " ms.");
            System.out.println("AVL total time: " + timesAVLMax + " ms.");
            System.out.println("AVL average time: " + ((double) timesAVLMax / countMinMax) + " ms.");
        }
        System.out.println("SUCCESS - IF NONE INCORRECT MESSAGE IS SHOWN :)");
    }

    public static void testOperationsOneByOne() {
        long timesBST = 0;
        long timesAVL = 0;
        long timesTreeSet = 0;
        long timesHashSet = 0; //moyno pridat dalsie? hashmap,set,map,...
        long start, end;

        Random random = new Random();// ak zvysovat nasadu v cykle tak zrusit vypisy
        BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
        AVLTree<MyInteger> avltree = new AVLTree<>();
        TreeSet<Integer> tSet = new TreeSet<>();
        HashSet<MyInteger> hSet = new HashSet<>();

        ArrayList<Integer> insertedToBST = new ArrayList<>();
        ArrayList<Integer> insertedToAVL = new ArrayList<>();

        int limit = 0;
        int count = 0;
        System.out.println("TESTING INSERT ===========================");
        while (limit < 20000000) {//kazdym cyklom sa zvysi o 2, cize spolu 10mil prvkov v kazdej strukture
            ++count;
            int data = random.nextInt(Integer.MAX_VALUE);
            MyInteger key = new MyInteger(data);
            // BST
            start = System.nanoTime();
            boolean bstInsert = bstree.insert(key);
            end = System.nanoTime();
            timesBST += ((end - start) / 1000000);
            if (bstInsert) {
                insertedToBST.add(data);
                ++limit;
            } else {
                if (bstree.find(key) == null) {
                    System.out.println("False insert in BST! index: " + limit);
                }
            }
            // AVL
            start = System.nanoTime();
            boolean avlInsert = avltree.insert(key);
            end = System.nanoTime();
            timesAVL += ((end - start) / 1000000);
            if (avlInsert) {
                insertedToAVL.add(data);
                ++limit;
            } else {
                if (avltree.find(key) == null) {
                    System.out.println("False insert in AVL! index: " + limit);
                }
            }
            // TreeSet
            start = System.nanoTime();
            tSet.add(data);
            end = System.nanoTime();
            timesTreeSet += ((end - start) / 1000000);
            // HashSet
            start = System.nanoTime();
            hSet.add(key);
            end = System.nanoTime();
            timesHashSet += ((end - start) / 1000000);
        }
        // Vypis priemernej a celkovej rychlosti
        // BST
        double averageTime = (double) timesBST / count;
        System.out.println("BST 10000000 inserts total time: " + timesBST + " ms.");
        System.out.println("BST average time per insert: " + averageTime + " ms.");
        // AVL
        averageTime = (double) timesAVL / count;
        System.out.println("AVL 10000000 inserts total time: " + timesAVL + " ms.");
        System.out.println("AVL average time per insert: " + averageTime + " ms.");
        // TreeSet
        averageTime = (double) timesTreeSet / count;
        System.out.println("TreeSet 10000000 inserts total time: " + timesTreeSet + " ms.");
        System.out.println("TreeSet average time per insert: " + averageTime + " ms.");
        // HashSet
        averageTime = (double) timesHashSet / count;
        System.out.println("HashSet 10000000 inserts total time: " + timesHashSet + " ms.");
        System.out.println("HashSet average time per insert: " + averageTime + " ms.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        timesHashSet = 0;
        System.out.println("TESTING DELETE ===========================");
        for (int i = 0; i < 2000000; ++i) {
            int deletedIndex = random.nextInt(insertedToBST.size());
            MyInteger deletedData = new MyInteger(insertedToBST.get(deletedIndex));
            // BST
            start = System.nanoTime();
            boolean bstDelete = bstree.delete(deletedData);
            end = System.nanoTime();
            timesBST += ((end - start) / 1000000);
            if (bstDelete) {
                int lastIndex = insertedToBST.size() - 1;
                Collections.swap(insertedToBST, deletedIndex, lastIndex);
                insertedToBST.remove(lastIndex);
            } else {
                System.out.println("False delete in BST! i: " + i + ". Deleted data: " + insertedToBST.get(deletedIndex));
            }
            // TreeSet
            start = System.nanoTime();
            tSet.remove(insertedToAVL.get(deletedIndex));
            end = System.nanoTime();
            timesTreeSet += ((end - start) / 1000000);
            // AVL
            start = System.nanoTime();
            boolean avlDelete = avltree.delete(deletedData);
            end = System.nanoTime();
            timesAVL += ((end - start) / 1000000);
            if (avlDelete) {
                int lastIndex = insertedToAVL.size() - 1;
                Collections.swap(insertedToAVL, deletedIndex, lastIndex);
                insertedToAVL.remove(lastIndex);
            } else {
                System.out.println("False delete in AVL! i: " + i + ". Deleted data: " + insertedToAVL.get(deletedIndex));
            }
            // HashSet
            start = System.nanoTime();
            hSet.add(deletedData);
            end = System.nanoTime();
            timesHashSet += ((end - start) / 1000000);
        }
        // BST
        averageTime = (double) timesBST / 2000000;
        System.out.println("BST 2000000 deletes total time: " + timesBST + " ms.");
        System.out.println("BST average time per delete: " + averageTime + " ms.");
        // AVL
        averageTime = (double) timesAVL / 2000000;
        System.out.println("AVL 2000000 deletes total time: " + timesAVL + " ms.");
        System.out.println("AVL average time per delete: " + averageTime + " ms.");
        // TreeSet
        averageTime = (double) timesTreeSet / 2000000;
        System.out.println("TreeSet 2000000 deletes total time: " + timesTreeSet + " ms.");
        System.out.println("TreeSet average time per delete: " + averageTime + " ms.");
        // HashSet
        averageTime = (double) timesHashSet / 2000000;
        System.out.println("HashSet 2000000 deletes total time: " + timesHashSet + " ms.");
        System.out.println("HashSet average time per delete: " + averageTime + " ms.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        timesHashSet = 0;
        System.out.println("TESTING FIND ===========================");
        for (int i = 0; i < 5000000; ++i) {
            int findIndex = random.nextInt(insertedToBST.size());
            MyInteger findData = new MyInteger(insertedToBST.get(findIndex));
            // BST
            start = System.nanoTime();
            BSTNode<MyInteger> bstFind = bstree.find(findData);
            end = System.nanoTime();
            timesBST += ((end - start) / 1000000);
            if (bstFind == null) {
                System.out.println("Null find in BST! i: " + i + ". Find data: " + insertedToBST.get(findIndex));
            }
            // AVL
            start = System.nanoTime();
            BSTNode<MyInteger> avlFind = avltree.find(findData);
            end = System.nanoTime();
            timesAVL += ((end - start) / 1000000);
            if (avlFind == null) {
                System.out.println("Null find in AVL! i: " + i + ". Find data: " + insertedToAVL.get(findIndex));
            }
            // TreeSet
            start = System.nanoTime();
            tSet.remove(insertedToBST.get(findIndex));
            end = System.nanoTime();
            timesTreeSet += ((end - start) / 1000000);
            // HashSet
            start = System.nanoTime();
            hSet.add(findData);
            end = System.nanoTime();
            timesHashSet += ((end - start) / 1000000);
        }
        // BST
        averageTime = (double) timesBST / 5000000;
        System.out.println("BST 5000000 finds total time: " + timesBST + " ms.");
        System.out.println("BST average time per find: " + averageTime + " ms.");
        // AVL
        averageTime = (double) timesAVL / 5000000;
        System.out.println("AVL 5000000 finds total time: " + timesAVL + " ms.");
        System.out.println("AVL average time per find: " + averageTime + " ms.");
        // TreeSet
        averageTime = (double) timesTreeSet / 5000000;
        System.out.println("TreeSet 5000000 finds total time: " + timesTreeSet + " ms.");
        System.out.println("TreeSet average time per find: " + averageTime + " ms.");
        // HashSet
        averageTime = (double) timesHashSet / 5000000;
        System.out.println("HashSet 5000000 finds total time: " + timesHashSet + " ms.");
        System.out.println("HashSet average time per find: " + averageTime + " ms.");

        System.out.println("======================================================");
        System.out.println("Size should be 8000000 in BST: " + insertedToBST.size());
        System.out.println("Size should be 8000000 in AVL: " + insertedToAVL.size());
        System.out.println("Number of nodes in BST from level order: " + bstree.levelOrder(bstree.getRoot()).size());
        System.out.println("Number of nodes in AVL from level order: " + avltree.levelOrder(bstree.getRoot()).size());

        System.out.println("SUCCESS - IF NONE INCORRECT MESSAGE IS SHOWN :)");
    }
}
