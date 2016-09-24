// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

// class SoccerGame
public class SoccerGame extends JPanel {
    public Color darkGreen;
    public Color lightGreen;
    public SoccerGame() {
        super();
        darkGreen = new Color(20, 170, 20);
        lightGreen = new Color(40, 190, 40);
        this.setBackground(darkGreen);
        // draw field grass
        for (int i = 0; i < 10; i++) {

        }
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(this.lightGreen);  
        g.fillRect(0, 0, 0, 0);
        super.paintComponent(g);
    }
}
