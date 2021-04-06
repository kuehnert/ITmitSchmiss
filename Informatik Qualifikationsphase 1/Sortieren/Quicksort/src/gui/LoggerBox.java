package gui;

import utils.Logger;

import javax.swing.*;

public class LoggerBox extends JTextArea implements Logger {
    @Override
    public void println(String s) {
        this.print(s + "\n");
    }

    @Override
    public void print(Object o) {
        super.append(o.toString());
    }

    @Override
    public void clear() {
        super.setText("");
    }

    @Override
    public void println() {
        print("\n");
    }

    @Override
    public void printf(String s, Object... args) {
        print(String.format(s, args));
    }
}
