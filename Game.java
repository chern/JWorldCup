// import classes
import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// class Game contains the main game logic, but is also the JComponent for drawing a field
public class Game extends JComponent {
    // instance fields
    private int score;
    private int speed;
    private boolean play;
    private int lastPlayer;
    private JFrame frame;
    private int width;
    private int height;
    public final Color darkGreen = new Color(20, 170, 20);
    public final Color lightGreen = new Color(40, 190, 40);
    private boolean light;
    private ArrayList<Player> players;
    // customizable constructor
    public Game(String title, int w, int h, int sp, boolean l) {
        // initialize frame
        Game sg = this;
        this.frame = new JFrame(title);
        this.frame.setSize(w, h + 20);
        this.frame.setBackground(this.darkGreen);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setResizable(false);

        // initialize instance fields
        this.play = false;
        this.score = 0;
        this.speed = sp;
        this.width = w;
        this.height = h;
        this.light = l;
        this.players = new ArrayList<Player>();

        // add ball with timer to frame
        Ball b = (new Ball(250, 250)).randVector();
        final Timer t = new Timer(sg.speed, null);
        t.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (sg.play) {
                        // move and repaint ball component
                        b.move().repaint();

                        // check collisions
                        Rectangle ballCollider = b.getCollider();
                        // check for player collisions in loop
                        for (int i = 0; i < players.size(); i++) {
                            Player p = players.get(i);
                            if (p.getCollider().intersects(ballCollider)) {
                                int[] playerVector = p.getVector();
                                // by random chance, do either one of the following
                                if (Math.random() < 0.3) {
                                    // strike the ball
                                    System.out.println("P" + (i + 1) + ": strike");
                                    b.setVector(new int[] {(int) (playerVector[0] + 1), (int) (playerVector[1] + 1)});
                                } else {
                                    // dribble the ball
                                    System.out.println("P" + (i + 1) + ": dribble");
                                    b.setVector(new int[] {(int) (playerVector[0] * 1.25), (int) (playerVector[1] * 1.25)});
                                }
                                sg.lastPlayer = i;
                            }
                        }
                        // check for goal collision
                        if (sg.getGoalCollider().intersects(ballCollider)) {
                            sg.score++;
                            System.out.println("P" + (sg.lastPlayer + 1) + ": SCORE!");
                            sg.pause();
                            WorldCup.setTimeout(1000, new Runnable() {
                                    public void run() {
                                        sg.play();
                                        b.setPosition(new int[] {250, 250}).randVector();
                                    }
                                });
                        }

                        // reset timer delay
                        if (t.getDelay() != sg.speed)
                            t.setDelay(speed);
                    }
                }
            });
        t.start();
        frame.add(b);

        // add field to frame
        frame.add(this);
        frame.setVisible(true);
    }

    // overridden paintComponent method for JComponent (for field and goal)
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

    // (chainable) mutator method for adding new players to the game
    public Game addPlayer() {
        // remove background
        Game sg = this;
        this.frame.remove(this);

        // add player to frame at random position
        Player p = new Player((int) (Math.random() * 480 + 10), (int) (Math.random() * 480 + 10));
        this.frame.add(p);
        players.add(p);
        this.frame.setVisible(true);

        // change player direction/speed randomly
        p.randVector();

        // add timer for moving player
        final Timer t1 = new Timer(sg.speed, null);
        t1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (sg.play) {
                        // move player component
                        p.move();
                        // repaint player component
                        p.repaint();
                        // reset timer delay
                        if (t1.getDelay() != sg.speed)
                            t1.setDelay(speed);
                    }
                }
            });
        t1.start();

        // add timer for changing player direction/speed
        Timer t2 = new Timer((int) (Math.random() * 2000 + 800), null);
        t2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (sg.play) {
                        // generate new direction/speed for player
                        p.randVector();
                    }
                }
            });
        t2.start();

        // re-add background
        this.frame.add(this);
        this.frame.setVisible(true);

        // chain
        return this;
    }

    // (chainable) mutator method for playing (starting or resuming) game
    public Game play() {
        this.play = true;
        // chain
        return this;
    }

    // (chainable) mutator method for pausing (stopping or freezing) game
    public Game pause() {
        this.play = false;
        // chain
        return this;
    }
    
    // (chainable) mutator method for game speed instance field
    public Game pause(int s) {
        this.speed = s;
        // chain
        return this;
    }

    // accessor method for rectangle representing the bounds of the goal
    public Rectangle getGoalCollider() {
        int goalWidth = this.width/2;
        int goalHeight = this.height/5;
        int goalX = this.width/4;
        int goalY = this.height - goalHeight;
        return new Rectangle(goalX + 10, goalY + 10, goalWidth - 20, goalHeight - 10);
    }

    public void readFile() {

    }

    public void saveFile() {
        for (Player p : players) {

        }
    }
}
