package exporters;

import sorters.Sorter;
import utils.Colors;

public class ANSIExporter extends Exporter {
    protected String log;

    public ANSIExporter(Sorter sorter) {
        super(sorter);
        log = "";
    }

    public void logLine() {
        if (!sorter.isDebugging()) {
            return;
        }

        String out = "\n";
        for (int i = 0; i < sorter.getArray().length; i++) {
            out += prettifyElement(i);
        }

        log += out;
    }

    @Override
    public void logPartition() {
        log += "\n------------------------------------------------------------------------------------------------";
    }

    public void logSwap(int x, int y) {
        int xValue = sorter.getArray()[x];
        int yValue = sorter.getArray()[y];
        log += "|| Swap " + xValue + " (@" + x + ") & " + yValue + " (@" + y + ")";
    }

    protected String prettifyElement(int index) {
        int value = sorter.getArray()[index];

        if (value == sorter.getPivotValue()) {
            return String.format(Colors.YELLOW_BOLD_BRIGHT + "%3d " + Colors.RESET, value);
        } else if (index < sorter.getOriginalLeft() || index > sorter.getOriginalRight()) {
            return String.format(Colors.BLACK_BRIGHT + "%3d " + Colors.RESET, value);
        } else if (index == sorter.getLeft() && index == sorter.getRight()) {
            return String.format(Colors.CYAN_BOLD_BRIGHT + "%3d " + Colors.RESET, value);
        } else if (index == sorter.getLeft()) {
            return String.format(Colors.GREEN + "%3d " + Colors.RESET, value);
        } else if (index == sorter.getRight()) {
            return String.format(Colors.BLUE + "%3d " + Colors.RESET, value);
        } else {
            return String.format(Colors.WHITE_BRIGHT + "%3d " + Colors.RESET, value);
        }
    }

    @Override
    public String getLog() {
        return log;
    }
}
