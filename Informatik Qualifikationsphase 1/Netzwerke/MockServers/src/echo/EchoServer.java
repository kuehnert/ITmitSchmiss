package echo;

import mainwindow.Main;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class EchoServer {
    private JTextArea taLogger;
    private EchoServerThread thread;

    public EchoServer(JTextArea taLogger) {
        this.taLogger = taLogger;
    }

    public boolean isRunning() {
        return thread != null && thread.isRunning();
    }

    public void start() {
        thread = new EchoServerThread(taLogger);
        thread.start();
    }

    public void stop() {
        thread.quit();
        taLogger.append("Echo-Server gestoppt.\n");
    }
}

class EchoServerThread extends Thread {
    private JTextArea taLogger;
    private ServerSocket serverSocket;
    private boolean isRunning;
    private ArrayList<EchoClientThread> clients = new ArrayList<EchoClientThread>();

    public EchoServerThread(JTextArea taLogger) {
        this.taLogger = taLogger;
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(Main.PORT_ECHO);
            taLogger.append("Echo-Server gestartet.\n" +
                    "Verbinde Dich mit Telnet an " + serverSocket.getInetAddress() +
                    " und auf Port " + Main.PORT_ECHO + ".\n");
            isRunning = true;

            while (isRunning) {
                try {
                    Socket client = serverSocket.accept();
                    taLogger.append("Neuer Client verbunden: " + client);
                    EchoClientThread clientThread = new EchoClientThread(this, client);
                    clients.add(clientThread);
                    clientThread.start();
                } catch (SocketException e) {
                    System.err.println("FEHLER");
                    isRunning = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            isRunning = false;
            for (EchoClientThread clientThread : clients) {
                clientThread.kill();
            }
        }

    }

    public JTextArea getLogger() {
        return taLogger;
    }

    public void quit() {
        try {
            for (EchoClientThread client : clients) {
                client.kill();
            }
            serverSocket.close();
        } catch (IOException e) {
            taLogger.append("Fehler beim Schließen vom Echo-Server!\n");
            e.printStackTrace();
        }
        isRunning = false;
    }
}

class EchoClientThread extends Thread {
    private Socket socket;
    private EchoServerThread server;
    private JTextArea taLogger;
    private BufferedReader in;
    private PrintWriter out;
    private boolean isRunning;

    public EchoClientThread(EchoServerThread server, Socket socket) {
        this.server = server;
        this.socket = socket;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (java.io.IOException e) {
            System.err.println("FEHLER: ");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        isRunning = true;

        while (isRunning) {
            try {
                String incoming = in.readLine();
                System.out.println("<" + incoming);
                out.println(incoming);
            } catch (IOException e) {
                System.err.println("FEHLER: ");
                e.printStackTrace();
                isRunning = false;
            }
        }

        taLogger.append("Schließe Client " + socket);
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void kill() {
        isRunning = false;
    }
}
