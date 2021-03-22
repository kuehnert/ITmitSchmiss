import java.util.Arrays;
import java.util.Scanner;

public class Schuelerverwaltung {
    Scanner tastatur = new Scanner(System.in);
    Schueler[] schueler; // Instantvariable
    int anzahl;

    public Schuelerverwaltung() {
        schueler = new Schueler[30]; // Maxwert
        anzahl = 0;

        zeigeMenue();
    }

    public void hinzufuegen(Schueler s) {
        if (anzahl >= schueler.length) {
            System.err
                    .println("FEHLER: Die Liste ist schon voll. Es passen nur " + schueler.length + " Schüler hinein.");
            return;
        }

        if (!s.istGueltig()) {
            System.err.println("Ungültige Daten in Schüler " + s);
            return;
        }

        schueler[anzahl] = s;
        anzahl += 1;
    }

    public void loescheLetzten() {
        if (anzahl < 1) {
            System.err.println("FEHLER: Keine Schüler vorhanden!");
            return;
        }

        anzahl -= 1;
        schueler[anzahl] = null;
    }

    private void ausgeben() {
        System.out.println("\nIm Abiturjahrgang sind " + anzahl + " Schüler mit dabei:");

        for (int i = 0; i < anzahl; i++) {
            System.out.println(i + ": " + schueler[i]);
        }
    }

    public void zeigeMenue() {
        System.out.println("Willkommen bei der Schülerliste");
        int gewaehlt = -1;

        do {
            zeigeHilfe();
            System.out.print("Deine Wahl: ");
            gewaehlt = Integer.parseInt(tastatur.nextLine());

            if (gewaehlt == 1) {
                ausgeben();
            } else if (gewaehlt == 2) {
                ausgebenSchueler();
            } else if (gewaehlt == 3) {
                hinzufuegenSchueler();
            } else if (gewaehlt == 4) {
                loescheLetzten();
                System.out.println("Letzter Schüler gelöscht!");
            } else if (gewaehlt == 5) {
                sortiereListe();
            } else if (gewaehlt == 6) {
                loadSchueler();
            } else if (gewaehlt == 7) {
                saveSchueler();
            } else {
                System.out.println("Keine gültige Wahl. Bitte gib eine Ziffer von 0 bis 5 ein.");
            }
        } while (gewaehlt != 0);

        System.out.println("OK, mach's gut!");
        System.exit(0);
    }

    public void hinzufuegenSchueler() {
        Schueler neuer = new Schueler();
        System.out.println("Bitte gib die Daten des neuen Schülers ein.");

        System.out.print("Vorname: ");
        neuer.setVorname(tastatur.nextLine());

        System.out.print("Nachname: ");
        neuer.setNachname(tastatur.nextLine());

        System.out.print("Alter: ");
        String eingabe = tastatur.nextLine();
        int alter = Integer.parseInt(eingabe);
        neuer.setAlter(alter);

        System.out.print("Durchschnitt: ");
        eingabe = tastatur.nextLine();
        double durchschnitt = Double.parseDouble(eingabe);
        neuer.setDurchschnitt(durchschnitt);

        System.out.println("Dieser Schüler wird der Liste hinzugefügt:");
        System.out.println(neuer);

        hinzufuegen(neuer);
    }

    public void ausgebenSchueler() {
        System.out.print("Welchen Schüler ausgeben? ");
        int index = Integer.parseInt(tastatur.nextLine());

        if (index < 0 || index > anzahl - 1) {
            System.out.println("Keine gültige Wahl. Bitte gib eine Zahl zwsichen 0 und " + (anzahl - 1) + " ein.");
        } else {
            System.out.println(schueler[index]);
        }
    }

    public void sortiereListe() {
        Arrays.sort(schueler, 0, anzahl);
    }

    public void zeigeHilfe() {
        System.out.println("Optionen:");
        System.out.println("1. Liste anzeigen");
        System.out.println("2. Schüler anzeigen");
        System.out.println("3. Schüler hinfügen");
        System.out.println("4. Letzten Schüler löschen");
        System.out.println("5. Liste sortieren");
        System.out.println("6. Liste laden");
        System.out.println("7. Liste speichern");
        System.out.println("0. Programm beenden");
    }

    public void saveSchueler() {
        String out = "";
        for (int i = 0; i < anzahl; i++) {
            out += schueler[i].toCSV();
        }

        FileLoader.save(out);
        System.out.println("Schülerliste gespeichert.");
    }

    public void loadSchueler() {
        String in = FileLoader.load();
        if (in == null) {
            System.out.println("Fehler beim Laden!");
            return;
        }

        String[] lines = in.split("\n");
        Arrays.fill(schueler, null);
        anzahl = 0;

        for (String line : lines) {
            hinzufuegen(new Schueler(line));
        }

        System.out.println("Habe " + anzahl + " Schüler eingelesen.");
    }

    public static void main(String[] args) {
        new Schuelerverwaltung();
    }
}
