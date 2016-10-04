// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JComponent;
import java.awt.Stroke;
import java.awt.BasicStroke;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
// class SoccerGame
public class SoccerGame extends JComponent {
    // instance fields
    private JFrame frame;
    private int width;
    private int height;
    public final Color darkGreen = new Color(20, 170, 20);
    public final Color lightGreen = new Color(40, 190, 40);
    private boolean light;
    private ArrayList<Player> playerList;
    // constructor
    public SoccerGame(String title, int w, int h, boolean l) {
        // initialize frame
        this.frame = new JFrame(title);
        this.frame.setSize(w, h + 20);
        this.frame.setBackground(this.darkGreen);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        // initialize instance fields
        this.width = w;
        this.height = h;
        this.light = l;
        this.playerList = new ArrayList<Player>();
        // add field to frame
        frame.add(this);
        frame.setVisible(true);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //  draw field grass
        final int rects = 5;
        boolean temp = light;
        for (int i = 0; i < rects; i++) {
            if (light) g.setColor(this.lightGreen);
            else g.setColor(this.darkGreen);
            g.fillRect(0, i * this.height/rects, this.width, this.height/rects);
            light = !light;
        }
        light = temp;
        // draw goal frame
        g2.setColor(Color.WHITE);
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(5));
        int goalWidth = this.width/2;
        int goalHeight = this.height/5;
        int goalX = this.width/4;
        int goalY = this.height - goalHeight;
        g2.drawRect(goalX, goalY, goalWidth, goalHeight);
        // draw goal lines
        g2.setStroke(new BasicStroke((float) 1.5));
        final int lines = 11;
        for (int i = 0; i < lines; i++)
            g2.drawLine(goalX + i * goalWidth/lines, goalY, goalX + goalWidth, goalY + goalHeight - i * goalHeight/lines);
        for (int i = 0; i < lines; i++)
            g2.drawLine(goalX, goalY + i * goalHeight/lines, goalX + goalWidth - i * goalWidth/lines, goalY + goalHeight);
        for (int i = 0; i < lines; i++) {
            int x1 = goalX;
            int x2 = goalX + i * goalWidth/lines;
            int y1 = goalY + i * goalHeight/lines;
            int y2 = goalY;
            g2.drawLine(x1, y1, x2, y2);
        }
        for (int i = 0; i < lines; i++) {
            int x1 = goalX + i * goalWidth/lines;
            int x2 = goalX + goalWidth;
            int y1 = goalY + goalHeight;
            int y2 = goalY + i * goalHeight/lines;
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public SoccerGame addPlayer() {
        // remove background
        this.frame.remove(this);
        
        // add player to frame
        Player p = new Player(50, 50);
        this.frame.add(p);
        playerList.add(p);
        this.frame.setVisible(true);
        
        p.randVector();
        Timer t1 = new Timer(10, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        p.move();
                        p.repaint();
                    }
                });
        t1.start();
        
        Timer t2 = new Timer((int) (Math.random() * 2000 + 800), new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        p.randVector();
                    }
                });
        t2.start();
        
        // re-add background
        this.frame.add(this);
        this.frame.setVisible(true);
        System.out.println(playerList.size());
        return this;
    }
    
    public void readFile() {

    }

    public void saveFile() {

    }
}
