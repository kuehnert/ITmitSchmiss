package sorters;

import utils.Logger;

public class Quicksort2 extends Sorter {
    public Quicksort2(Logger logger) {
        super(logger);
    }

    public Quicksort2(int[] a, Logger logger) {
        super(a, logger);
    }

    private int partition(int l, int r) {
        oLeft = left = l;
        oRight = right = r;
        pivotValue = a[(left + right) / 2];
        left--;
        right++;
        printPartition();

        while (true) {
            do {
                left++;
                printLine();
            } while (a[left] < pivotValue);

            do {
                right--;
                printLine();
            } while (a[right] > pivotValue);

            if (left < right) {
                swap(left, right);
            } else {
                return right;
            }
        }
    }

    protected void quicksort(int left, int right) {
        if (left >= right) {
            return;
        }

        int pivot = partition(left, right);
        quicksort(left, pivot);
        quicksort(pivot + 1, right);
    }
}
