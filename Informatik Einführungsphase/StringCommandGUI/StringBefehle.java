public class StringBefehle {
    /**
     * Die Methode gibt den String verdoppelt, also zweimal hintereinander zur�ck
     */
    public static String duplicate(String in) {
        String out = "";

        // Manipuliere den String
        out += in + " " + in;

        return out;
    }

    /**
     * Die Methode gibt den String Zeichen fuer Zeichen rueckwaerts zurueck
     */
    public static String reverse(String in) {
        String out = "";

        for (int i = in.length() - 1; i >= 0; i -= 1) {
            out += in.charAt(i);
        }

        return out;
    }

    // Schreiben Sie eine analoge Methode, jedes einzelne Zeichen in
    // einen Grossbuchstaben wandelt und aneinander geh�ngt zur�ckgibt.
    public static String upper(String in) {
        return in.toUpperCase();
    }
}
