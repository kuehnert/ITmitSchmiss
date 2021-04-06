package gui;

import console.Benchmark;
import console.DemoSorter;
import console.StressTest;
import exporters.HTMLExporter;
import sorters.Sorter;
import utils.ArrayMaker;
import utils.DemoArray;
import utils.Vars;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortWindow extends JFrame {
    private final DemoSorter demoSorter;
    private final ArrayMaker arrayMaker = new ArrayMaker();
    private JPanel pMain;
    private JEditorPane epSource;
    private JComboBox cbSorter;
    private JButton bStressTest;
    private JButton bBenchmark;
    private JButton bDemo;
    private LoggerBox taLog;
    private JScrollPane spWebView;
    private JScrollPane spSource;
    private JEditorPane epDemo;
    private JScrollPane spDemo;
    private JComboBox cbArrays;
    private JTextField tfSelectedArray;
    private Sorter selectedSorter;

    public SortWindow() throws HeadlessException {
        super();
        setTitle(Vars.TITLE);
        add(pMain);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(size.width*4/5, size.height*4/5);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        taLog.println(Vars.WELCOME);

        cbArrays.setModel(new DefaultComboBoxModel(arrayMaker.getArrays()));

        demoSorter = new DemoSorter(taLog);
        cbSorter.setModel(new DefaultComboBoxModel(demoSorter.getSorters().toArray()));

        // Set sample arrays in combo box and select the first one
        cbArrays.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = cbArrays.getSelectedItem().toString();
                tfSelectedArray.setText(s);
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
            }
        });

        // Stress-Test Action
        bStressTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.clear();
                StressTest.stressTest(selectedSorter, taLog);
            }
        });

        // Benchnmark Action
        bBenchmark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taLog.clear();
                Benchmark.runBenchmarks(demoSorter.getSorters(), taLog);
            }
        });
    }
}
