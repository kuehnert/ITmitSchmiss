package sorters;

public class MeinQuicksort extends Sorter {
    public MeinQuicksort() {
        super();
    }

    public MeinQuicksort(int[] a) {
        super(a);
    }

    private int partition(int l, int r) {
        printPartition();
        oLeft = left = l;
        oRight = right = r;
        pivotIndex = (left+right)/2;
        pivotValue = a[pivotIndex];

        printLine();

        while (true) {
            while (left < right && a[left] < pivotValue) {
                left += 1;
                printLine();
            }

            while (a[right] > pivotValue) {
                right -= 1;
                printLine();
            }

            if (left < right) {
                swap(left, right);
            } else {
                break;
            }
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

            quicksort(left, pivot);
            quicksort(pivot + 1, right);
        }
    }
}
