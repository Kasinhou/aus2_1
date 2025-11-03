package main;

import gui.Controller;
import gui.View;

import javax.swing.*;

/**
 * Main invokes GUI where user can use the system.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WHOSystem who = new WHOSystem();
            View view = new View();
            Controller controller = new Controller(who, view);
            view.setVisible(true);
        });
    }
}