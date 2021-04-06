package utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ArrayMaker {
    public static final int MAX_VALUE = 100_000;
    private static final int[][] SAMPLE_ARRAYS = {{14, 12, 5, 27, 6, 15, 3}, {14, 27, 30, 27, 5, 53, 12}, {44, 65, 30
            , 27, 50, 53, 28}, {14, 12, 5, 27, 6, 15, 3}, {1, 2, 3, 4, 5}, {10, 9, 8, 7, 6, 5, 4, 3, 2, 1}, {8, 5, 4,
            2, 10, 9, 7, 6, 3, 1}, {14, 14, 34, 30, 27, 5, 53, 12}, {1, 6, 2, 5, 3, 4, 4, 3, 2, 6, 1}, {51, 66, 1, 26
            , 52, 95, 80, 57, 70, 65}, {614, 614, 614, 643, 751, 768, 701, 757}};
    private DemoArray[] arrays;

    public ArrayMaker() {
        arrays = new DemoArray[SAMPLE_ARRAYS.length];
        for (int i = 0; i < SAMPLE_ARRAYS.length; i++) {
            arrays[i] = new DemoArray(SAMPLE_ARRAYS[i]);
        }

        // arrays = new ArrayList<>(SAMPLE_ARRAYS.length);
        // for (int[] a : SAMPLE_ARRAYS) {
        //     arrays.add(new DemoArray(a));
        // }
    }

    public static int[] fromString(String s) {
        // Remove everything that is not a digit pr comma from String
        // to avoid number format exceptions
        s = s.replaceAll("[^\\d,]", "");

        // Split String at commas into String array with each number in a part
        String[] parts = s.split(",");

        // Convert each part into an int and collect in int array & return result
        int[] result = Arrays.stream(parts).mapToInt(Integer::parseInt).toArray();
        return result;
    }

    public static int[] generateRandom(int l) {
        int[] a = new int[l];

        // for (int i = 0; i < a.length; i++) {
        //     a[i] = (int) (Math.random() * MAX_VALUE);
        // }

        // Alternative version:
        Arrays.setAll(a, i -> (int) (Math.random() * MAX_VALUE));

        return a;
    }

    public List<DemoArray> getList() {
        return Arrays.asList(arrays);
    }

    public DemoArray[] getArrays() {
        return arrays;
    }
}
