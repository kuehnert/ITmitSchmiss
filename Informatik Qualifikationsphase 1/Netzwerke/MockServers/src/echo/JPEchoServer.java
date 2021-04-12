package echo;

import daytime.TimeServer;
import mainwindow.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPEchoServer extends JPanel {
    private JButton bStartStop;
    private JTextArea taEchoLog;
    private JPanel pMain;
    private JLabel lbPort;
    private EchoServer echoServer;

    public JPEchoServer() {
        super();
        lbPort.setText("" + Main.PORT_ECHO);
        add(pMain);
        echoServer = new EchoServer(taEchoLog);
        bStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (echoServer.isRunning()) {
                    echoServer.stop();
                    bStartStop.setText("Start");
                } else {
                    echoServer.start();
                    bStartStop.setText("Stop");
                }
            }
        });
    }
}
