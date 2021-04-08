package main;

import java.util.Arrays;
import java.util.List;

import sorters.*;
import utils.ArrayMaker;
import utils.ConsoleLogger;
import utils.Logger;

/**
 * main.Benchmark Misst und vergleicht die Dauer von x Sortiervorgängen in den
 * unterschiedlichen Methoden
 */
public class Benchmark {
    public static final int NUM_TESTS = 3_000;
    public static final int TEST_SIZE = 1_000;

    public static void runBenchmarks(List<Sorter> sorters, Logger logger) {
        logger.printf("Performing Benchmarking of %d Quicksort Methods\n", sorters.size());
        logger.printf("Each method sorts %d random arrays of %d elements each:\n", NUM_TESTS, TEST_SIZE);
        logger.printf("The digit (1..%d) corresponds to each method\n", sorters.size());

        for (Sorter sorter : sorters) {
            sorter.prepareBenchmark();
        }

        for (int i = 0; i < NUM_TESTS; i++) {
            int[] a = ArrayMaker.generateRandom(TEST_SIZE);

            for (Sorter sorter : sorters) {
                sorter.benchmark(Arrays.copyOf(a, a.length));
                logger.print(sorter.getID());
            }
        }
        logger.println("\n");

        for (Sorter sorter : sorters) {
            logger.println(sorter.getBenchmarkResult());
        }
    }

    public static void main(String[] args) {
        Logger consoleLogger = new ConsoleLogger();
        DemoSorter show = new DemoSorter(consoleLogger);
        runBenchmarks(show.getSorters(), consoleLogger);
        consoleLogger.println("\n\nFiniss!");
    }
}
