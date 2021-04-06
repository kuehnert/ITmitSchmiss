package utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DemoArray {
    private int[] array;

    public DemoArray(int[] array) {
        setArray(array);
    }

    public static String toString(int[] a) {
        return Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(", "));
    }

    public int[] getArray() {
        return array;
    }

    public void setArray(int[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return DemoArray.toString(array);
    }
}
