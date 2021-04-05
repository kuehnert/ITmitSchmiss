package main;

import sorters.Sorter;
import utils.ClassFinder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ShowSorter {
    public static final int[][] SAMPLE_ARRAYS = {{14, 12, 5, 27, 6, 15, 3},
            {14, 27, 30, 27, 5, 53, 12},
            {44, 65, 30, 27, 50, 53, 28},
            {14, 12, 5, 27, 6, 15, 3},
            {1, 2, 3, 4, 5},
            {10, 9, 8, 7, 6, 5, 4, 3, 2, 1},
            {8, 5, 4, 2, 10, 9, 7, 6, 3, 1},
            {14, 14, 34, 30, 27, 5, 53, 12},
            {1, 6, 2, 5, 3, 4, 4, 3, 2, 6, 1},
            {51, 66, 1, 26, 52, 95, 80, 57, 70, 65},
            {614, 614, 614, 643, 751, 768, 701, 757}};
    private List<Sorter> sorters;

    public ShowSorter() {
        findClasses();
    }

    public static void main(String[] args) {
        ShowSorter show = new ShowSorter();
        show.debugArray(show.getSorters().get(0), SAMPLE_ARRAYS[0]);
        System.out.println("\n\nFiniss!");
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

                sorters.add((Sorter) clas.getDeclaredConstructor().newInstance());
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
