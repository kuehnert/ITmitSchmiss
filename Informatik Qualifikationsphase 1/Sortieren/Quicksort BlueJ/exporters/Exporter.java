package exporters;

import sorters.Sorter;

public abstract class Exporter {
    protected Sorter sorter;

    protected Exporter(Sorter sorter) {
        this.sorter = sorter;
    }

    public abstract String getLog();

    public abstract void logPartition();

    public abstract void logLine();

    public abstract void logSwap(int x, int y);
}
