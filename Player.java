// import classes
import javax.swing.JComponent;

// class Player represents a soccer player
public class Player extends JComponent {
    // instance fields
    private int xPos;
    private int yPos;
    private String type;
    // default constructor
    public Player() {
        this.xPos = 0;
        this.yPos = 0;
        this.type = "player";
    }
    // customizable constructor
    public Player(int xP, int yP, String type) {
        this.xPos = xP;
        this.yPos = yP;
        this.type = type;
    }
    
    public void movePlayer() {
        // calculate random direction, move that way for random amount of time
    }
}
