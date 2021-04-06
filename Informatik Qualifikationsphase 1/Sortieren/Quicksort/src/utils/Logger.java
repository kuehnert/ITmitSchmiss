package utils;

public interface Logger {
    public abstract void print(Object o);

    public abstract void println(String s);

    public abstract void println();

    public abstract void clear();

    public abstract void printf(String s, Object... args);
}
