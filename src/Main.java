import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        Map m = new Map();
        //m.spawnBauern(8);
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
        for (int i = 0; i < 8; i++) {
            m.spawnBauer(i, i);
        }
        m.spawnTurm(0, 7);
        boolean bauerZug = true;
        int turmGezogen = 0;
        int bauernGezogen = 0;
        int anzDurchgaenge = 0;
        System.out.println("Turm gefangen: " + m.isTurmGefangen());
        while (!m.isTurmGefangen()) {
            if (bauerZug) {
                if (m.TurmisCatchAble()) {
                    Position p = m.getCatchPosition();
                    m.doMove(new Zug(p.posx, p.posy, m.getTurmPosition().posx, m.getTurmPosition().posy));
                } else {
                    Position p = m.getTurmPosition();
                    Position p2 = m.getBauerPositions().get(0);
                    if (p2.posx <= p.posx && p2.posy < p.posy) {
                        Position p3 = m.getBauerPositions().get(bauernGezogen++);
                        m.doMove(new Zug(p3.posx, p3.posy, p3.posx, p3.posy + 1));

                    } else {
                        Position p3 = m.getBauerPositions().get(bauernGezogen++);
                        m.doMove(new Zug(p3.posx, p3.posy, p3.posx + 1, p3.posy));
                    }
                    if (bauernGezogen + anzDurchgaenge == 7) {
                        bauernGezogen = 0;
                        anzDurchgaenge++;
                    }
                }
            } else {
                Position p = m.getTurmPosition();
                Position p2 = m.getBauerPositions().get(0);
                int modulo = 1;
                if (!(p2.posx <= p.posx && p2.posy < p.posy)) {
                    modulo = 0;
                }
                if (turmGezogen % 2 == modulo) {
                    m.doMove(new Zug(p.posx, p.posy, p.posx - 1, p.posy));
                } else {
                    m.doMove(new Zug(p.posx, p.posy, p.posx + 1, p.posy));
                }
                turmGezogen++;
            }
            bauerZug = !bauerZug;
        }
        System.out.println("Turm gefangen!");
    }
}

class Position {
    int posx;
    int posy;

    public Position(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }
}
