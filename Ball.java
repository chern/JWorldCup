// import classes
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

// class Ball represents a soccer player
public class Ball extends JComponent {
    // instance fields
    private int xPos; // horizontal position
    private int yPos; // vertical position
    private int xVec; // magnitude of horizontal vector
    private int yVec; // magnitude of vertical vector
    private int radius;
    // default constructor
    public Ball() {
        this.construct(0, 0, 0, 0, 12);
    }
    // customizable constructor
    public Ball(int xP, int yP) {
        this.construct(xP, yP, 0, 0, 12);
    }
    // private mutator convenience method for setting instance fields
    private void construct(int xP, int yP, int xV, int yV, int r) {
        this.xPos = xP;
        this.yPos = yP;
        this.xVec = xV;
        this.yVec = yV;
        this.radius = r;
        this.setBounds(0, 0, 500, 500);
        this.setBackground(null);
    }
    
    // overridden paintComponent method for JComponent
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Ellipse2D.Double ball = new Ellipse2D.Double(this.xPos, this.yPos, this.radius * 2, this.radius * 2);
        g2.setColor(Color.WHITE);
        g2.fill(ball);
    }
    
    // (chainable) mutator for method for generating random new direction and speed
    public Ball randVector() {
        // generate new random vector magnitudes
        this.xVec = (int) (Math.random() * 8 - 4);
        this.yVec = (int) (Math.random() * 8 - 4);
        // chain
        return this;
    }
    // (chainable) mutator for method for choosing new direction and speed
    public Ball setVector(int[] v) {
        // set new vector magnitudes
        this.xVec = v[0];
        this.yVec = v[1];
        // chain
        return this;
    }
    
    // (chainable) overloaded method for moving JComponent
    public Ball move() {
        // call normal move ball method
        this.moveBall();
        // chain
        return this;
    }
    // (chainable) mutator for method for choosing new position
    public Ball setPosition(int[] p) {
        // set new position coordinates
        this.xPos = p[0];
        this.yPos = p[1];
        // chain
        return this;
    }
    
    // (chainable) mutator method for moving ball
    public Ball moveBall() {
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
    
    // accessor method for rectangle representing the bounds of the ball
    public Rectangle getCollider() {
        int diameter = this.radius * 2;
        return new Rectangle(this.xPos, this.yPos, diameter, diameter);
    }
    
    // accessor method for array containing ball vector
    public int[] getVector() {
        return new int[] {this.xVec, this.yVec};
    }
    // accessor method for array containing ball position
    public int[] getPosition() {
        return new int[] {this.xPos, this.yPos};
    }
}