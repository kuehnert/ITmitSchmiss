package main;

import gui.SortWindow;

import javax.swing.*;

public class MainGUI implements Runnable {
    @Override
    public void run() {
        JFrame window = new SortWindow();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new MainGUI());
    }
}
