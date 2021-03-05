public class Schuelerverwaltung {
    Schueler[] schueler; // Instanzvariable

    public Schuelerverwaltung() {
        schueler = new Schueler[30]; // Maxwert

        schueler[0] = new Schueler("Klock", "Hans", 56, 2.1);
        schueler[1] = new Schueler("Beutlin", "Frodo", 50, 1.0);
        schueler[2] = new Schueler("Gamdschie", "Samweis", 45, 1.3);
        ausgeben();
        schueler[1].nachname = "Sauron";
        ausgeben();
    }

    private void ausgeben() {
        for (int i = 0; i < schueler.length; i++) {
            if (schueler[i] != null) {
                System.out.println(i + ": " + schueler[i].toString());
                System.out.println(i + ": " + schueler[i].ausgeben());
            } else {
                System.out.println("-");
            }
        }
    }

    public static void main(String[] args) {
        new Schuelerverwaltung();
    }
}
