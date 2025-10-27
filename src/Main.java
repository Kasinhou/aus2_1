import structure.AVLNode;
import structure.AVLTree;
import structure.BSTNode;
import structure.BinarySearchTree;
import test.MyInteger;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<MyInteger> books = new BinarySearchTree<>();
        AVLTree<MyInteger> avlbooks = new AVLTree<>(1);
        avlbooks.insert(null);
//        int[] listOfInts = new int[]{20,10,30,25,40,27};
        int[] listOfInts = new int[]{97,70,80,91,55,51,6};
//        int[] listOfInts = new int[]{40,10,50,5,30,65,20,55,70,15,25,11};
        for (int i : listOfInts) {
            System.out.println("VKLADAM " + i);
//            books.insert(new MyInteger(i));
            avlbooks.insert(new MyInteger(i));
            ArrayList<BSTNode<MyInteger>> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
            for (BSTNode<MyInteger> b : levelBooks) {
                System.out.print(b.getData().getI() + " ");
            }
//            ArrayList<MyInteger> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
//            for (MyInteger b : levelBooks) {
//                System.out.print(b.getI() + " ");
//            }
            System.out.println();

        }
        System.out.println(avlbooks.delete(new MyInteger(55)));
//        System.out.println("BOOKS FIND NULL" + books.find(new MyInteger(45)));
//        System.out.println(books.find(books.getRoot().getLeftSon().getData()));
        System.out.print("size: " + books.getSize() + " avl: " + avlbooks.getSize());
//        System.out.println();
//        ArrayList<BSTNode<MyInteger>> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
//        for (BSTNode<MyInteger> i : levelBooks) {
//            System.out.print(i.getData().getId() + " ");
//        }
//        System.out.println();
//        ArrayList<MyInteger> interval = avlbooks.findInterval(avlbooks.getRoot().getData(), avlbooks.getRoot().getRightSon().getRightSon().getRightSon().getData());
//        ArrayList<MyInteger> interval = avlbooks.findInterval(new MyInteger(53), new MyInteger(56));
//        System.out.println("INTERVAL od do");
//        for (MyInteger b : interval) {
//            System.out.print(" " + b.getI());
//        }
//        System.out.println("nasledovnik " + avlbooks.getRoot().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot()).getData().getId());
//        System.out.println("nasledovnik " + avlbooks.getRoot().getLeftSon().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot().getLeftSon()).getData().getId());
//        System.out.println("nasledovnik " + avlbooks.getRoot().getLeftSon().getLeftSon().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot().getLeftSon().getLeftSon()).getData().getId());
//        System.out.println("nasledovnik " + avlbooks.getRoot().getRightSon().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot().getRightSon()).getData().getId());
//        System.out.println("nasledovnik " + avlbooks.getRoot().getRightSon().getLeftSon().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot().getRightSon().getLeftSon()).getData().getId());
//        if (avlbooks.nextInOrder(avlbooks.getRoot().getRightSon().getRightSon()) != null) {
//            System.out.println("nasledovnik " + avlbooks.getRoot().getRightSon().getRightSon().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot().getRightSon().getRightSon()).getData().getId());
//        } else {
//            System.out.println("nasledovnik " + avlbooks.getRoot().getRightSon().getRightSon().getData().getId() + " je " + avlbooks.nextInOrder(avlbooks.getRoot().getRightSon().getRightSon()) + " lebo je to posledny prvok.");
//        }
//        avlbooks.rightRotation((structure.AVLNode<MyInteger>) avlbooks.getRoot());
//        books.delete(books.getRoot().getData());
//        books.insert(new MyInteger(87));
//        System.out.println(books.findMinimum().getId());
//        System.out.println(books.findMaximum().getId());
//        ArrayList<MyInteger> levelBooksA = avlbooks.levelOrder(avlbooks.getRoot());
//        for (MyInteger i : levelBooksA) {
//            System.out.print(i.getI() + " ");
//        }
//        System.out.println();
//        System.out.println(((structure.AVLNode<MyInteger>) avlbooks.getRoot()).getBalanceFactor());

    }
}