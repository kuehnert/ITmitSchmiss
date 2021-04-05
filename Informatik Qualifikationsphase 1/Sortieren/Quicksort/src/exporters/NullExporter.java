package exporters;

import sorters.Sorter;

public class NullExporter extends Exporter {
    public NullExporter(Sorter sorter) {
        super(sorter);
    }

    @Override
    public String getLog() {
        return "";
    }

    @Override
    public void logPartition() {
    }

    @Override
    public void logLine() {
    }

    @Override
    public void logSwap(int x, int y) {
    }
}
