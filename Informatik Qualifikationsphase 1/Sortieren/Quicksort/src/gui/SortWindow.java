package gui;

import exporters.HTMLExporter;
import javafx.application.Platform;
import main.Benchmark;
import main.ShowSorter;
import main.StressTest;
import sorters.Sorter;
import utils.ArrayMaker;
import utils.DemoArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class SortWindow extends JFrame {
    private JPanel pMain;
    private JEditorPane epSource;
    private JComboBox cbSorter;
    private JButton bStressTest;
    private JButton bBenchmark;
    private JButton bDemo;
    private JTextArea taLog;
    private JScrollPane spWebView;
    private JScrollPane spSource;
    private JEditorPane epDemo;
    private JScrollPane spDemo;
    private JComboBox cbArrays;
    private JTextField tfSelectedArray;

    private final ShowSorter showSorter;
    private final ArrayMaker arrayMaker = new ArrayMaker();
    private Sorter selectedSorter;

    public SortWindow() throws HeadlessException {
        super();
        setTitle("Quicksort Demo");
        add(pMain);
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cbArrays.setModel(new DefaultComboBoxModel(arrayMaker.getArrays()));

        showSorter = new ShowSorter();
        cbSorter.setModel(new DefaultComboBoxModel(showSorter.getSorters().toArray()));
        // PrintStream printStream = new PrintStream(new CustomOutputStream(taLog), true, StandardCharsets.UTF_8);
        // System.setOut(printStream);
        // System.setErr(printStream);

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
                System.out.println("Demoing " + selectedSorter);
                selectedSorter.setExporter(new HTMLExporter(selectedSorter));
                String s = tfSelectedArray.getText();
                int[] arrayToSort = ArrayMaker.fromString(s);
                selectedSorter.setArray(arrayToSort);
                s = DemoArray.toString(arrayToSort);
                tfSelectedArray.setText(s);
                selectedSorter.debug();
                String out = selectedSorter.getExporter().getLog();
                epDemo.setText(out);
            }
        });

        // Stress-Test Action
        bStressTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StressTest.stressTest(selectedSorter);
            }
        });

        // Benchnmark Action
        bBenchmark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Benchmark.runBenchmarks(showSorter.getSorters());
            }
        });
    }
}
