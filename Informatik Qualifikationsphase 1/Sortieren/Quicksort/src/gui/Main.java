package gui;

import javax.swing.*;

public class Main implements Runnable {
    @Override
    public void run() {
        JFrame window = new SortWindow();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }
}
