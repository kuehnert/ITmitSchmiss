package main;

import sorters.Quicksort2;
import sorters.Sorter;
import utils.ArrayMaker;
import utils.ConsoleLogger;
import utils.Logger;

public class StressTest {
    public static final int NUM_TESTS = 500;
    public static final int TEST_SIZE = 10_000;

    public static void stressTest(Sorter sorter, Logger logger) {
        logger.printf("Stress testing method %s with sorting %d arrays of %d elements each:\n", sorter, NUM_TESTS, TEST_SIZE);
        sorter.setDebug(false);

        for (int i = 0; i < NUM_TESTS; i++) {
            int[] a = ArrayMaker.generateRandom(TEST_SIZE);
            sorter.setArray(a);
            sorter.quicksort();
            sorter.check();
        }
    }

    public static void main(String[] args) {
        Logger logger = new ConsoleLogger();
        Sorter s = new Quicksort2(logger);
        stressTest(s, logger);
    }
}
