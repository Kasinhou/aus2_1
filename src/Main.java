import structure.AVLNode;
import structure.AVLTree;
import structure.BSTNode;
import structure.BinarySearchTree;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BinarySearchTree<Book> books = new BinarySearchTree<>();
        AVLTree<Book> avlbooks = new AVLTree<>();
//        int[] listOfInts = new int[]{20,10,30,25,40,27};
        int[] listOfInts = new int[]{97,70,80,91,55,51,6};
//        int[] listOfInts = new int[]{40,10,50,5,30,65,20,55,70,15,25,11};
        for (int i : listOfInts) {
            System.out.println("VKLADAM " + i);
//            books.insert(new Book(i));
            avlbooks.insert(new Book(i));
            ArrayList<BSTNode<Book>> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
            for (BSTNode<Book> b : levelBooks) {
                System.out.print(b.getData().getId() + " ");
            }
            System.out.println();

        }
        System.out.println(avlbooks.delete(new Book(55)));
//        System.out.println("BOOKS FIND NULL" + books.find(new Book(45)));
//        System.out.println(books.find(books.getRoot().getLeftSon().getData()));
        System.out.print("size: " + books.getSize() + " avl: " + avlbooks.getSize());
//        System.out.println();
//        ArrayList<BSTNode<Book>> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
//        for (BSTNode<Book> i : levelBooks) {
//            System.out.print(i.getData().getId() + " ");
//        }
//        System.out.println();
//        ArrayList<Book> interval = avlbooks.findInterval(avlbooks.getRoot().getData(), avlbooks.getRoot().getRightSon().getRightSon().getRightSon().getData());
//        ArrayList<Book> interval = avlbooks.findInterval(new Book(53), new Book(56));
//        System.out.println("INTERVAL od do");
//        for (Book b : interval) {
//            System.out.print(" " + b.getId());
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
//        avlbooks.rightRotation((structure.AVLNode<Book>) avlbooks.getRoot());
//        books.delete(books.getRoot().getData());
//        books.insert(new Book(87));
//        System.out.println(books.findMinimum().getId());
//        System.out.println(books.findMaximum().getId());
//        ArrayList<Book> levelBooksA = avlbooks.levelOrder(avlbooks.getRoot());
//        for (Book i : levelBooksA) {
//            System.out.print(i.getId() + " ");
//        }
//        System.out.println();
//        System.out.println(((structure.AVLNode<Book>) avlbooks.getRoot()).getBalanceFactor());

    }
}