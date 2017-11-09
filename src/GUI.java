import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GUI extends JPanel {
    private Map map;
    private Figur[][] brett; // Auf diesem Brett wird der ganze Verlauf erneut durchsimuliert
    private Timer t; // Für eine schrittweise Reproduktion der Schritte
    private int atMove;
    private JTextArea log;

    public GUI(Map m, JTextArea l) {
        this.map = m;
        this.log = l;
        brett = m.getInitalMap();
        this.atMove = 0;

        // Initalisierung des Timers für die Visualisierung
        this.t = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (atMove == map.getVerlauf().size() || map.getVerlauf().size() == 0) {
                    System.out.println("Finished with redo");
                    log.append("\nDone...");
                    t.stop();
                } else {
                    Zug m = map.getVerlauf().get(atMove++);
                    System.out.println("Redo: " + m.toString());
                    if(brett[m.getX()][m.getY()].isWhite()){
                        log.append("White: " + m.toString() + "\n");
                    }
                    else{
                        log.append("\tBlack: " + m.toString() + "\n");
                    }
                    brett[m.getToX()][m.getToY()] = brett[m.getX()][m.getY()];
                    brett[m.getX()][m.getY()] = null;
                    repaint();
                    log.repaint();
                }
            }
        });
        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        boolean color = true;
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                if (color) {
                    g.setColor(new Color(255, 211, 155));
                } else {
                    g.setColor(new Color(205, 133, 63));
                }
                color = !color;
                g.fillRect(i * 50, n * 50, 50, 50);

                // Draw Figur
                if (brett[i][n] != null) {
                    if (brett[i][n].isWhite()) {
                        g.setColor(Color.WHITE); // Bauer
                    } else {
                        g.setColor(Color.BLACK); // Turm
                    }
                    g.fillOval( i * 50 + 10, n * 50 + 10, 30, 30);
                }
            }
            color = !color;
        }
    }
}
