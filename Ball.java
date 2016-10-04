// import classes
import javax.swing.JComponent;

// class Ball represents a soccer player
public class Ball extends JComponent {
    // instance fields
    private int xPos;
    private int yPos;
    // default constructor
    public Ball() {
        this.xPos = 0;
        this.yPos = 0;
    }
    // customizable constructor
    public Ball(int xP, int yP) {
        this.xPos = xP;
        this.yPos = yP;
    }
}