package sorters;

import exporters.ANSIExporter;
import exporters.Exporter;
import exporters.NullExporter;

import java.util.Arrays;

public abstract class Sorter {
    protected String name;
    protected char id;
    protected int oLeft;
    protected int oRight;
    protected int left;
    protected int right;
    protected int pivotIndex;
    protected int pivotValue;
    protected int[] a;
    protected int[] original;
    protected int[] sorted;
    protected boolean debug;
    protected long duration;
    protected Exporter exporter;
    protected String[] previous;

    protected Sorter() {
        this(null);
    }

    protected Sorter(int[] a) {
        this.name = this.getClass().getSimpleName();
        this.id = name.charAt(name.length() - 1);
        this.debug = false;
        this.exporter = new NullExporter(this);
        this.previous = new String[2];
        Arrays.fill(previous, "nada");
        setArray(a);
    }

    public Exporter getExporter() {
        return exporter;
    }

    public void setExporter(Exporter exporter) {
        this.exporter = exporter;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean check() {
        if (isSuccessful()) {
            System.out.print(".");
            return true;
        } else {
            System.out.println("ERROR:");
            debug();
            return false;
        }
    }

    protected abstract void quicksort(int left, int right);

    public boolean isDebugging() {
        return debug;
    }

    public String getName() {
        return name;
    }

    public char getID() {
        return id;
    }

    public int[] getArray() {
        return a;
    }

    public void setArray(int[] a) {
        this.a = a;

        if (a != null) {
            this.original = Arrays.copyOf(a, a.length);
            this.sorted = Arrays.copyOf(a, a.length);
            Arrays.sort(sorted);
        }
    }

    public void debug() {
        quicksort(true);
        System.out.println(exporter.getLog());

        System.out.print("\nBefore : " + Arrays.toString(original));
        System.out.println("\nAfter  : " + Arrays.toString(a));
        System.out.print("Correct: " + Arrays.toString(sorted) + " " + successMark());
    }

    private String successMark() {
        return isSuccessful() ? "✔" : "❌";
    }

    public void prepareBenchmark() {
        duration = 0;
    }

    public void benchmark(int[] a) {
        this.a = a;
        long start = System.currentTimeMillis();
        quicksort(0, a.length - 1);
        long end = System.currentTimeMillis();
        duration += end - start;
    }

    public String getBenchmarkResult() {
        return String.format("%s: %7.2f Sekunden", name, duration / 1000.0);
    }

    public void quicksort() {
        quicksort(false);
    }

    public void quicksort(boolean debug) {
        this.a = Arrays.copyOf(original, original.length);
        this.debug = debug;
        oLeft = oRight = left = right = pivotIndex = pivotValue = -1;
        quicksort(0, a.length - 1);
    }

    public boolean isSuccessful() {
        return Arrays.equals(a, sorted);
    }

    public int getLeft() {
        return left;
    }

    public int getOriginalLeft() {
        return oLeft;
    }

    public int getRight() {
        return right;
    }

    public int getPivotValue() {
        return pivotValue;
    }

    public int getOriginalRight() {
        return oRight;
    }

    protected void swap(int i, int j) {
        if (i == j) return;

        if (debug) {
            exporter.logSwap(i, j);
        }

        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;

        if (debug) {
            printLine();
        }
    }

    protected void printPartition() {
        exporter.logPartition();
    }

    protected void printLine() {
        exporter.logLine();
    }

    private void checkInfiniteLoop() {
        String f = fingerprint();
        for (String prev : previous) {
            if (f.equals(prev)) {
                throw new RuntimeException("Infinite Loop in " + getName() + "!");
            }
        }

        previous[1] = previous[0];
        previous[0] = f;
    }

    private String fingerprint() {
        return String.format("%d,%d,%d,%d,%d", oLeft, oRight, pivotIndex, left, right);
    }
}
