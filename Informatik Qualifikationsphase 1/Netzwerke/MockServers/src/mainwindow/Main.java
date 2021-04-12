package mainwindow;

import pop3.JPPOP3Server;

import javax.swing.*;

public class Main extends JFrame {
    public static final int PORT_TIME = 10013;
    public static final int PORT_ECHO = 10007;
    public static final int PORT_POP3 = 10110;
    public static final int PORT_SMTP = 10025;

    private static final String VERSION = "1.0.2";
    private JPanel pMain;
    private JPPOP3Server JPPOP3Server;
    private JTabbedPane tpTabs;

    public Main() {
        super("Network Mock-Server (" + VERSION + ")");
        // tpTabs.setSelectedIndex(2);
        add(pMain);
        pack();
        setSize(640, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
