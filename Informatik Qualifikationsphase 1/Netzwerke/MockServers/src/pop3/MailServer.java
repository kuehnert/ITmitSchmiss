package pop3;

import mainwindow.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MailServer extends Thread {
    public static final String LOCAL_DOMAIN = "meinewelt.de";
    private DefaultListModel<User> users;
    private JTextArea taLog;
    private ServerSocket serverSocket;
    private boolean running;

    public MailServer(JTextArea taLog) {
        this.taLog = taLog;
        users = new DefaultListModel<>();
        loadStaticData();
    }

    private static String localize(String prefix) {
        return prefix + "@" + LOCAL_DOMAIN;
    }

    public JTextArea getTALog() {
        return taLog;
    }

    public boolean isRunning() {
        return running;
    }

    public DefaultListModel<User> getUsers() {
        return users;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(Main.PORT_POP3);
            running = true;
            taLog.append("Mail-Server gestartet auf Port " + Main.PORT_POP3 + "\n");
            while (running) {
                Socket clientSocket = serverSocket.accept();
                MailClient client = new MailClient(this, clientSocket);
                client.start();
            }

        } catch (IOException e) {
            System.err.println("FEHLER im MailServer");
            e.printStackTrace();
        }
    }

    public void stopThread() {
        running = false;
    }

    private void loadStaticData() {
        User anna = new User("anna", "geheim");
        anna.getMails().add(0, new Mail("karl@heinz.de", localize("anna"), "Wichtig!", "Hallo Anna", new Date()));
        anna.getMails().add(1, new Mail("willi@beispiel.de", localize("anna"), "Ich liebe Dich!", "Hallo Anna,\nvon mir auch!\nViele Gruesse,\nWilli", new Date()));
        users.add(0, anna);

        User bob = new User("bob", "topSecret");
        bob.getMails().add(0, new Mail("sokrates", localize("bob"), "Spam!", "Hi!", new Date()));
        users.add(1, bob);
    }
}
