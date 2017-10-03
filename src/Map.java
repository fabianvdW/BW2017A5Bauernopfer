import java.util.ArrayList;

public class Map {
    private Figur[][] map; // 1. Dim ist A-H; 2. Dim ist 1-8
    private Figur[][] initalMap; // wird nur zur Visualsierung, um den Initalzustand zu haben, benutzt
    private ArrayList<Zug> verlauf; // für die Visualisierung

    public Map() {
        this.reset();
    }

    /**
     * Setzt die ganze Map zurück. Bauern müssen neu generiert werden
     */
    public void reset() {
        map = new Figur[8][8];
        initalMap = new Figur[8][8];
        verlauf = new ArrayList<Zug>();
    }

    public ArrayList<Figur> getFiguren() {
        ArrayList<Figur> f = new ArrayList<Figur>();
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                if (map[i][n] != null) {
                    f.add(map[i][n]);
                }
            }
        }
        return f;
    }

    /**
     * Generiert eine bestimmte Anzahl an Bauern und einen Turm und setzt diese zufällig auf das Spielbrett, nachdem das Spielbrett davor zurückgesetzt wurde
     *
     * @param anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden
     */
    public void spawnBauern(int anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden) {
        this.reset();
        //DEBUG für GUI
        if (anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden == 1) {
            map[0][0] = new Figur(false);
            initalMap[0][0] = map[0][0];
            int i = 0;
            doMove(new Zug(i++, 0, i, 0));
            doMove(new Zug(i++, 0, i, 0));
            doMove(new Zug(i++, 0, i, 0));
            doMove(new Zug(i++, 0, i, 0));
            doMove(new Zug(i++, 0, i, 0));
            doMove(new Zug(i++, 0, i, 0));
            return;
        }
        if (anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden > 8 * 8 - 1) {
            System.out.println("Okay... Das Feld hat nur 64 Felder nur mal so als Tipp (Angegeben wurden aber " + anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden + " Bauern bereits");
        } else if (anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden < 1) {
            System.out.println("Dass will ich sehen, wie du es hinkriegst mit weniger als einem Bauern den Turm zu fangen...");
        }

        int x;
        int y;
        for (int i = 0; i < anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden; i++) {
            do {
                x = (int) Math.floor(Math.random() * 8);
                y = (int) Math.floor(Math.random() * 8);
            } while (map[x][y] != null);
            map[x][y] = new Figur(true, x, y);
            initalMap[x][y] = map[x][y];
        }
        do {
            x = (int) Math.floor(Math.random() * 8);
            y = (int) Math.floor(Math.random() * 8);
        } while (map[x][y] != null);
        map[x][y] = new Figur(false, x, y);
        initalMap[x][y] = map[x][y];
    }

    /**
     * Führt den Zug aus
     *
     * @param move
     */
    public void doMove(Zug move) {
        if (map[move.getX()][move.getY()] == null) {
            System.out.println("Error: Da ist keine Figur, die gerückt werden könnte " + move.toString());
        } else if (map[move.getToX()][move.getToY()] != null && !map[move.getToX()][move.getToY()].isWhite()) {
            System.out.println("Error: Auf dem Zielfeld ist bereits eine Figur (Bauer) vorhanden " + move.toString());
        } else {
            // Überprüfung auf gültigen Zug
            if (isValidBauerMove(map[move.getX()][move.getY()], move) || isValidTurmMove(map[move.getX()][move.getY()], move)) {
                map[move.getToX()][move.getToY()] = map[move.getX()][move.getY()];
                map[move.getToX()][move.getToY()].setPosition(move.getToX(), move.getToY());
                map[move.getX()][move.getY()] = null;
                verlauf.add(move);
            } else {
                System.out.println("Ungültiger Zug!" + move.toString());
            }
        }
    }

    private boolean isValidBauerMove(Figur f, Zug move) {
        int x = Math.abs(move.getX() - move.getToX());
        int y = Math.abs(move.getY() - move.getToY());
        return f.isWhite() && x + y < 1;
    }

    private boolean isValidTurmMove(Figur f, Zug move) {
        int x = Math.abs(move.getX() - move.getToX());
        int y = Math.abs(move.getY() - move.getToY());
        return !f.isWhite() && (x == 0 || y == 0);
    }

    private boolean isValidDameMove(Figur f, Zug move) {
        int x = Math.abs(move.getX() - move.getToX());
        int y = Math.abs(move.getY() - move.getToY());
        return isValidTurmMove(f, move) || x == y;
    }

    /**
     * Gibt zurück, ob der Turm bereits gefangen wurde
     *
     * @return Turm gefangen
     */
    public boolean isTurmGefangen() {
        for (Figur f[] : map) {
            for (Figur ff : f) {
                if (ff != null) {
                    if (!ff.isWhite()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @return Anfangszustand des Spielbretts
     */
    public Figur[][] getInitalMap() {
        return initalMap;
    }

    /**
     * @return Liste jedes getätigten Zuges auf dem Spielbrett
     */
    public ArrayList<Zug> getVerlauf() {
        return verlauf;
    }
}
