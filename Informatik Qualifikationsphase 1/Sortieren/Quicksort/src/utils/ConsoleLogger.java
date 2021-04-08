package utils;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

public class ConsoleLogger implements Logger {
    // Output everything in utf-8
    public ConsoleLogger() {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
    }

    @Override
    public void print(Object o) {
        System.out.print(o);
    }

    @Override
    public void printf(String s, Object... args) {
        System.out.printf(s, args);
    }

    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public void println(String s) {
        System.out.println(s);
    }

    @Override
    public void clear() {
        System.out.print(Colors.CLEAR);
    }
}
