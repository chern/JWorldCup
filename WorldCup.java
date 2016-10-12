// import classes
import javax.swing.Timer;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// class Engine contains main method to be run
public class WorldCup {
    // main method creates and interacts with an instance of class SoccerGame
    public static void main(String[] args) {
        Game game = (new Game("Soccer Game", 500, 500, 2, false, "savedData.csv")).pause();
    }

    // convenience method for Java equivalent of JavaScript's `setTimeout` function
    public static void setTimeout(int msDelay, Runnable r) {
        final Timer t = new Timer(msDelay, null);
        t.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    t.stop();
                    r.run();
                }
            });
        t.start();
    }
}
