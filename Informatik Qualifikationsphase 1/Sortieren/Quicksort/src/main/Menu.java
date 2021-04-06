package main;

import exporters.ANSIExporter;
import exporters.HTMLExporter;
import sorters.Sorter;
import utils.*;

import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Menu {
    public static final String VERSION = "1.0.0";
    public static final int EXPORT_ANSI = 1;
    public static final int EXPORT_HTML = 2;

    private final ShowSorter show = new ShowSorter();
    private final ArrayMaker arrayMaker = new ArrayMaker();
    private int exporter_value = EXPORT_HTML;

    public Menu() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
    }

    public static void main(String[] args) {
        new Menu().showMenu();
    }

    private Sorter chooseSorter() {
        System.out.println("Wähle eine Quicksort-Variante:");
        List<Sorter> sorters = show.getSorters();
        Sorter choice = null;

        while (choice == null) {
            for (int i = 0; i < sorters.size(); i++) {
                System.out.printf("%2d: %s\n", i, sorters.get(i).getName());
            }

            int num = Keyboard.readInt("Deine Wahl:");
            if (num >= 0 && num < sorters.size()) {
                choice = sorters.get(num);
            }
        }

        return choice;
    }

    private int[] chooseArray() {
        System.out.println("Wähle ein Zahlenfeld zum Sortieren:");
        List<DemoArray> arrays = arrayMaker.getList();
        int[] choice = null;

        while (choice == null) {
            for (int i = 0; i < arrays.size(); i++) {
                System.out.printf("%2d: %s\n", i, arrays.get(i));
            }

            int num = Keyboard.readInt("Deine Wahl:");
            if (num >= 0 && num < arrays.size()) {
                choice = arrays.get(num).getArray();
            }
        }

        return choice;
    }


    private void showMenu() {
        System.out.printf(Colors.CLEAR + "Willkommen beim Quicksort-Demonstrator (v%s)\n", VERSION);
        System.out.println("Hier hast Du folgende Möglichkeiten:");
        Sorter sorter = null;
        boolean weiter = true;

        while (weiter) {
            System.out.println("\n1. Teste ein bestimmtes Verfahren");
            System.out.printf("2. Stresstest ein bestimmtes Verfahren (Sortiere %d Arrays mit je %d Elementen)\n",
                    StressTest.NUM_TESTS, StressTest.TEST_SIZE);
            System.out.printf("3. Benchmark aller Verfahren (Zeitvergleich mit %d Arrays mit je %d Elementen)\n",
                    Benchmark.NUM_TESTS, Benchmark.TEST_SIZE);
            System.out.println("4. Wähle Ausgabe im ANSI-Farbschema" + (exporter_value == EXPORT_ANSI ? " (✔ aktiv)"
                    : ""));
            System.out.println("5. Wähle Ausgabe im HTML-Format" + (exporter_value == EXPORT_HTML ? " (✔ aktiv)" : ""));
            if (sorter != null) {
                System.out.println("6. Speichere letzte Ausgabe");
            }
            System.out.println("X. Beende das Programm");

            char choice = Keyboard.readChar("Deine Wahl:");
            switch (choice) {
                case '1':
                    sorter = chooseSorter();
                    int[] numbers = chooseArray();
                    if (exporter_value == EXPORT_ANSI) {
                        sorter.setExporter(new ANSIExporter(sorter));
                    } else {
                        sorter.setExporter(new HTMLExporter(sorter));
                    }

                    show.debugArray(sorter, numbers);
                    break;
                case '2':
                    sorter = chooseSorter();
                    StressTest.stressTest(sorter);
                    System.out.println("\nFertig.");
                    break;
                case '3':
                    Benchmark.runBenchmarks(show.getSorters());
                    break;
                case '4':
                    exporter_value = EXPORT_ANSI;
                    break;
                case '5':
                    exporter_value = EXPORT_HTML;
                    break;
                case '6':
                    if (sorter != null) {
                        String home = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
                        String filename = home + "/QuicksortOutput.html";
                        FileReadWrite.save(filename, sorter.getExporter().getLog());
                        System.out.printf("Ergebnis gespeichert in %s.", filename);
                        try {
                            Desktop.getDesktop().open(new File(filename));
                        } catch (IOException ignored) {
                        }
                    }
                    break;
                case 'x':
                case 'X':
                    weiter = false;
                    break;
                default:
                    System.out.println("Das habe ich leider nicht verstanden. :(");
            }
        }

        System.out.println("Bye-bye.");
    }
}
