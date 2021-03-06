package sorters;

import utils.Logger;

public class Quicksort1 extends Sorter {
    public Quicksort1(Logger logger) {
        super(logger);
    }

    public Quicksort1(int[] a, Logger logger) {
        super(a, logger);
    }

    private int[] partition(int l, int r) {
        oLeft = left = l;
        oRight = right = r;
        pivotValue = a[(left + right) / 2];

        printPartition();

        while (left <= right) {
            while (a[left] < pivotValue) {
                left += 1;
                printLine();
            }

            while (a[right] > pivotValue) {
                right -= 1;
                printLine();
            }

            if (left <= right) {
                swap(left, right);
                left++;
                right--;

                printLine();
            }
        }

        return new int[]{left, right};
    }

    protected void quicksort(int left, int right) {
        if (left < right) {
            int[] pivot = partition(left, right);
            quicksort(left, pivot[1]);
            quicksort(pivot[0], right);
        }
    }
}
