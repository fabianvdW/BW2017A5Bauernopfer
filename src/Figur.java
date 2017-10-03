public class Figur {
    private boolean color; // true = white; false = black
    private int x;
    private int y;

    /**
     * @param color, true = white Bauer; false = black Turm
     */
    public Figur(boolean color) {
        this.color = color;
    }

    public Figur(boolean color, int x, int y){
        this(color);
        setPosition(x, y);
    }

    public boolean isWhite() {
        return color;
    }

    public void setColor(boolean newColor) {
        this.color = newColor;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
