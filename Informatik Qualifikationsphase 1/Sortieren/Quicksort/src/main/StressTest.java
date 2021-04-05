package main;

import sorters.Quicksort1;
import sorters.Quicksort2;
import sorters.Sorter;

public class StressTest {
    public static final int NUM_TESTS = 500;
    public static final int TEST_SIZE = 10_000;
    public static final int MAX_VALUE = 100_000;

    protected static int[] generateRandom(int l) {
        int[] a = new int[l];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * MAX_VALUE);
        }

        return a;
    }

    public static void stressTest(Sorter sorter) {
        sorter.setDebug(false);

        for (int i = 0; i < NUM_TESTS; i++) {
            int[] a = generateRandom(TEST_SIZE);
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
