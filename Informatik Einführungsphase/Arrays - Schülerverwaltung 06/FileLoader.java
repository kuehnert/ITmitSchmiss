import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileLoader {
    private static final String filename = "FileLoader.txt";

    public static void save(String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("FEHLER: Beim Speichern von " + filename + " ist ein Fehler aufgetreten.");
            // e.printStackTrace();
        }
    }

    public static String load() {
        String input = null;

        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("FEHLER: Datei " + filename + " nicht gefunden!");
            return null;
        }

        try {
            input = new String(Files.readAllBytes(Path.of(filename)));
        } catch (IOException e) {
            System.out.println("FEHLER beim Lesen der Datei " + filename + "!");
            return null;
        }

        return input;
    }

    public static void main(String[] args) {
        String out = "Das ist ein Test vom Laden und Speichern";
        System.out.println("SAVE: " + out);
        save(out);
        String in = load();
        System.out.println("LOAD: " + in);
    }
}
