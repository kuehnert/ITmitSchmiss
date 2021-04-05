package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReadWrite {
    private FileReadWrite() {
    }

    public static void save(String filename, String data) {
        try {
            PrintWriter writer = new PrintWriter(new File(filename), StandardCharsets.UTF_8);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public static String load(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.printf("FEHLER: File '%s' not found.", filename);
            return null;
        }

        try {
            return new String(Files.readAllBytes(Path.of(filename)), "UTF-8");
        } catch (IOException e) {
            System.out.println("File not found! Programm is closing with Error!");
            e.printStackTrace();
            return null;
        }
    }
}
