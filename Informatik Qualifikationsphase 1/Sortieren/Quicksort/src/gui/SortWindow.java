package gui;

import main.Benchmark;
import main.DemoSorter;
import main.StressTest;
import exporters.Exporter;
import exporters.HTMLExporter;
import sorters.Sorter;
import utils.ArrayMaker;
import utils.DemoArray;
import utils.FileReadWrite;
import utils.Vars;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SortWindow extends JFrame {
    private final DemoSorter demoSorter;
    private JPanel pMain;
    private JEditorPane epSource;
    private JComboBox cbSorter;
    private JButton bStressTest;
    private JButton bBenchmark;
    private JButton bDemo;
    private LoggerBox taLog;
    private JEditorPane epDemo;
    private JComboBox cbArrays;
    private JTextField tfSelectedArray;
    private JButton bSave;
    private JSplitPane spHorizontal;
    private JSplitPane spVertical;
    private Sorter selectedSorter;

    public SortWindow() throws HeadlessException {
        super();
        setTitle(Vars.TITLE);
        add(pMain);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // Fehler macht nix
        }

        // Load font from resources
        InputStream is = SortWindow.class.getResourceAsStream("/CascadiaCode.ttf");
        try {
            Font codeFont = Font.createFont(Font.TRUETYPE_FONT, is);
            codeFont = codeFont.deriveFont(16.0f);
            epSource.setFont(codeFont);
            epDemo.setFont(codeFont);
            taLog.setFont(codeFont);
        } catch (IOException | FontFormatException e) {
            System.out.println("Unable to load font. Whatever.");
        }

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        size = new Dimension(size.width * 3 / 5, size.height * 3 / 5);
        setSize(size);
        spVertical.setDividerLocation(size.height * 2 / 3);
        spHorizontal.setDividerLocation(size.width / 3 - 100);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        taLog.println(Vars.WELCOME);

        ArrayMaker arrayMaker = new ArrayMaker();
        cbArrays.setModel(new DefaultComboBoxModel(arrayMaker.getArrays()));

        demoSorter = new DemoSorter(taLog);
        cbSorter.setModel(new DefaultComboBoxModel(demoSorter.getSorters().toArray()));

        // Set sample arrays in combo box and select the first one
        cbArrays.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = cbArrays.getSelectedItem().toString();
                tfSelectedArray.setText(s);
                bSave.setEnabled(false);
            }
        });

        cbArrays.setSelectedIndex(0);

        // Put sorting methods in combo box and select the first one
        cbSorter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedSorter = (Sorter) cbSorter.getSelectedItem();
                String sorterCode = selectedSorter.getCode();
                epSource.setText(sorterCode);
                epSource.setCaretPosition(0);
                bSave.setEnabled(false);
            }
        });
        cbSorter.setSelectedIndex(0);

        // Demo Sort
        bDemo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.clear();
                taLog.println("Demoing " + selectedSorter);
                selectedSorter.setExporter(new HTMLExporter(selectedSorter));
                String s = tfSelectedArray.getText();
                int[] arrayToSort = ArrayMaker.fromString(s);
                selectedSorter.setArray(arrayToSort);
                s = DemoArray.toString(arrayToSort);
                tfSelectedArray.setText(s);

                String result = selectedSorter.debug();
                taLog.println(result);

                String out = selectedSorter.getExporter().getLog();
                epDemo.setText(out);
                epDemo.setCaretPosition(0);

                bSave.setEnabled(true);
            }
        });

        // Stress-Test Action
        bStressTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.clear();
                bSave.setEnabled(false);
                StressTest.stressTest(selectedSorter, taLog);
            }
        });

        // Benchmark Action
        bBenchmark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.clear();
                bSave.setEnabled(false);
                Benchmark.runBenchmarks(demoSorter.getSorters(), taLog);
            }
        });

        // Save benchmark results in html file and open it in browser
        bSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exporter exporter = selectedSorter.getExporter();
                if (exporter == null) {
                    return;
                }

                String home = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
                String filename = home + "/QuicksortOutput.html";
                FileReadWrite.save(filename, exporter.getLog());
                taLog.printf("Log saved in %s. Trying to open file in browser\n", filename);
                try {
                    Desktop.getDesktop().open(new File(filename));
                } catch (IOException ignored) {
                }
            }
        });

        tfSelectedArray.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                bSave.setEnabled(false);
            }
        });
    }
}
