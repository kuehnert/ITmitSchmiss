package utils;

import java.util.Scanner;

public class Keyboard {
    private static Scanner keys = new Scanner(System.in);

    public static char readChar(String prompt) {
        System.out.print(prompt + " ");

        String in = keys.next();
        return in.charAt(0);
    }

    public static int readInt(String prompt) {
        System.out.print(prompt + " ");

        String in = keys.next();
        int value = -1;
        try {
            value = Integer.parseInt(in);
        } catch (NumberFormatException e) {
        }

        return value;
    }
}
