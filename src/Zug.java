public class Zug {
    public int x;
    public int y;
    public int toX;
    public int toY;

    public Zug(int x, int y, int toX, int toY) {
        this.x = x;
        this.y = y;
        this.toX = toX;
        this.toY = toY;
    }

    @Override
    public String toString() {
        // Ausgabe
        return "[Zug (" + x + ", " + y + ") -> (" + toX + ", " + toY + ")]";
    }
}