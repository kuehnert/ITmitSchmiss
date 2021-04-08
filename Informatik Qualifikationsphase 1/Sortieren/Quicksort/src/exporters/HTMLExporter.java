package exporters;

import sorters.Sorter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HTMLExporter extends Exporter {
    private static final String HEAD_START = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<title>Quicksort</title>\n"
            + "<style>";
    private static final String HEAD_END = "</style>\n</head>\n<body>\n";
    private static String styles;
    private final List<DataRow> rows;

    public HTMLExporter(Sorter sorter) {
        super(sorter);
        this.rows = new ArrayList<>();
        styles = styles();
    }

    public void logLine(boolean newPartition) {
        if (!sorter.isDebugging()) {
            return;
        }

        DataRow newRow = new DataRow(newPartition);

        for (int i = 0; i < sorter.getArray().length; i++) {
            newRow.data.add(prettifyElement(i));
        }

        rows.add(newRow);
    }

    private DataRow lastRow() {
        int last = rows.size() - 1;
        return (last < 0) ? null : rows.get(last);
    }

    @Override
    public void logPartition() {
        logLine(true);
    }

    @Override
    public void logLine() {
        logLine(false);
    }

    public void logSwap(int x, int y) {
        int xValue = sorter.getArray()[x];
        int yValue = sorter.getArray()[y];
        Objects.requireNonNull(lastRow()).comment = "Swap " + xValue + " @" + x + " & " + yValue + " @" + y;
    }

    private DataCell prettifyElement(int index) {
        int value = sorter.getArray()[index];
        int pivotValue = sorter.getPivotValue();
        List<String> classes = new ArrayList<>();

        if (index < sorter.getOriginalLeft() || index > sorter.getOriginalRight()) {
            classes.add("outside");
        } else {
            if (value == pivotValue) {
                classes.add("pivot");
            }

            if (index == sorter.getLeft()) {
                classes.add("left");
            }

            if (index == sorter.getRight()) {
                classes.add("right");
            }

            if (value < pivotValue) {
                classes.add("lower");
            }

            if (value > pivotValue) {
                classes.add("higher");
            }
        }

        String classStr = String.join(" ", classes);
        return new DataCell(value, classStr);
    }

    private String styles() {
        try {
            InputStream in = HTMLExporter.class.getResourceAsStream("/styles.css");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    private String headerRow() {
        StringBuilder out = new StringBuilder("<table caption='Quicksort Value Table'>\n<thead><tr>");
        int alen = sorter.getArray().length;

        for (int i = 0; i < alen; i++) {
            out.append("<th>a[").append(i).append("]</th>");
        }

        return out + "<th>Swaps</th></tr>\n</thead>\n";
    }

    private String head() {
        return HEAD_START + styles() + HEAD_END;
    }

    @Override
    public String getLog() {
        String table = rows.stream().map(Objects::toString).collect(Collectors.joining());

        return head() + "<h1>" + sorter.getName() + "</h1>" + headerRow() + "\n<tbody>\n<tr class='partition'>" + table + "</tr></tbody>\n</table" + "></body></html>";
    }
}

class DataRow {
    boolean newPartition;
    List<DataCell> data;
    String comment;

    public DataRow(boolean newPartition) {
        this.newPartition = newPartition;
        data = new ArrayList<>();
        comment = "";
    }

    @Override
    public String toString() {
        String dataStr = data.stream().map(Objects::toString).collect(Collectors.joining());
        return String.format("<tr class='%s'>%s<td>%s</td></tr>", (newPartition ? "partition" : ""), dataStr, comment);
    }
}

class DataCell {
    int value;
    String classes;

    public DataCell(int value, String classes) {
        this.value = value;
        this.classes = classes;
    }

    @Override
    public String toString() {
        return String.format("<td class='%s'>%d</td>", classes, value);
    }
}
