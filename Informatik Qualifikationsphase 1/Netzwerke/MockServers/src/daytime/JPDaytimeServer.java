package daytime;

import mainwindow.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JPDaytimeServer extends JPanel {
    private JButton bStartStop;
    private JTextArea taLog;
    private JPanel pMain;
    private JLabel lbPort;
    private TimeServer timeServer;

    public JPDaytimeServer() {
        super();
        lbPort.setText("" + Main.PORT_TIME);
        add(pMain);
        timeServer = new TimeServer(taLog);
        bStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (timeServer.isRunning()) {
                    timeServer.stop();
                    bStartStop.setText("Start");
                } else {
                    timeServer.start();
                    bStartStop.setText("Stop");
                }
            }
        });
    }
}
