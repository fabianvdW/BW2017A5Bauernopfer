import java.util.ArrayList;

public class Map {
    private Figur[][] map; // 1. Dim ist A-H; 2. Dim ist 1-8
    private Figur[][] initalMap; // wird nur zur Visualsierung, um den Initalzustand zu haben, benutzt
    private ArrayList<Zug> verlauf; // für die Visualisierung
    private boolean isTurmGefangen;
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
    public Position getTurmPosition(){
        for(int i=0;i<8;i++){
            for(int k=0;k<8;k++){
                if(map[i][k]!=null &&!map[i][k].isWhite()){
                    return new Position(i,k);
                }
            }
        }
        System.exit(2);
        return null;
    }
    public ArrayList<Position> getBauerPositions(){
        ArrayList<Position> positions = new ArrayList<Position>();
        for(int i=0;i<8;i++){
            for(int k=0;k<8;k++){
                if(map[i][k]!=null && map[i][k].isWhite()){
                    positions.add(new Position(i,k));
                }
            }
        }
        return  positions;
    }
    /**
     *
     * @param posx
     * @param posy
     */
    public void spawnBauer(int posx, int posy){
        if(posx<=7 && posx>=0 && posy<=7 &&posy>=0) {
            if (map[posx][posy] == null) {
                map[posx][posy]= new Figur(true);
                initalMap[posx][posy] = map[posx][posy];
                return;
            }
        }
        System.exit(1);
    }

    public void spawnTurm(int posx, int posy){
        if(posx<=8 && posx>=0 && posy<=8 &&posy>=0) {
            if (map[posx][posy] == null) {
                map[posx][posy]= new Figur(false);
                initalMap[posx][posy] = map[posx][posy];
                return;
            }
        }
        System.exit(1);
    }
    /**
     * Generiert eine bestimmte Anzahl an Bauern und einen Turm und setzt diese zufällig auf das Spielbrett, nachdem das Spielbrett davor zurückgesetzt wurde
     *
     * @param anzahlBauern
     */
    public void spawnBauern(int anzahlBauern) {
        this.reset();
        //DEBUG für GUI
        /*if(anzahlDerOpferBauernDieEinfachSoDemGroßenBösenSchwarzenTurmZumFraßVorgeworfenWerden == 1){
            map[0][0] = new Figur(false);
            initalMap[0][0] = map[0][0];
            int i = 0;
            doMove(new Zug(i++,0, i,0));
            doMove(new Zug(i++,0, i,0));
            doMove(new Zug(i++,0, i,0));
            doMove(new Zug(i++,0, i,0));
            doMove(new Zug(i++,0, i,0));
            doMove(new Zug(i++,0, i,0));
            return;
        }*/
        if (anzahlBauern > 8 * 8 - 1) {
            System.out.println("Okay... Das Feld hat nur 64 Felder nur mal so als Tipp (Angegeben wurden aber " + anzahlBauern + " Bauern bereits");
            System.exit(0);
        } else if (anzahlBauern < 1) {
            System.out.println("Dass will ich sehen, wie du es hinkriegst mit weniger als einem Bauern den Turm zu fangen...");
            System.exit(0);
        }

        int x;
        int y;
        for (int i = 0; i < anzahlBauern; i++) {
            do {
                x = (int) Math.floor(Math.random() * 8);
                y = (int) Math.floor(Math.random() * 8);
            } while (map[x][y] != null);
            map[x][y] = new Figur(true);
            initalMap[x][y] = map[x][y];
        }
        //Turm verhalten ist relativ dumm.
        do {
            x = (int) Math.floor(Math.random() * 8);
            y = (int) Math.floor(Math.random() * 8);
        } while (map[x][y] != null);
        map[x][y] = new Figur(false);
        initalMap[x][y] = map[x][y];
    }
    public boolean TurmisCatchAble(){
        Position p= this.getTurmPosition();
        ArrayList<Position> positions= this.getBauerPositions();
        for(Position px: positions){
            if((px.posx+1==p.posx&& p.posy==px.posy) || (px.posx-1==p.posx&& p.posy==px.posy) || (px.posx==p.posx&& p.posy+1==px.posy) || (px.posx==p.posx&& p.posy-1==px.posy)){
                return true;
            }
        }
        return false;
    }
    public Position getCatchPosition(){
        Position p= this.getTurmPosition();
        ArrayList<Position> positions= this.getBauerPositions();
        for(Position px: positions){
            if((px.posx+1==p.posx&& p.posy==px.posy) || (px.posx-1==p.posx&& p.posy==px.posy) || (px.posx==p.posx&& p.posy+1==px.posy) || (px.posx==p.posx&& p.posy-1==px.posy)){
                return px;
            }
        }
        return null;
    }
    /**
     * Führt den Zug aus
     *
     * @param move
     */
    public void doMove(Zug move) {
        if (map[move.getX()][move.getY()] == null) {
            System.out.println("Error: Da ist keine Figur, die gerückt werden könnte [Zug (" + move.getX() + ", " + move.getY() + ") -> (" + move.getToX() + ", " + move.getToY() + ")]");
            System.exit(0);
        } else if (map[move.getToX()][move.getToY()] != null && map[move.getToX()][move.getToY()].isWhite()) {
            System.out.println("Error: Auf dem Zielfeld ist bereits eine Figur (Bauer) vorhanden [Zug (" + move.getX() + ", " + move.getY() + ") -> (" + move.getToX() + ", " + move.getToY() + ")]");
            System.exit(0);
        } else {
            if(map[move.getToX()][move.getToY()]!=null &&!map[move.getToX()][move.getToY()].isWhite()){
                isTurmGefangen=true;
            }
            map[move.getToX()][move.getToY()] = map[move.getX()][move.getY()];
            map[move.getX()][move.getY()] = null;
            verlauf.add(move);
        }
    }

    /**
     * Gibt zurück, ob der Turm bereits gefangen wurde
     *
     * @return Turm gefangen
     */
    public boolean isTurmGefangen() {
        return isTurmGefangen;
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
