/*
 * Source: https://dbs.cs.uni-duesseldorf.de/lehre/docs/java/javabuch/examples/EchoServer.java
 */
package originals;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        int cnt = 0;
        try {
            System.out.println("Warte auf Verbindungen auf Port 7...");
            ServerSocket echod = new ServerSocket(7);
            while (true) {
                Socket socket = echod.accept();
                (new EchoClientThread(++cnt, socket)).start();
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            System.exit(1);
        }
    }
}

class EchoClientThread
        extends Thread {
    private int name;
    private Socket socket;

    public EchoClientThread(int name, Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    public void run() {
        String msg = "echo.EchoServer: Verbindung " + name;
        System.out.println(msg + " hergestellt");
        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            out.write((msg + "\r\n").getBytes());
            int c;
            while ((c = in.read()) != -1) {
                out.write((char) c);
                System.out.print((char) c);
            }
            System.out.println("Verbindung " + name + " wird beendet");
            socket.close();
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}