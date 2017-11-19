import java.util.ArrayList;

public class Map {
    private Figur[][] map; // 1. Dim ist A-H; 2. Dim ist 1-8
    public Figur[][] initalMap; // wird nur zur Visualsierung, um den Initalzustand zu haben, benutzt
    public ArrayList<Zug> verlauf; // zur Visualisierung
    public boolean isTurmGefangen;

    public Map() {
        this.reset();
    }

    /**
     * Setzt die ganze Map zurück. Bauern müssen neu generiert werden
     */
    public void reset() {
        map = new Figur[8][8];
        initalMap = new Figur[8][8];
        verlauf = new ArrayList<>();
    }

    /**
     * @return Position des Turm auf dem Brett
     */
    public Position getTurmPosition() {
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (map[i][k] != null && !map[i][k].bauer) {
                    return new Position(i, k);
                }
            }
        }
        System.exit(2);
        return null;
    }

    /**
     * @return Liste der Positionen der Bauern auf dem Brett
     */
    public ArrayList<Position> getBauerPositions() {
        ArrayList<Position> positions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if (map[i][k] != null && map[i][k].bauer) {
                    positions.add(new Position(i, k));
                }
            }
        }
        return positions;
    }

    /**
     * @param x X-Position des Bauern
     * @param y Y-Position des Bauern
     */
    public void spawnBauer(int x, int y) {
        if (x <= 7 && x >= 0 && y <= 7 && y >= 0) {
            if (map[x][y] == null) {
                map[x][y] = new Figur(true);
                initalMap[x][y] = map[x][y];
                return;
            }
        }
        System.exit(1);
    }

    /**
     * @param x X-Position des Turm
     * @param y Y-Position des Turm
     */
    public void spawnTurm(int x, int y) {
        if (x <= 8 && x >= 0 && y <= 8 && y >= 0) {
            if (map[x][y] == null) {
                map[x][y] = new Figur(false);
                initalMap[x][y] = map[x][y];
                return;
            }
        }
        System.exit(1);
    }

    public boolean TurmIsCatchAble() {
        Position p = this.getTurmPosition();
        ArrayList<Position> positions = this.getBauerPositions();
        for (Position px : positions) {
            if ((px.x + 1 == p.x && p.y == px.y) || (px.x - 1 == p.x && p.y == px.y) || (px.x == p.x && p.y + 1 == px.y) || (px.x == p.x && p.y - 1 == px.y)) {
                return true;
            }
        }
        return false;
    }

    public Position getCatchPosition() {
        Position p = this.getTurmPosition();
        ArrayList<Position> positions = this.getBauerPositions();
        for (Position px : positions) {
            if ((px.x + 1 == p.x && p.y == px.y) || (px.x - 1 == p.x && p.y == px.y) || (px.x == p.x && p.y + 1 == px.y) || (px.x == p.x && p.y - 1 == px.y)) {
                return px;
            }
        }
        return null;
    }

    /**
     * Führt den Zug aus
     *
     * @param move Der auszuführende Zug
     */
    public void doMove(Zug move) {
        System.out.println("Führe Zug aus " + move.toString());
        if (map[move.x][move.y] == null) {
            System.out.println("Error: Da ist keine Figur, die gerückt werden könnte");
            System.exit(0);
        } else if (map[move.toX][move.toY] != null && map[move.toX][move.toY].bauer) {
            System.out.println("Error: Auf dem Zielfeld ist bereits eine Figur (Bauer) vorhanden");
            System.exit(0);
        } else {
            if (map[move.toX][move.toY] != null && !map[move.toX][move.toY].bauer) {
                isTurmGefangen = true;
            }
            map[move.toX][move.toY] = map[move.x][move.y];
            map[move.x][move.y] = null;
            verlauf.add(move);
        }
    }
}
