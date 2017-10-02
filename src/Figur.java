public class Figur {
    private boolean color; // true = white; false = black

    /**
     *
     * @param color, true = white Bauer; false = black Turm
     */
    public Figur(boolean color) {
        this.color = color;
    }

    public boolean isWhite() {
        return color;
    }

    public void setColor(boolean newColor) {
        this.color = newColor;
    }
}
