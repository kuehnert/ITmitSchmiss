package main;

import java.util.Arrays;
import java.util.List;

import sorters.*;

/**
 * main.Benchmark Misst und vergleicht die Dauer von x Sortiervorgängen in den
 * unterschiedlichen Methoden
 */
public class Benchmark {
    public static final int NUM_TESTS = 3_000;
    public static final int TEST_SIZE = 1_000;

    public static void runBenchmarks(List<Sorter> sorters) {
        for (Sorter sorter : sorters) {
            sorter.prepareBenchmark();
        }

        for (int i = 0; i < NUM_TESTS; i++) {
            int[] a = StressTest.generateRandom(TEST_SIZE);

            for (Sorter sorter : sorters) {
                sorter.benchmark(Arrays.copyOf(a, a.length));
                System.out.print(sorter.getID());
            }
        }
        System.out.println();

        for (Sorter sorter : sorters) {
            System.out.println(sorter.getBenchmarkResult());
        }
    }

    public static void main(String[] args) {
        ShowSorter show = new ShowSorter();
        runBenchmarks(show.getSorters());
        System.out.println("\n\nFiniss!");
    }
}
