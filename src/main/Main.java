package main;

import gui.Controller;
import gui.View;

import javax.swing.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WHOSystem who = new WHOSystem();
            View view = new View();
            Controller controller = new Controller(who, view);

//            Generator generator = new Generator(who);
            view.setVisible(true);
        });
        WHOSystem who = new WHOSystem();
        who.addPerson("M", "K", LocalDate.of(2002, 7, 30), "2");
        who.addPerson("E", "H", LocalDate.of(2001, 7, 28), "3");
        who.addPerson("R", "H", LocalDate.of(2000, 7, 27), "1");
        who.addPerson("T", "O", LocalDate.of(2022, 7, 29), "4");
        who.addPerson("W", "T", LocalDate.of(2010, 7, 31), "5");
        who.addTestResult(LocalDate.of(2022, 8, 7), "2", 40, 1, 2, 2, false, 0.0, "This is first one");
        who.addTestResult(LocalDate.of(2022, 8, 7), "1", 8, 1, 2, 2, true, 1.0, "fkuffufyuj");
        who.addTestResult(LocalDate.of(2022, 8, 7), "3", 2, 2, 2, 2, true, 4.0, "dhshgghsghsgsg sgtrh rth rth ");
        who.addTestResult(LocalDate.of(2022, 8, 6), "4", 85, 2, 2, 2, true, 0.5, "");
//        System.out.println(who.showTestResult(40, "2"));
//        System.out.println(who.showAllPersonTests("2"));
        System.out.println("===============================================================================");
//        Generator generator = new Generator(who);
//        who.removeTest(85);
//        System.out.println("===============================================================================");
//        System.out.println(who.showTestResult(85, "2"));
//        System.out.println("===============================================================================");
//        System.out.println(who.showAllTestsInWorkspace(LocalDate.of(2022, 6, 5), LocalDate.of(2022, 10, 7), 2));
        System.out.println(who.showPositivePeopleInDistrict(2, LocalDate.of(2022, 8, 9), 3));
//        System.out.println(who.showPositiveTestsInDistrict(1, LocalDate.of(2022, 8, 5), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.showPositiveTestsInDistrict(1, LocalDate.of(2022, 8, 6), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.showAllTestsInDistrict(2, LocalDate.of(2022, 8, 7), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.showPositiveTestsInRegion(2, LocalDate.of(2022, 8, 7), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.showAllTestsInRegion(2, LocalDate.of(2022, 8, 6), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.showPositiveTests(LocalDate.of(2022, 8, 6), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.showAllTests(LocalDate.of(2022, 8, 7), LocalDate.of(2022, 8, 7)));
//        System.out.println(who.getPeople().levelOrder(who.getPeople().getRoot()));
//        who.removePersonAndTests("2");
//        System.out.println(who.getPeople().levelOrder(who.getPeople().getRoot()));
//        BinarySearchTree<MyInteger> books = new BinarySearchTree<>();
//        AVLTree<MyInteger> avlbooks = new AVLTree<>();
//        avlbooks.insert(null);
////        int[] listOfInts = new int[]{20,10,30,25,40,27};
//        int[] listOfInts = new int[]{97,70,80,91,55,51,6};
////        int[] listOfInts = new int[]{40,10,50,5,30,65,20,55,70,15,25,11};
//        for (int i : listOfInts) {
//            System.out.println("VKLADAM " + i);
////            books.insert(new MyInteger(i));
//            avlbooks.insert(new MyInteger(i));
//            ArrayList<BSTNode<MyInteger>> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
//            for (BSTNode<MyInteger> b : levelBooks) {
//                System.out.print(b.getData().getI() + " ");
//            }
//            ArrayList<MyInteger> levelBooks = avlbooks.levelOrder(avlbooks.getRoot());
//            for (MyInteger b : levelBooks) {
//                System.out.print(b.getI() + " ");
//            }
//            System.out.println();

//        }
//        System.out.println(avlbooks.delete(new MyInteger(55)));
//        System.out.println("BOOKS FIND NULL" + books.find(new MyInteger(45)));
//        System.out.println(books.find(books.getRoot().getLeftSon().getData()));
//        System.out.print("size: " + books.getSize() + " avl: " + avlbooks.getSize());
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