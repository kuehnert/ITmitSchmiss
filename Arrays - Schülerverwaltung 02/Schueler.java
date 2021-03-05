public class Schueler {
    private String nachname;
    private String vorname;
    private int alter;
    private double durchschnitt;

    public Schueler(String nachname, String vorname, int alter, double durchschnitt) {
        setNachname(nachname);
        setVorname(vorname);
        setAlter(alter);
        setDurchschnitt(durchschnitt);
    }

    public void setNachname(String nachname) {
        if (nachname.length() < 2) { // Nachname soll min. 2 Buchstaben lang sein
            // ungültige Eingabe
            System.err.println("FEHLER: Nachname ungültig: " + nachname);
            this.nachname = "Ungültig";
            this.alter = -1;
            return;
        }

        this.nachname = nachname;
    }

    public void setVorname(String vorname) {
        if (vorname.length() < 2) {
            System.err.println("FEHLER: Vorname ungültig: " + vorname);
            this.vorname = "Ungültig";
            this.alter = -1;
            return;
        }

        this.vorname = vorname;
    }

    public void setAlter(int alter) {
        if (alter < 8 || alter > 30) {
            System.err.println("FEHLER: Alter muss zwischen 8 und 30 sein. " + alter);
            this.alter = -1;
            return;
        }

        this.alter = alter;
    }

    public void setDurchschnitt(double durchschnitt) {
        if (durchschnitt < 1.0 || durchschnitt > 4.0) {
            System.err.println("FEHLER: Durchschnitt muss zwischen 1.0 und 4.0 sein");
            this.durchschnitt = 1000.0;
            this.alter = -1;
            return;
        }

        this.durchschnitt = durchschnitt;
    }

    public boolean istGueltig() {
        return alter > -1;
    }

    public String toString() {
        return nachname + ", " + vorname + " (" + alter + ") " + durchschnitt;
    }
}
Wir programmieren eine kleine Schülerverwaltung in Java (Teil 2)
