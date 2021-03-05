import java.util.Arrays;

public class Schuelerverwaltung {
    Schueler[] schueler; // Instantvariable
    int anzahl;

    public Schuelerverwaltung() {
        schueler = new Schueler[30]; // Maxwert
        anzahl = 0;

        hinzufuegen(new Schueler("Klock", "Hans", 26, 2.1));
        hinzufuegen(new Schueler("Beutlin", "Frodo", 20, 1.0));
        hinzufuegen(new Schueler("Gamdschie", "Samweis", 25, 1.3));
        hinzufuegen(new Schueler("A", "Samweis", 2500, 1.3));

        ausgeben();

        loescheLetzten();
        ausgeben();
        loescheLetzten();
        ausgeben();
        loescheLetzten();
        ausgeben();
        loescheLetzten();
        ausgeben();
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

    public static void main(String[] args) {
        new Schuelerverwaltung();
    }
}
