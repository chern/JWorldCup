// import classes
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

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
        this.construct(0, 0, null, null, "player", 0, 0);
    }
    // customizable constructors
    public Player(int xP, int yP) {
        this.construct(xP, yP, null, null, "player", 0, 0);
    }

    public Player(int xP, int yP, Color jc) {
        this.construct(0, 0, jc, null, "player", 0, 0);
    }

    public Player(int xP, int yP, Color hc, Color jc, String t) {
        this.construct(0, 0, hc, jc, t, 0, 0);
    }
    public Player(int xP, int yP, int xV, int yV) {
        this.construct(xP, yP, null, null, "player", xV, yV);
    }
    // private mutator convenience method for setting instance fields
    private void construct(int xP, int yP, Color hc, Color jc, String t, int xV, int yV) {
        // choose random head and jersey colors if not provided
        if (hc == null)
            hc = new Color((int) (Math.random() * 75 + 175), (int) (Math.random() * 65 + 145), (int) (Math.random() * 55 + 135));
        if (jc == null)
            jc = new Color((int) (Math.random() * 210 + 45), (int) (Math.random() * 210 + 30), (int) (Math.random() * 210 + 40));
        this.xPos = xP;
        this.yPos = yP;
        this.xVec = xV;
        this.yVec = yV;
        this.type = t;
        this.headColor = hc;
        this.jerseyColor = jc;
        this.setBounds(0, 0, 500, 500);
        this.setBackground(null);
    }
    
    
    // overridden paintComponent method for JComponent
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D.Double head = new Ellipse2D.Double(this.xPos, this.yPos, 18, 18);
        g2.setColor(this.jerseyColor);
        g2.fillRect(this.xPos + 4, this.yPos + 15, 11, 25);
        g2.setColor(this.headColor);
        g2.fill(head);
    }

    // (chainable) mutator for method for generating new random direction and speed
    public Player randVector() {
        // generate new random vector magnitudes
        this.xVec = (int) (Math.random() * 6 - 3);
        this.yVec = (int) (Math.random() * 6 - 3);
        // chain
        return this;
    }

    // (chainable) overloaded method for moving JComponent
    public Player move() {
        // call normal move player method
        this.movePlayer();
        // chain
        return this;
    }

    // (chainable) mutator method for moving player
    public Player movePlayer() {
        // boundary colliders
        if (this.xPos >= 480) {
            this.xVec *= -1;
            this.xPos = 479;
        } else if (this.xPos <= 0) {
            this.xVec *= -1;
            this.xPos = 1;
        }
        if (this.yPos >= 460) {
            this.yVec *= -1;
            this.yPos = 459;
        } else if (this.yPos <= 0) {
            this.yVec *= -1;
            this.yPos = 1;
        }

        // move coordinates by vector
        this.xPos += this.xVec;
        this.yPos += this.yVec;

        // chain
        return this;
    }

    // accessor method for rectangle representing the bounds of the player
    public Rectangle getCollider() {
        return new Rectangle(this.xPos, this.yPos, 18, 32);
    }

    // accessor method for array containing player vector
    public int[] getVector() {
        return new int[] {this.xVec, this.yVec};
    }
    public int[] getPosition() {
        return new int[] {this.xPos, this.yPos};
    }
}
