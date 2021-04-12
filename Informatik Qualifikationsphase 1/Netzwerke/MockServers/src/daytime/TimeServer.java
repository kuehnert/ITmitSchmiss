package daytime;

import mainwindow.Main;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class TimeServer {
    private JTextArea taLogger;
    private TimeServerThread thread;

    public TimeServer(JTextArea taLogger) {
        this.taLogger = taLogger;
    }

    public boolean isRunning() {
        return thread != null && thread.isRunning();
    }

    public void start() {
        thread = new TimeServerThread(taLogger);
        thread.start();
    }

    public void stop() {
        thread.quit();
        taLogger.append("Time Server gestoppt.\n");
    }
}

class TimeServerThread extends Thread {
    private JTextArea taLogger;
    private ServerSocket serverSocket;
    private boolean isRunning;

    public TimeServerThread(JTextArea taLogger) {
        this.taLogger = taLogger;
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(10013);
            taLogger.append("Time Server gestartet.\n" +
                    "Verbinde Dich mit Telnet an " + serverSocket.getInetAddress() +
                    " und auf Port " + Main.PORT_TIME +
                    "\nDu kannst die Ausgabe vergleichen mit z.B. time-a-g.nist.gov auf Port 13.\n");
            isRunning = true;

            while (isRunning) {
                try (Socket client = serverSocket.accept()) {
                    OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
                    String ausgabe = now.toString() + " (UTC)";
                    taLogger.append("Client verbunden, sende: " + ausgabe + "\n");
                    PrintWriter out = new PrintWriter(client.getOutputStream());
                    out.println(ausgabe);
                    out.flush();
                    out.close();
                } catch (SocketException e) {
                    System.err.println("FEHLER");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
        }

    }

    public void quit() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            taLogger.append("Fehler beim Schließen von daytime.TimeServer!\n");
            e.printStackTrace();
        }
        isRunning = false;
    }
}