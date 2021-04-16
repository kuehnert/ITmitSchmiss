import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class StringGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final String VERSION = "1.0.1";
    private static final Class<?>[] PARAMS = {String.class};
    private JLabel lbInput;
    private JLabel lbOutput;
    private JLabel lbCommands;
    private JTextArea taInput;
    private JTextArea taOutput;
    private JPanel pInputOutput;
    private JPanel pCommands;
    private JButton bCopy;
    private JScrollPane spCommands;
    private JScrollPane spInput;
    private JScrollPane spOutput;
    private JList<String> lCommands;

    public StringGUI() {
        super(String.format("IT mit Schmiss! - StringGUI (%s)", VERSION));
        // Beende Programm beim Schliessen des Fensters
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Setze Fenstergroesse auf 800x600 Pixel
        setPreferredSize(new Dimension(800, 600));

        // Versuche den nativen Look & Feel des Betriebssystems zu nutzen
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.out.println("Konnte den Look & Feel des Betriebssystems nicht aktivieren.");
        }

        // Setze das Layout des Fensters, dass die beiden Panels nebeneinander stehen
        setLayout(new BoxLayout(getContentPane(), BoxLayout.LINE_AXIS));

        // Input-Output Panel
        pInputOutput = new JPanel();
        pInputOutput.setLayout(new BoxLayout(pInputOutput, BoxLayout.PAGE_AXIS));
        pInputOutput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pInputOutput.setMaximumSize(new Dimension(500, 1000));

        lbInput = new JLabel("Eingabe:");
        lbInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        pInputOutput.add(lbInput);

        taInput = new JTextArea("The quick, brown fox jumps over the lazy dog.");
        taInput.setBackground(Color.BLACK);
        taInput.setForeground(Color.GREEN);
        taInput.setCaretColor(Color.WHITE);
        taInput.setLineWrap(true);
        taInput.setWrapStyleWord(true);
        Font taFont = taInput.getFont().deriveFont(14f); // will only change size to 12pt
        taInput.setFont(taFont);
        spInput = new JScrollPane(taInput);
        spInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        pInputOutput.add(spInput);

        lbOutput = new JLabel("Ausgabe:");
        lbOutput.setAlignmentX(Component.LEFT_ALIGNMENT);
        pInputOutput.add(lbOutput);

        taOutput = new JTextArea();
        taOutput.setBackground(Color.BLACK);
        taOutput.setForeground(Color.GREEN);
        taOutput.setCaretColor(Color.WHITE);
        taOutput.setLineWrap(true);
        taOutput.setWrapStyleWord(true);
        taOutput.setFont(taFont);
        taOutput.setAlignmentX(Component.LEFT_ALIGNMENT);
        taOutput.setEnabled(false);
        taOutput.setEditable(false);
        spOutput = new JScrollPane(taOutput);
        spOutput.setAlignmentX(Component.LEFT_ALIGNMENT);
        pInputOutput.add(spOutput);

        // Command Panel
        pCommands = new JPanel();
        pCommands.setLayout(new BoxLayout(pCommands, BoxLayout.PAGE_AXIS));
        pCommands.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));
        pCommands.setMaximumSize(new Dimension(500, 1000));

        lbCommands = new JLabel("Befehle:");
        lbCommands.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCommands.add(lbCommands);

        lCommands = new JList<>(populateList());
        lCommands.setFont(taFont);
        spCommands = new JScrollPane(lCommands);
        spCommands.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCommands.add(spCommands);

        // HTML wegen Encoding von 'a'-umlaut
        bCopy = new JButton("<html>Ausf&uuml;hren!</html>");
        bCopy.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCommands.add(bCopy);

        add(pInputOutput);
        add(pCommands);
        pack();
        setLocationRelativeTo(null);

        // Konfiguriere den Button
        bCopy.addActionListener(event -> convertText());
    }

    /**
     * Durchsuche die Klasse "StringBefehle" nach Methoden, die einen String bekommen und einen
     * String zurueckgeben
     *
     * @return String-Array mit den Namen der Methoden
     */
    private String[] populateList() {
        Method[] rMethods = StringBefehle.class.getDeclaredMethods();
        String[] methods = new String[rMethods.length];

        for (int i = 0; i < methods.length; i++) {
            Method m = rMethods[i];
            Class<?>[] params = m.getParameterTypes();
            Class<?> returnType = m.getReturnType();

            if (returnType.equals(String.class) && Arrays.equals(params, PARAMS)) {
                methods[i] = m.getName();
            }
        }

        Arrays.sort(methods);
        return methods;

    }

    private void convertText() {
        String command = lCommands.getSelectedValue();
        if (command == null) {
            JOptionPane.showMessageDialog(this,
                    "<html>Du musst erst eine Funktion ausw&auml;hlen,<br>bevor Du umwandeln kannst.</html>",
                    "Achtung", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String in = taInput.getText();
        String out;

        try {
            Method m = StringBefehle.class.getMethod(command, String.class);
            out = (String) m.invoke(null, in);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            out = String.format("FEHLER beim Methodenaufruf von '%s'", command);
        }

        taOutput.setText(out);
        taOutput.setEnabled(true);
    }

    public static void runMe() {
        main(null);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new StringGUI().setVisible(true);
        });
    }
}
