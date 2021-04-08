package main;

import exporters.ANSIExporter;
import exporters.Exporter;
import sorters.Sorter;
import utils.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class DemoSorter {
    private List<Sorter> sorters;
    private final Logger logger;

    public DemoSorter(Logger logger) {
        this.logger = logger;
        findClasses();
    }

    public static void main(String[] args) {
        ArrayMaker arrayMaker = new ArrayMaker();
        Logger log = new ConsoleLogger();
        DemoSorter show = new DemoSorter(log);
        Sorter sortMethod = show.getSorters().get(0);
        Exporter export = new ANSIExporter(sortMethod);
        sortMethod.setExporter(export);
        DemoArray array = arrayMaker.getArrays()[0];

        System.out.println("Zeige Methode " + sortMethod.getName() +  " mit Array " + array + ": ");
        show.debugArray(sortMethod, array.getArray());
        System.out.println(export.getLog());
        log.println("\n\nFiniss!");
    }

    public List<Sorter> getSorters() {
        return sorters;
    }

    private void findClasses() {
        sorters = new ArrayList<>();

        try {
            List<Class<?>> sorterClasses = ClassFinder.getClassesForPackage("sorters");

            for (Class<?> clas : sorterClasses) {
                if (clas.isAssignableFrom(Sorter.class)) {
                    continue;
                }

                sorters.add((Sorter) clas.getDeclaredConstructor(Logger.class).newInstance(logger));
            }
        } catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void debugArray(Sorter sorter, int[] a) {
        sorter.setArray(a);
        sorter.debug();
    }

    public boolean sortArray(Sorter sorter, int[] a) {
        sorter.setArray(a);
        sorter.quicksort();
        return sorter.check();
    }
}
