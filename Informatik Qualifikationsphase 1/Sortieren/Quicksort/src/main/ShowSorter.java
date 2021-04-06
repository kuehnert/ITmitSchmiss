package main;

import sorters.Sorter;
import utils.ArrayMaker;
import utils.ClassFinder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ShowSorter {
    private List<Sorter> sorters;

    public ShowSorter() {
        findClasses();
    }

    public static void main(String[] args) {
        ShowSorter show = new ShowSorter();
        ArrayMaker arrayMaker = new ArrayMaker();

        show.debugArray(show.getSorters().get(0), arrayMaker.getArrays()[0].getArray());
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
