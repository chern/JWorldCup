// import classes
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
// class Engine contains main method to be run
public class Engine {
    // main method creates and interacts with instances of class SoccerGame
    public static void main(String[] args) {
       JFrame frame = new JFrame("Soccer Game");
       frame.setSize(500, 520);
       SoccerGame game = new SoccerGame(500, 500, true);
       frame.add(game);
       frame.setVisible(true);
       class KeyListen implements KeyListener {
           public void keyPressed(KeyEvent e) {
            }
           public void keyTyped(KeyEvent e) {
            } 
           public void keyReleased(KeyEvent e) {
            } 
        }
    }
}
