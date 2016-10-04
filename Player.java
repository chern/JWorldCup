// import classes
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Dimension;

// class Player represents a soccer player
public class Player extends JComponent {
    // instance fields
    private int xPos; // horizontal position
    private int yPos; // vertical position
    private int xVec; // magnitude of horizontal vector
    private int yVec; // magnitude of vertical vector
    private Color headColor; // color of head
    private Color jerseyColor; // color of jersey
    private String type; // type of player
    // default constructor
    public Player() {
        this.construct(0, 0, null, null, "player");
    }
    // customizable constructors
    public Player(int xP, int yP) {
        this.construct(xP, yP, null, null, "player");
    }
    public Player(int xP, int yP, Color jc) {
        this.construct(0, 0, jc, null, "player");
    }
    public Player(int xP, int yP, Color hc, Color jc, String t) {
        this.construct(0, 0, hc, jc, t);
    }
    // private mutator convenience method for setting instance fields
    private void construct(int xP, int yP, Color hc, Color jc, String t) {
        // choose random head and jersey colors if not already chosen
        if (hc == null)
            hc = new Color((int) (Math.random() * 75 + 175), (int) (Math.random() * 65 + 145), (int) (Math.random() * 55 + 135));
        if (jc == null)
            jc = new Color((int) (Math.random() * 210 + 30), (int) (Math.random() * 210 + 10), (int) (Math.random() * 210 + 20));
        this.xPos = xP;
        this.yPos = yP;
        this.xVec = 0;
        this.yVec = 0;
        this.type = t;
        this.headColor = hc;
        this.jerseyColor = jc;
        this.setBounds(0, 0, 500, 500);
        this.setBackground(null);
    }

    // overridden paintComponent method for player
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D.Double head = new Ellipse2D.Double(this.xPos, this.yPos, 18, 18);
        g2.setColor(this.jerseyColor);
        g2.fillRect(this.xPos + 4, this.yPos + 15, 11, 25);
        g2.setColor(this.headColor);
        g2.fill(head);
    }

    // mutator for method for choosing new direction and speed
    public Player randVector() {
        // chose random vector magnitude
        this.xVec = (int) (Math.random() * 6 - 3);
        this.yVec = (int) (Math.random() * 6 - 3);
        
        // chain
        return this;
    }
    
    public Player move() {
        this.movePlayer();
        
        // chain
        return this;
    }
    
    // mutator method for moving player
    public Player movePlayer() {
        // boundary colliders
        if (this.xPos > 480 || this.xPos < 0) this.xVec *= -1;
        if (this.yPos > 450 || this.yPos < 0) this.yVec *= -1;
        // move coordinates by vector
        this.xPos += this.xVec;
        this.yPos += this.yVec;
        
        // chain
        return this;
    }
}
