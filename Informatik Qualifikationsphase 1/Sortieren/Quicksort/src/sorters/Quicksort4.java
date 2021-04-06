package sorters;

import utils.Logger;

public class Quicksort4 extends Sorter {
    public Quicksort4(Logger logger) {
        super(logger);
    }

    public Quicksort4(int[] a, Logger logger) {
        super(a, logger);
    }

    private int partition(int l, int r) {
        printPartition();

        oLeft = left = l;
        oRight = right = r;
        pivotValue = a[pivotIndex];

        printLine();

        // Pivot-Element ans Ende verschieben
        swap(pivotIndex, right);

        for (int i = oLeft; i < oRight; i++) {
            if (a[i] <= pivotValue) {
                swap(left, i);
                left++;
            }
        }

        // Pivot-Element an die richtige Position kopieren
        swap(oRight, left);

        // neue Pivot-Position zurückgeben
        return left;
    }

    protected void quicksort(int left, int right) {
        pivotIndex = (left + right) / 2;

        if (right > left) {
            int pivot = partition(left, right);
            quicksort(left, pivot - 1);
            quicksort(pivot + 1, right);

        }
    }
}
