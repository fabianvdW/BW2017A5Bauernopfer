import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        Map m = new Map();
        teilaufgabe1(m);

        // Grafische Aufarbeitung des Prozesses
        JFrame f = new JFrame();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JTextArea log = new JTextArea();
        JScrollPane scroll = new JScrollPane(log);
        f.setLayout(new GridLayout());
        f.add(scroll);
        f.add(new GUI(m, log, false));
        //f.setSize(416, 439);
        f.setSize(816, 439);
        f.setVisible(true);
    }

    public static void teilaufgabe1(Map m) {
        //spawnen der Bauern auf der Diagonalen
        for (int i = 0; i < 8; i++) {
            m.spawnBauer(i, i);
        }

        m.spawnTurm(0, 7);
        boolean bauerZug = true;
        int turmGezogen = 0;
        int bauernGezogen = 0;
        int anzDurchgaenge = 0;
        System.out.println("Turm gefangen: " + m.isTurmGefangen);
        while (!m.isTurmGefangen) {
            if (bauerZug) {
                if (m.TurmIsCatchAble()) {
                    Position p = m.getCatchPosition();
                    m.doMove(new Zug(p.x, p.y, m.getTurmPosition().x, m.getTurmPosition().y));
                } else {
                    // Zumachen der Diagonalen zu einer Linie
                    Position p = m.getTurmPosition();
                    Position p2 = m.getBauerPositions().get(0);
                    if (p2.x <= p.x && p2.y < p.y) {
                        Position p3 = m.getBauerPositions().get(bauernGezogen++);
                        m.doMove(new Zug(p3.x, p3.y, p3.x, p3.y + 1));

                    } else {
                        Position p3 = m.getBauerPositions().get(bauernGezogen++);
                        m.doMove(new Zug(p3.x, p3.y, p3.x + 1, p3.y));
                    }
                    if (bauernGezogen + anzDurchgaenge == 7) {
                        bauernGezogen = 0;
                        anzDurchgaenge++;
                    }
                }
            } else {
                // Der Turm bewegt sich abwechselnd nur um 1 Feld nach rechts oder links
                Position p = m.getTurmPosition();
                Position p2 = m.getBauerPositions().get(0);
                int modulo = 1;
                if (!(p2.x <= p.x && p2.y < p.y)) {
                    modulo = 0;
                }
                if (turmGezogen % 2 == modulo) {
                    m.doMove(new Zug(p.x, p.y, p.x - 1, p.y));
                } else {
                    m.doMove(new Zug(p.x, p.y, p.x + 1, p.y));
                }
                turmGezogen++;
            }
            bauerZug = !bauerZug;
        }
        System.out.println("Turm gefangen!");
    }
}

class Position {
    public int x;
    public int y;

    public Position(int posx, int posy) {
        this.x = posx;
        this.y = posy;
    }
}
