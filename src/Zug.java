public class Zug {
    private int x;
    private int y;
    private int toX;
    private int toY;

    public Zug(int x, int y, int toX, int toY) {
        this.x = x;
        this.y = y;
        this.toX = toX;
        this.toY = toY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }

    @Override
    public String toString() {
        return "[Zug (" + x + ", " + y + ") -> (" + toX + ", " + toY + ")]";
    }
}