package main;

import sorters.Quicksort2;
import sorters.Sorter;
import utils.ArrayMaker;

public class StressTest {
    public static final int NUM_TESTS = 500;
    public static final int TEST_SIZE = 10_000;

    public static void stressTest(Sorter sorter) {
        sorter.setDebug(false);

        for (int i = 0; i < NUM_TESTS; i++) {
            int[] a = ArrayMaker.generateRandom(TEST_SIZE);
            sorter.setArray(a);
            sorter.quicksort();
            sorter.check();
        }
    }

    public static void main(String[] args) {
        Sorter s = new Quicksort2();
        stressTest(s);
    }
}
