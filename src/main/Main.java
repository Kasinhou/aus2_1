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
//        WHOSystem who = new WHOSystem();
//        who.addPerson("M", "K", LocalDate.of(2002, 7, 30), "2");
//        who.addPerson("E", "H", LocalDate.of(2001, 7, 28), "3");
//        who.addPerson("R", "H", LocalDate.of(2000, 7, 27), "1");
//        who.addPerson("T", "O", LocalDate.of(2022, 7, 29), "4");
//        who.addPerson("W", "T", LocalDate.of(2010, 7, 31), "5");
//        who.addTestResult(LocalDate.of(2022, 8, 7), "2", 40, 1, 2, 2, false, 0.0, "This is first one");
//        who.addTestResult(LocalDate.of(2022, 8, 7), "1", 8, 1, 2, 2, true, 1.0, "fkuffufyuj");
//        who.addTestResult(LocalDate.of(2022, 8, 7), "3", 2, 2, 2, 2, true, 4.0, "dhshgghsghsgsg sgtrh rth rth ");
//        who.addTestResult(LocalDate.of(2022, 8, 6), "4", 85, 2, 2, 2, true, 0.5, "");
//        System.out.println(who.showTestResult(40, "2"));
//        System.out.println(who.showAllPersonTests("2"));
        System.out.println("===============================================================================");
//        Generator generator = new Generator(who);
//        who.removeTest(85);
//        System.out.println("===============================================================================");
//        System.out.println(who.showTestResult(85, "2"));
//        System.out.println("===============================================================================");
//        System.out.println(who.showAllTestsInWorkspace(LocalDate.of(2022, 6, 5), LocalDate.of(2022, 10, 7), 2));
//        System.out.println(who.showPositivePeopleInDistrict(2, LocalDate.of(2022, 8, 9), 3));
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
    }
}