class Schueler {
    String nachname;
    String vorname;
    int alter;
    double durchschnitt;

    public Schueler(String nachname, String vorname, int alter, double durchschnitt) {
        this.nachname = nachname;
        this.vorname = vorname;
        this.alter = alter;
        this.durchschnitt = durchschnitt;
    }

    public String ausgeben() {
        return nachname + ", " + vorname + " (" + alter + ") " + durchschnitt;
    }
}
