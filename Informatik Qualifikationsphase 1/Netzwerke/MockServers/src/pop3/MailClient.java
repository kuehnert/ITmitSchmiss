package pop3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

class MailClient extends Thread {
    public static final int STATE_CONNECTED = 0;
    public static final int STATE_USERNAME_GIVEN = 1;
    public static final int STATE_AUTHENTICATED = 2;
    private MailServer server;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private int state;
    private boolean running;
    private User user;

    public MailClient(MailServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.running = false;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            state = STATE_CONNECTED;
            server.getTALog().append("Client verbunden an " + socket.getInetAddress() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleStateConnected(String input) {
        Pattern p = Pattern.compile("^USER\\s+(?<name>.+)$");
        Matcher m = p.matcher(input);

        if (m.find()) {
            String username = m.group("name");
            Stream users = Arrays.stream(server.getUsers().toArray());
            User user = (User) users.filter(u -> ((User) u).getName().equals(username))
                    .reduce((a, b) -> {
                throw new IllegalStateException("Multiple elements: " + a + ", " + b);
            }).get();
            writer.println("+OK User accepted");
            return;
        }

        if (input.startsWith("QUIT")) {
            running = false;
            writer.println("+OK POP3 server signing off");
            return;
        }

        writer.println("The following commands are available: \n" + "USER <username>: authenticates the user\n" +
                "QUIT: ends the session");
    }

    private void handleStateUsernameGiven(String input) {
        Pattern p1 = Pattern.compile("^PASS\\s+(?<password>.+)$");
        Matcher m1 = p1.matcher(input);

        if (m1.find()) {
            String password = m1.group("password");
            // Stream<User> users = Stream<User> (server.getUsers());
            // User user = users.anyMatch(u -> u.getName().equals(username));
            // if (user == null) {
            //     state = STATE_CONNECTED;
            //     return;
            // }

            writer.println("+OK");
            return;
        }
    }

    private void handleStateAuthenticated(String input) {

    }

    public void stopThread() {
        running = false;
    }

    public void run() {
        running = true;
        writer.format("+OK POP3 server ready <%s>\n", server.LOCAL_DOMAIN);
        String input;

        try {
            while (running && (input = reader.readLine()) != null) {
                switch (state) {
                    case STATE_CONNECTED:
                        handleStateConnected(input);
                        break;
                    case STATE_USERNAME_GIVEN:
                        handleStateUsernameGiven(input);
                        break;
                    case STATE_AUTHENTICATED:
                        handleStateAuthenticated(input);
                        break;
                }
            }

            reader.close();
            writer.close();
            socket.close();
            server.getTALog().append("Clientverbindung beendet an " + socket.getInetAddress() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}