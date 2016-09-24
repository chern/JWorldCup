// import classes
import javax.swing.JFrame;

// class Engine contains main method to be run
public class Engine {
    // main method creates and interacts with instances of class SoccerGame
    public static void main(String[] args) {
       JFrame frame = new JFrame("Soccer Game");
       frame.setSize(500, 500);
       SoccerGame game = new SoccerGame();
       frame.add(game);
       frame.setVisible(true);
    }
}
