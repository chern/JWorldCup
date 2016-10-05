// import classes
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
// class Engine contains main method to be run
public class WorldCup {
    // main method creates and interacts with an instance of class SoccerGame
    public static void main(String[] args) throws IOException {
       Game game = new Game("Soccer Game", 500, 500, 2, false);
       game.addPlayer().addPlayer().addPlayer().addPlayer().addPlayer().addPlayer().addPlayer();
       game.play();
       game.saveFile();
    }
    
    // convenience method for Java equivalent of JavaScript's `setTimeout` function
    public static void setTimeout(int msDelay, Runnable r) {
        final Timer t = new Timer(msDelay, null);
        t.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    r.run();
                    t.stop();
                }
            });
        t.start();
    }
}
