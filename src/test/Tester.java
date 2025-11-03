package test;

import structure.AVLTree;
import structure.BinarySearchTree;

import java.util.*;

/**
 * This class is testing both BST and AVL trees (functionality and performance of methods) using both random and in sequence operations.
 * There are compared with other structures (TreeSet - all and HashSet - insert, delete, find).
 */
public class Tester {
    public static void main(String[] args) {
//        testRandomOperations();
        testOperationsOneByOne();
    }

    public static void testRandomOperations() {
        Random random = new Random();
        for (int s = 0; s < 1; ++s) {
            long timesBSTInsert = 0, timesBSTDelete = 0, timesBSTFind = 0, timesBSTMin = 0, timesBSTMax = 0, timesBSTInterval = 0;
            long timesAVLInsert = 0, timesAVLDelete = 0, timesAVLFind = 0, timesAVLMin = 0, timesAVLMax = 0, timesAVLInterval = 0;
            long timesTreeSetInsert = 0, timesTreeSetDelete = 0, timesTreeSetFind = 0, timesTreeSetMin = 0, timesTreeSetMax = 0, timesTreeSetInterval = 0;
            long timesHashSetInsert = 0, timesHashSetDelete = 0, timesHashSetFind = 0;
            int countInsert = 0, countDelete = 0, countFind = 0, countMinMax = 0, countInterval = 0;
            long start, end;
            int max = 0, min = Integer.MAX_VALUE;
            int minIndex, maxIndex;
            BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
            AVLTree<MyInteger> avltree = new AVLTree<>();
            TreeSet<Integer> tSet = new TreeSet<>();
            HashSet<Integer> hSet = new HashSet<>();
            ArrayList<Integer> helpStructures = new ArrayList<>();
            random.setSeed(s);
            for (int i = 0; i < 1000000; ++i) {//20mil
                if (i % 100000 == 0) {
                    System.out.println(i);
                }
                double r = random.nextDouble();
                int data = random.nextInt(Integer.MAX_VALUE);
                MyInteger key = new MyInteger(data);
                //MIN MAX
                if (r < 0.1) {//0.1
                    if (!helpStructures.isEmpty()) {
                        ++countMinMax;
                        start = System.nanoTime();
                        int minBST = bstree.findMinimum().getI();
                        end = System.nanoTime();
                        timesBSTMin += (end - start);
                        start = System.nanoTime();
                        int minAVL = avltree.findMinimum().getI();
                        end = System.nanoTime();
                        timesAVLMin += (end - start);
                        start = System.nanoTime();
                        int minTSet = tSet.first();
                        end = System.nanoTime();
                        timesTreeSetMin += (end - start);
                        start = System.nanoTime();
                        int maxBST = bstree.findMaximum().getI();
                        end = System.nanoTime();
                        timesBSTMax += (end - start);
                        start = System.nanoTime();
                        int maxAVL = avltree.findMaximum().getI();
                        end = System.nanoTime();
                        timesAVLMax += (end - start);
                        start = System.nanoTime();
                        int maxTSet = tSet.last();
                        end = System.nanoTime();
                        timesTreeSetMax += (end - start);
                        if (minBST != min || minAVL != min) {
                            System.out.println("Seed " + s + ", index " + i + ": MINIMUM data in BST a AVL: " + minBST + " " + minAVL + " was unsuccessfull! :(");
                        }
                        if (maxBST != max || maxAVL != max) {
                            System.out.println("Seed " + s + ", index " + i + ": MAXIMUM data in BST a AVL: " + maxBST + " " + maxAVL + " was unsuccessfull! :(");
                        }
                        if (minTSet != min || maxTSet != max) {
                            System.out.println("Seed " + s + ", index " + i + ": MAXIMUM/MINIMUM data in TreeSet: " + minTSet + " " + maxTSet + " was unsuccessfull! :(");
                        }
                    }
                // FIND
                } else if (r < 0.35) {//0.35 // hladat aj take co sa tam nenachazaju? Nema zmysel stale, otestovanych par predtym
                    if (!helpStructures.isEmpty()) {
                        ++countFind;
                        int findIndex = random.nextInt(helpStructures.size());
                        MyInteger findData = new MyInteger(helpStructures.get(findIndex));
                        //BST
                        start = System.nanoTime();
                        MyInteger findBST = bstree.find(findData);
                        end = System.nanoTime();
                        timesBSTFind += (end - start);
                        if (findBST == null) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in BST: " + helpStructures.get(findIndex) + " was unsuccessfull! :(");
                        }
                        // AVL
                        start = System.nanoTime();
                        MyInteger findAVL = avltree.find(findData);
                        end = System.nanoTime();
                        timesAVLFind += (end - start);
                        if (findAVL == null) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in AVL: " + helpStructures.get(findIndex) + " was unsuccessfull! :(");
                        }
                        // TreeSet
                        start = System.nanoTime();
                        boolean tSetFind = tSet.contains(helpStructures.get(findIndex));
                        end = System.nanoTime();
                        timesTreeSetFind += (end - start);
                        // HashSet
                        start = System.nanoTime();
                        boolean hSetFind = hSet.contains(helpStructures.get(findIndex));
                        end = System.nanoTime();
                        timesHashSetFind += (end - start);
                        if (!tSetFind || !hSetFind) {
                            System.out.println("Seed " + s + ", index " + i + ": FIND data in TreeSet or HashSet: " + helpStructures.get(findIndex) + " was unsuccessfull! :(");
                        }
                    }
                // DELETE
                } else if (r < 0.45) {//0.45 // mazat len take ktore su v strukture, inak to nema zmysel, take co nie su stacilo otestovat par pred tym
                    if (!helpStructures.isEmpty()) {
                        ++countDelete;
                        int deletedIndex = random.nextInt(helpStructures.size());
                        int dData = helpStructures.get(deletedIndex);
                        MyInteger deletedData = new MyInteger(dData);
                        // BST
                        start = System.nanoTime();
                        boolean deletedBST = bstree.delete(deletedData);
                        end = System.nanoTime();
                        timesBSTDelete += (end - start);
                        // AVL
                        start = System.nanoTime();
                        boolean deletedAVL = avltree.delete(deletedData);
                        end = System.nanoTime();
                        timesAVLDelete += (end - start);
                        if (!deletedBST) {
                            System.out.println("Seed " + s + ", index " + i + ": DELETED data in BST: " + helpStructures.get(deletedIndex) + " was unsuccessfull! :(");
                        } else if (!deletedAVL) {
                            System.out.println("Seed " + s + ", index " + i + ": DELETED data in AVL: " + helpStructures.get(deletedIndex) + " was unsuccessfull! :(");
                        } else {
                            int lastIndex = helpStructures.size() - 1;
                            Collections.swap(helpStructures, deletedIndex, lastIndex);
                            helpStructures.remove(lastIndex);
                            if (dData == min) {
                                min = Integer.MAX_VALUE;
                                for (int inserted : helpStructures) {
                                    if (inserted < min) {
                                        min = inserted;
                                    }
                                }
                            }
                            if (dData == max) {
                                max = 0;
                                for (int inserted : helpStructures) {
                                    if (inserted > max) {
                                        max = inserted;
                                    }
                                }
                            }
                        }
                        // TreeSet
                        start = System.nanoTime();
                        boolean deletedTSET = tSet.remove(dData);
                        end = System.nanoTime();
                        if (!deletedTSET) {
                            System.out.println("NOT REMOVED FROM TSET");
                        }
                        timesTreeSetDelete += (end - start);
                        // HashSet
                        start = System.nanoTime();
                        boolean deletedHSET = hSet.remove(dData);
                        end = System.nanoTime();
                        if (!deletedHSET) {
                            System.out.println("NOT REMOVED FROM HSET");
                        }
                        timesHashSetDelete += (end - start);
                    }
                // INTERVAL FIND
                } else if (r < 0.5) {//0.5
                    if (!helpStructures.isEmpty()) {
                        ++countInterval;
                        // definovat pred usporiadany zoznam ako
                        Collections.sort(helpStructures);
                        int bstCount = helpStructures.size();
                        if (bstCount >= 1000) {
                            minIndex = random.nextInt(bstCount - 500);
//                            maxIndex = minIndex + 500;
                            maxIndex = minIndex + random.nextInt(bstCount - minIndex - 500);
                        } else {
                            minIndex = random.nextInt(bstCount);
                            maxIndex = minIndex + random.nextInt(bstCount - minIndex);
                        }
                        MyInteger from = new MyInteger(helpStructures.get(minIndex));
                        MyInteger to = new MyInteger(helpStructures.get(maxIndex));
                        start = System.nanoTime();
                        ArrayList<MyInteger> bstInterval = bstree.findInterval(from, to);
                        end = System.nanoTime();
                        timesBSTInterval += (end - start);

                        if ((maxIndex - minIndex + 1) != bstInterval.size()) {
                            System.out.println("Seed " + s + ", index " + i + " The BST interval find did not even get the same number of results.");
                        }
                        for (int x = minIndex; x <= maxIndex; ++x) {
                            if (helpStructures.get(x) != bstInterval.get(x - minIndex).getI()) {
                                System.out.print("Seed " + s + ", index " + i + ": INTERVAL FIND data in BST: " + " was unsuccessfull! :(");
                                System.out.println(helpStructures.get(x) + " != " + bstInterval.get(x - minIndex).getI());
                            }
                        }
                        // AVL
                        start = System.nanoTime();
                        ArrayList<MyInteger> avlInterval = avltree.findInterval(from, to);
                        end = System.nanoTime();
                        timesAVLInterval += (end - start);

                        if ((maxIndex - minIndex + 1) != avlInterval.size()) {
                            System.out.println("Seed " + s + ", index " + i + " The AVL interval find did not even get the same number of results.");
                        }
                        for (int x = minIndex; x <= maxIndex; ++x) {
                            if (helpStructures.get(x) != avlInterval.get(x - minIndex).getI()) {
                                System.out.print("Seed " + s + ", index " + i + ": INTERVAL FIND data in AVL: " + " was unsuccessfull! :(");
                                System.out.println(helpStructures.get(x) + " != " + avlInterval.get(x - minIndex).getI());
                            }
                        }
                        // TreeSet
                        start = System.nanoTime();
                        SortedSet<Integer> tSetInterval = tSet.subSet(helpStructures.get(minIndex), true, helpStructures.get(maxIndex), true);
                        end = System.nanoTime();
                        timesTreeSetInterval += (end - start);
                        if ((maxIndex - minIndex + 1) != tSetInterval.size()) {
                            System.out.println("Seed " + s + ", index " + i + " The TreeSet interval find did not even get the same number of results.");
                        }
                        List<Integer> listTSetInterval = new ArrayList<>(tSetInterval);
                        for (int x = minIndex; x <= maxIndex; ++x) {
                            if (!Objects.equals(helpStructures.get(x), listTSetInterval.get(x - minIndex))) {
                                System.out.print("Seed " + s + ", index " + i + ": INTERVAL FIND data in TreeSet: " + " was unsuccessfull! :(");
                                System.out.println(helpStructures.get(x) + " != " + listTSetInterval.get(x - minIndex));
                            }
                        }
                        // HashSet
                        // does not allow
                    }
                // INSERT
                } else {
                    ++countInsert;
                    // BST
                    start = System.nanoTime();
                    boolean insertedBST = bstree.insert(key);
                    end = System.nanoTime();
                    timesBSTInsert += (end - start);
                    // AVL
                    start = System.nanoTime();
                    boolean insertedAVL = avltree.insert(key);
                    end = System.nanoTime();
                    timesAVLInsert += (end - start);
                    if (!insertedBST) {
                        if (bstree.find(key) == null) {
                            System.out.println("Seed " + s + ", index " + i + ": INSERT data to BST: " + data + " was unsuccessfull! :(");
                        }
                    } else if (!insertedAVL) {
                        if (avltree.find(key) == null) {
                            System.out.println("Seed " + s + ", index " + i + ": INSERT data to AVL: " + data + " was unsuccessfull! :(");
                        }
                    } else {
                        helpStructures.add(data);
                        if (data < min) {
                            min = data;
                        }
                        if (data > max) {
                            max = data;
                        }
                    }
                    // TreeSet
                    start = System.nanoTime();
                    tSet.add(data);
                    end = System.nanoTime();
                    timesTreeSetInsert += (end - start);
                    // HashSet
                    start = System.nanoTime();
                    hSet.add(data);
                    end = System.nanoTime();
                    timesHashSetInsert += (end - start);
                }
            }
            // kontrola na konci, velkost rovnaka pri vlozenych a level order
            if (!helpStructures.isEmpty()) {
                if (helpStructures.size() != bstree.levelOrder().size()) {
                    System.out.println("NOT EQUAL SIZE OF INSERTED AND LEVEL ORDER IN BST!");
                }
                if (helpStructures.size() != avltree.levelOrder().size()) {
                    System.out.println("NOT EQUAL SIZE OF INSERTED AND LEVEL ORDER IN AVL!");
                }
                if (avltree.isNotAVL()) {// Kontrola vlastnosti AVL, ci je naozaj |BF| <= 1
                    System.out.println("AVL is not really AVL, some node has difference of heights of subtrees more that 1.");
                } else {
                    System.out.println("AVL is AVL, all the nodes have |BF| <= 1.");
                }
                // Kontrola inorder prehliadky
                Collections.sort(helpStructures);
                //BST
                ArrayList<MyInteger> bstInorder = bstree.inOrder();
                if (bstInorder.size() != helpStructures.size()) {
                    System.out.println("BST INORDER does not have the same size as help structure.");
                    System.out.println(bstInorder.size() + " != " + helpStructures.size());
                }
                for (int x = 0; x < helpStructures.size(); ++x) {
                    if (helpStructures.get(x) != bstInorder.get(x).getI()) {
                        System.out.print("Index " + x + ": in inorder in BST was unsuccessfull! :(");
                        System.out.println(helpStructures.get(x) + " != " + bstInorder.get(x).getI());
                    }
                }
                //AVL
                ArrayList<MyInteger> avlInorder = avltree.inOrder();
                if (avlInorder.size() != helpStructures.size()) {
                    System.out.println("AVL INORDER does not have the same size as help structure.");
                    System.out.println(avlInorder.size() + " != " + helpStructures.size());
                }
                for (int x = 0; x < helpStructures.size(); ++x) {
                    if (helpStructures.get(x) != avlInorder.get(x).getI()) {
                        System.out.print("Index " + x + ": in inorder in AVL was unsuccessfull! :(");
                        System.out.println(helpStructures.get(x) + " != " + avlInorder.get(x).getI());
                    }
                }
            }
            System.out.println("Performance:");
            System.out.println("===============INSERT: count = " + countInsert);
            System.out.println("BST total time: " + timesBSTInsert + " ns.");
            System.out.println("BST average time: " + ((double) timesBSTInsert / countInsert) + " ns.");
            System.out.println("AVL total time: " + timesAVLInsert + " ns.");
            System.out.println("AVL average time: " + ((double) timesAVLInsert / countInsert) + " ns.");
            System.out.println("TreeSet total time: " + timesTreeSetInsert + " ns.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetInsert / countInsert) + " ns.");
            System.out.println("HashSet total time: " + timesHashSetInsert + " ns.");
            System.out.println("HashSet average time: " + ((double) timesHashSetInsert / countInsert) + " ns.");
            System.out.println("===============DELETE: count = " + countDelete);
            System.out.println("BST total time: " + timesBSTDelete + " ns.");
            System.out.println("BST average time: " + ((double) timesBSTDelete / countDelete) + " ns.");
            System.out.println("AVL total time: " + timesAVLDelete + " ns.");
            System.out.println("AVL average time: " + ((double) timesAVLDelete / countDelete) + " ns.");
            System.out.println("TreeSet total time: " + timesTreeSetDelete + " ns.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetDelete / countDelete) + " ns.");
            System.out.println("HashSet total time: " + timesHashSetDelete + " ns.");
            System.out.println("HashSet average time: " + ((double) timesHashSetDelete / countDelete) + " ns.");
            System.out.println("===============FIND: count = " + countFind);
            System.out.println("BST total time: " + timesBSTFind + " ns.");
            System.out.println("BST average time: " + ((double) timesBSTFind / countFind) + " ns.");
            System.out.println("AVL total time: " + timesAVLFind + " ns.");
            System.out.println("AVL average time: " + ((double) timesAVLFind / countFind) + " ns.");
            System.out.println("TreeSet total time: " + timesTreeSetFind + " ns.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetFind / countFind) + " ns.");
            System.out.println("HashSet total time: " + timesHashSetFind + " ns.");
            System.out.println("HashSet average time: " + ((double) timesHashSetFind / countFind) + " ns.");
            System.out.println("===============FIND INTERVAL: count = " + countInterval);
            System.out.println("BST total time: " + timesBSTInterval + " ns.");
            System.out.println("BST average time: " + ((double) timesBSTInterval / countInterval) + " ns.");
            System.out.println("AVL total time: " + timesAVLInterval + " ns.");
            System.out.println("AVL average time: " + ((double) timesAVLInterval / countInterval) + " ns.");
            System.out.println("TreeSet total time: " + timesTreeSetInterval + " ns.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetInterval / countInterval) + " ns.");
            System.out.println("===============MINIMUM: count = " + countMinMax);
            System.out.println("BST total time: " + timesBSTMin + " ns.");
            System.out.println("BST average time: " + ((double) timesBSTMin / countMinMax) + " ns.");
            System.out.println("AVL total time: " + timesAVLMin + " ns.");
            System.out.println("AVL average time: " + ((double) timesAVLMin / countMinMax) + " ns.");
            System.out.println("TreeSet total time: " + timesTreeSetMin + " ns.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetMin / countMinMax) + " ns.");
            System.out.println("===============MAXIMUM: count = " + countMinMax);
            System.out.println("BST total time: " + timesBSTMax + " ns.");
            System.out.println("BST average time: " + ((double) timesBSTMax / countMinMax) + " ns.");
            System.out.println("AVL total time: " + timesAVLMax + " ns.");
            System.out.println("AVL average time: " + ((double) timesAVLMax / countMinMax) + " ns.");
            System.out.println("TreeSet total time: " + timesTreeSetMax + " ns.");
            System.out.println("TreeSet average time: " + ((double) timesTreeSetMax / countMinMax) + " ns.");
        }
        System.out.println("SUCCESS - IF NONE INCORRECT MESSAGE IS SHOWN :)");
    }

    public static void testOperationsOneByOne() {
        long timesBST = 0;
        long timesAVL = 0;
        long timesTreeSet = 0;
        long timesHashSet = 0;
        long start, end;
        int minIndex, maxIndex;

        Random random = new Random();// ak zvysovat nasadu v cykle tak zrusit vypisy
        BinarySearchTree<MyInteger> bstree = new BinarySearchTree<>();
        AVLTree<MyInteger> avltree = new AVLTree<>();
        TreeSet<Integer> tSet = new TreeSet<>();
        HashSet<Integer> hSet = new HashSet<>();

        ArrayList<Integer> helpStructure = new ArrayList<>();

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
            timesBST += (end - start);
            // AVL
            start = System.nanoTime();
            boolean avlInsert = avltree.insert(key);
            end = System.nanoTime();
            timesAVL += (end - start);
            if (bstInsert) {
                helpStructure.add(data);
                ++limit;
            } else {
                if (bstree.find(key) == null) {
                    System.out.println("False insert in BST! index: " + limit);
                }
            }
            if (avlInsert) {
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
            timesTreeSet += (end - start);
            // HashSet
            start = System.nanoTime();
            hSet.add(data);
            end = System.nanoTime();
            timesHashSet += (end - start);
        }
        // Vypis priemernej a celkovej rychlosti
        // BST
        double averageTime = (double) timesBST / count;
        System.out.println("BST 10000000 inserts total time: " + timesBST + " ns.");
        System.out.println("BST average time per insert: " + averageTime + " ns.");
        // AVL
        averageTime = (double) timesAVL / count;
        System.out.println("AVL 10000000 inserts total time: " + timesAVL + " ns.");
        System.out.println("AVL average time per insert: " + averageTime + " ns.");
        // TreeSet
        averageTime = (double) timesTreeSet / count;
        System.out.println("TreeSet 10000000 inserts total time: " + timesTreeSet + " ns.");
        System.out.println("TreeSet average time per insert: " + averageTime + " ns.");
        // HashSet
        averageTime = (double) timesHashSet / count;
        System.out.println("HashSet 10000000 inserts total time: " + timesHashSet + " ns.");
        System.out.println("HashSet average time per insert: " + averageTime + " ns.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        timesHashSet = 0;
        System.out.println("TESTING DELETE ===========================");
        for (int i = 0; i < 2000000; ++i) {
            int deletedIndex = random.nextInt(helpStructure.size());
            MyInteger deletedData = new MyInteger(helpStructure.get(deletedIndex));
            // TreeSet
            start = System.nanoTime();
            boolean tSetDelete = tSet.remove(helpStructure.get(deletedIndex));
            end = System.nanoTime();
            if (!tSetDelete) {
                System.out.println("False delete in TreeSet! i: " + i + ". Deleted data: " + helpStructure.get(deletedIndex));
            }
            timesTreeSet += (end - start);
            // HashSet
            start = System.nanoTime();
            boolean hSetDelete = hSet.remove(helpStructure.get(deletedIndex));
            end = System.nanoTime();
            if (!hSetDelete) {
                System.out.println("False delete in HashSet! i: " + i + ". Deleted data: " + helpStructure.get(deletedIndex));
            }
            timesHashSet += (end - start);
            // AVL
            start = System.nanoTime();
            boolean avlDelete = avltree.delete(deletedData);
            end = System.nanoTime();
            timesAVL += (end - start);
            if (!avlDelete) {
                System.out.println("False delete in AVL! i: " + i + ". Deleted data: " + helpStructure.get(deletedIndex));
            }
            // BST
            start = System.nanoTime();
            boolean bstDelete = bstree.delete(deletedData);
            end = System.nanoTime();
            timesBST += (end - start);
            if (bstDelete) {
                int lastIndex = helpStructure.size() - 1;
                Collections.swap(helpStructure, deletedIndex, lastIndex);
                helpStructure.remove(lastIndex);
            } else {
                System.out.println("False delete in BST! i: " + i + ". Deleted data: " + helpStructure.get(deletedIndex));
            }
        }
        // BST
        averageTime = (double) timesBST / 2000000;
        System.out.println("BST 2000000 deletes total time: " + timesBST + " ns.");
        System.out.println("BST average time per delete: " + averageTime + " ns.");
        // AVL
        averageTime = (double) timesAVL / 2000000;
        System.out.println("AVL 2000000 deletes total time: " + timesAVL + " ns.");
        System.out.println("AVL average time per delete: " + averageTime + " ns.");
        // TreeSet
        averageTime = (double) timesTreeSet / 2000000;
        System.out.println("TreeSet 2000000 deletes total time: " + timesTreeSet + " ns.");
        System.out.println("TreeSet average time per delete: " + averageTime + " ns.");
        // HashSet
        averageTime = (double) timesHashSet / 2000000;
        System.out.println("HashSet 2000000 deletes total time: " + timesHashSet + " ns.");
        System.out.println("HashSet average time per delete: " + averageTime + " ns.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        timesHashSet = 0;
        System.out.println("TESTING FIND ===========================");
        for (int i = 0; i < 5000000; ++i) {
            int findIndex = random.nextInt(helpStructure.size());
            MyInteger findData = new MyInteger(helpStructure.get(findIndex));
            // BST
            start = System.nanoTime();
            MyInteger bstFind = bstree.find(findData);
            end = System.nanoTime();
            timesBST += (end - start);
            if (bstFind == null) {
                System.out.println("Null find in BST! i: " + i + ". Find data: " + helpStructure.get(findIndex));
            }
            // AVL
            start = System.nanoTime();
            MyInteger avlFind = avltree.find(findData);
            end = System.nanoTime();
            timesAVL += (end - start);
            if (avlFind == null) {
                System.out.println("Null find in AVL! i: " + i + ". Find data: " + helpStructure.get(findIndex));
            }
            // TreeSet
            start = System.nanoTime();
            boolean tSetFind = tSet.contains(helpStructure.get(findIndex));
            end = System.nanoTime();
            if (!tSetFind) {
                System.out.println("Not find in TreeSet! i: " + i + ". Find data: " + helpStructure.get(findIndex));
            }
            timesTreeSet += (end - start);
            // HashSet
            start = System.nanoTime();
            boolean hSetFind = hSet.contains(helpStructure.get(findIndex));
            end = System.nanoTime();
            if (!hSetFind) {
                System.out.println("Not find in HashSet! i: " + i + ". Find data: " + helpStructure.get(findIndex));
            }
            timesHashSet += (end - start);
        }
        // BST
        averageTime = (double) timesBST / 5000000;
        System.out.println("BST 5000000 finds total time: " + timesBST + " ns.");
        System.out.println("BST average time per find: " + averageTime + " ns.");
        // AVL
        averageTime = (double) timesAVL / 5000000;
        System.out.println("AVL 5000000 finds total time: " + timesAVL + " ns.");
        System.out.println("AVL average time per find: " + averageTime + " ns.");
        // TreeSet
        averageTime = (double) timesTreeSet / 5000000;
        System.out.println("TreeSet 5000000 finds total time: " + timesTreeSet + " ns.");
        System.out.println("TreeSet average time per find: " + averageTime + " ns.");
        // HashSet
        averageTime = (double) timesHashSet / 5000000;
        System.out.println("HashSet 5000000 finds total time: " + timesHashSet + " ns.");
        System.out.println("HashSet average time per find: " + averageTime + " ns.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        System.out.println("TESTING INTERVAL FIND ===========================");
        Collections.sort(helpStructure);
        for (int i = 0; i < 1000000; ++i) {
            int bstCount = helpStructure.size();
            if (bstCount >= 1000) {
                minIndex = random.nextInt(bstCount - 500);
                maxIndex = minIndex + 500;
                //                            maxIndex = minIndex + random.nextInt(bstCount - minIndex - 500);
            } else {
                minIndex = random.nextInt(bstCount);
                maxIndex = minIndex + random.nextInt(bstCount - minIndex);
            }
            MyInteger from = new MyInteger(helpStructure.get(minIndex));
            MyInteger to = new MyInteger(helpStructure.get(maxIndex));
            start = System.nanoTime();
            ArrayList<MyInteger> bstInterval = bstree.findInterval(from, to);
            end = System.nanoTime();
            timesBST += (end - start);

            if ((maxIndex - minIndex + 1) != bstInterval.size()) {
                System.out.println("Index " + i + " The BST interval find did not even get the same number of results.");
            }
            for (int x = minIndex; x <= maxIndex; ++x) {
                if (helpStructure.get(x) != bstInterval.get(x - minIndex).getI()) {
                    System.out.print("Index " + i + ": INTERVAL FIND data in BST: " + " was unsuccessfull! :(");
                    System.out.println(helpStructure.get(x) + " != " + bstInterval.get(x - minIndex).getI());
                }
            }
            // AVL
            start = System.nanoTime();
            ArrayList<MyInteger> avlInterval = avltree.findInterval(from, to);
            end = System.nanoTime();
            timesAVL += (end - start);

            if ((maxIndex - minIndex + 1) != avlInterval.size()) {
                System.out.println("Index " + i + " The AVL interval find did not even get the same number of results.");
            }
            for (int x = minIndex; x <= maxIndex; ++x) {
                if (helpStructure.get(x) != avlInterval.get(x - minIndex).getI()) {
                    System.out.print("Index " + i + ": INTERVAL FIND data in AVL: " + " was unsuccessfull! :(");
                    System.out.println(helpStructure.get(x) + " != " + avlInterval.get(x - minIndex).getI());
                }
            }
            // TreeSet
            start = System.nanoTime();
            SortedSet<Integer> tSetInterval = tSet.subSet(helpStructure.get(minIndex), true, helpStructure.get(maxIndex), true);
            end = System.nanoTime();
            timesTreeSet += (end - start);
            if ((maxIndex - minIndex + 1) != tSetInterval.size()) {
                System.out.println("Index " + i + " The TreeSet interval find did not even get the same number of results.");
            }
            List<Integer> listTSetInterval = new ArrayList<>(tSetInterval);
            for (int x = minIndex; x <= maxIndex; ++x) {
                if (!Objects.equals(helpStructure.get(x), listTSetInterval.get(x - minIndex))) {
                    System.out.print("Index " + i + ": INTERVAL FIND data in TreeSet: " + " was unsuccessfull! :(");
                    System.out.println(helpStructure.get(x) + " != " + listTSetInterval.get(x - minIndex));
                }
            }
        }
        // BST
        averageTime = (double) timesBST / 1000000;
        System.out.println("BST 1000000 interval finds total time: " + timesBST + " ns.");
        System.out.println("BST average time per interval find: " + averageTime + " ns.");
        // AVL
        averageTime = (double) timesAVL / 1000000;
        System.out.println("AVL 1000000 interval finds total time: " + timesAVL + " ns.");
        System.out.println("AVL average time per interval find: " + averageTime + " ns.");
        // TreeSet
        averageTime = (double) timesTreeSet / 1000000;
        System.out.println("TreeSet 1000000 interval finds total time: " + timesTreeSet + " ns.");
        System.out.println("TreeSet average time per interval find: " + averageTime + " ns.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        int min = helpStructure.get(0);
        System.out.println("TESTING MINIMUM ===========================");
        for (int i = 0; i < 2000000; ++i) {
            start = System.nanoTime();
            int minBST = bstree.findMinimum().getI();
            end = System.nanoTime();
            timesBST += (end - start);
            start = System.nanoTime();
            int minAVL = avltree.findMinimum().getI();
            end = System.nanoTime();
            timesAVL += (end - start);
            start = System.nanoTime();
            int minTSet = tSet.first();
            end = System.nanoTime();
            timesTreeSet += (end - start);
            if (minBST != min || minAVL != min || minTSet != min) {
                System.out.println("Incorrect minimum: " + min + " find in one of the structures. i = " + i);
            }
        }
        // BST
        averageTime = (double) timesBST / 2000000;
        System.out.println("BST 2000000 minimum finds total time: " + timesBST + " ns.");
        System.out.println("BST average time per minimum finds: " + averageTime + " ns.");
        // AVL
        averageTime = (double) timesAVL / 2000000;
        System.out.println("AVL 2000000 minimum finds total time: " + timesAVL + " ns.");
        System.out.println("AVL average time per minimum find: " + averageTime + " ns.");
        // TreeSet
        averageTime = (double) timesTreeSet / 2000000;
        System.out.println("TreeSet 2000000 minimum finds total time: " + timesTreeSet + " ns.");
        System.out.println("TreeSet average time per minimum find: " + averageTime + " ns.");

        timesBST = 0;
        timesAVL = 0;
        timesTreeSet = 0;
        int max = helpStructure.get(helpStructure.size() -1);
        System.out.println("TESTING MAXIMUM ===========================");
        for (int i = 0; i < 2000000; ++i) {
            start = System.nanoTime();
            int maxBST = bstree.findMaximum().getI();
            end = System.nanoTime();
            timesBST += (end - start);
            start = System.nanoTime();
            int maxAVL = avltree.findMaximum().getI();
            end = System.nanoTime();
            timesAVL += (end - start);
            start = System.nanoTime();
            int maxTSet = tSet.last();
            end = System.nanoTime();
            timesTreeSet += (end - start);
            if (maxBST != max || maxAVL != max || maxTSet != max) {
                System.out.println("Incorrect maximum: " + max + " find in one of the structures. i = " + i);
            }
        }
        // BST
        averageTime = (double) timesBST / 2000000;
        System.out.println("BST 2000000 maximum finds total time: " + timesBST + " ns.");
        System.out.println("BST average time per maximum finds: " + averageTime + " ns.");
        // AVL
        averageTime = (double) timesAVL / 2000000;
        System.out.println("AVL 2000000 maximum finds total time: " + timesAVL + " ns.");
        System.out.println("AVL average time per maximum find: " + averageTime + " ns.");
        // TreeSet
        averageTime = (double) timesTreeSet / 2000000;
        System.out.println("TreeSet 2000000 maximum finds total time: " + timesTreeSet + " ns.");
        System.out.println("TreeSet average time per maximum find: " + averageTime + " ns.");
        System.out.println("TESTING INORDER ===========================");
        //BST
        ArrayList<MyInteger> bstInorder = bstree.inOrder();
        if (bstInorder.size() != helpStructure.size()) {
            System.out.println("BST INORDER does not have the same size as help structure.");
            System.out.println(bstInorder.size() + " != " + helpStructure.size());
        }
        for (int x = 0; x < helpStructure.size(); ++x) {
            if (helpStructure.get(x) != bstInorder.get(x).getI()) {
                System.out.print("Index " + x + ": in inorder in BST was unsuccessfull! :(");
                System.out.println(helpStructure.get(x) + " != " + bstInorder.get(x).getI());
            }
        }
        //AVL
        ArrayList<MyInteger> avlInorder = avltree.inOrder();
        if (avlInorder.size() != helpStructure.size()) {
            System.out.println("AVL INORDER does not have the same size as help structure.");
            System.out.println(avlInorder.size() + " != " + helpStructure.size());
        }
        for (int x = 0; x < helpStructure.size(); ++x) {
            if (helpStructure.get(x) != avlInorder.get(x).getI()) {
                System.out.print("Index " + x + ": in inorder in AVL was unsuccessfull! :(");
                System.out.println(helpStructure.get(x) + " != " + avlInorder.get(x).getI());
            }
        }
        if (avltree.isNotAVL()) {// Kontrola vlastnosti AVL
            System.out.println("AVL is not really AVL, some node has difference of heights of subtrees more that 1.");
        } else {
            System.out.println("AVL is AVL, all the nodes have |BF| <= 1.");
        }

        System.out.println("======================================================");
        System.out.println("Size should be 8000000 in structure: " + helpStructure.size());
        System.out.println("Number of nodes in BST from level order: " + bstree.levelOrder().size());
        System.out.println("Number of nodes in AVL from level order: " + avltree.levelOrder().size());

        System.out.println("SUCCESS - IF NONE INCORRECT MESSAGE IS SHOWN :)");
    }
}
