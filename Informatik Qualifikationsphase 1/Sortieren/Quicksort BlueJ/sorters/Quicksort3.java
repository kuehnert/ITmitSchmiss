package sorters;

import utils.Logger;

public class Quicksort3 extends Sorter {
    public Quicksort3(Logger logger) {
        super(logger);
    }

    public Quicksort3(int[] a, Logger logger) {
        super(a, logger);
    }

    private int partition(int l, int r) {
        printPartition();

        oLeft = left = l;
        oRight = right = r;
        pivotIndex = left;
        pivotValue = a[pivotIndex];
        left += 1;

        printLine();

        while (true) {
            while (left < right && a[left] <= pivotValue) {
                left += 1;
                printLine();
            }

            while (a[right] > pivotValue) {
                right -= 1;
                printLine();
            }

            if (left < right) {
                swap(left, right);
            } else
                break;
        }

        // Swap pivot into place
        if (a[right] < pivotValue) {
            swap(right, pivotIndex);
        }

        return right;
    }

    protected void quicksort(int left, int right) {
        if (left < right) {
            int pivot = partition(left, right);

            quicksort(left, pivot - 1);
            quicksort(pivot + 1, right);
        }
    }
}
