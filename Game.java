// import classes
import java.util.ArrayList;
// import graphics/window classes
import java.awt.Font;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
// import filesystem classes
import com.opencsv.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

// class Game contains the main game logic, but is also the JComponent for drawing a field
public class Game extends JComponent {
    // instance fields
    private int score;
    private int speed;
    private boolean play;
    private String filepath;
    private Ball ball;
    private int lastPlayer;
    private JFrame frame;
    private int width;
    private int height;
    public final Color darkGreen = new Color(20, 170, 20);
    public final Color lightGreen = new Color(40, 190, 40);
    private boolean light;
    private ArrayList<Player> players;
    private Ball b;
    // UI controls
    private JLabel scoreLabel;
    private JButton playButton;
    private JButton pauseButton;
    private JButton addPlayerButton;
    private JButton clearFieldButton;
    private JButton readFileButton;
    private JButton saveFileButton;
    private JSlider speedSlider;
    
    // customizable constructor
    public Game(String title, int w, int h, int sp, boolean l, String fp) {
        // initialize frame
        Game sg = this;
        this.frame = new JFrame(title);
        this.frame.setLayout(null);
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
        this.filepath = fp;
        this.players = new ArrayList<Player>();

        // add ball with timer to frame
        b = (new Ball(250, 250)).randVector();
        final Timer t = new Timer(sg.speed, null);
        t.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (sg.play) {
                        // move and repaint ball component
                        b.move().repaint();
                        // check collisions
                        Rectangle ballCollider = b.getCollider();
                        // check for player collisions in loop
                        for (int i = 0; i < sg.players.size(); i++) {
                            Player p = players.get(i);
                            if (p.getCollider().intersects(ballCollider)) {
                                int[] playerVector = p.getVector();
                                // by random chance, do either one of the following
                                if (Math.random() < 0.3) { // 30% chance
                                    // strike the ball
                                    System.out.println("P" + (i + 1) + ": strike");
                                    b.setVector(new int[] {(int) (playerVector[0] + 1), (int) (playerVector[1] + 1)});
                                } else { // 70% chance
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
                            sg.scoreLabel.setText("Score: " + score);
                            System.out.println("P" + (sg.lastPlayer + 1) + ": SCORE!");
                            sg.pause();
                            WorldCup.setTimeout(500, new Runnable() {
                                    public void run() {
                                        sg.play();
                                        b.setPosition(new int[] { w/2, h/2 }).randVector();
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
        this.frame.add(b);
        this.ball = b;
        
        // initialize score label (and add to frame)
        this.scoreLabel = new JLabel("Score: " + score);
        this.scoreLabel.setFont(new Font("Helvetica Bold", Font.PLAIN, 20));
        this.scoreLabel.setSize(100, 30);
        this.scoreLabel.setLocation(13, 10);
        this.frame.add(this.scoreLabel);
        
        // initialize buttons and ActionListeners (and add to frame)
        this.playButton = new JButton("Play");
        this.playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (sg.play) sg.pause();
                else sg.play();
            }
        });
        this.playButton.setSize(60, 50);
        this.playButton.setLocation(106, 2);
        this.frame.add(this.playButton);
        
        this.addPlayerButton = new JButton("Add Player");
        this.addPlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sg.addPlayer();
            }
        });
        this.addPlayerButton.setSize(100, 25);
        this.addPlayerButton.setLocation(162, 4);
        this.frame.add(this.addPlayerButton);
        
        this.clearFieldButton = new JButton("Clear Field");
        this.clearFieldButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sg.clear();
            }
        });
        this.clearFieldButton.setSize(100, 25);
        this.clearFieldButton.setLocation(162, 26);
        this.frame.add(this.clearFieldButton);
        
        this.readFileButton = new JButton("Read File");
        this.readFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sg.readFile();
            }
        });
        this.readFileButton.setSize(100, 25);
        this.readFileButton.setLocation(254, 4);
        this.frame.add(this.readFileButton);
        
        this.saveFileButton = new JButton("Save File");
        this.saveFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sg.saveFile();
            }
        });
        this.saveFileButton.setSize(100, 25);
        this.saveFileButton.setLocation(254, 26);
        this.frame.add(this.saveFileButton);

        // initialize and add speed slider
        JLabel speedLabel = new JLabel("Speed: ");
        speedLabel.setFont(new Font("Helvetica Bold", Font.PLAIN, 14));
        speedLabel.setSize(100, 30);
        speedLabel.setLocation(355, 10);
        this.frame.add(speedLabel);
        this.speedSlider = new JSlider(1, 100, this.speed);
        this.speedSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sg.setSpeed(sg.speedSlider.getValue());
            }
        });
        this.speedSlider.setSize(100, 30);
        this.speedSlider.setLocation(400, 10);
        this.frame.add(this.speedSlider);
        
        // add field to frame
        this.setBounds(0, 0, w, h);
        this.frame.add(this);
        
        // show frame
        this.frame.setVisible(true);
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
        // randomize position and vector
        return this.addPlayer((int) (Math.random() * 480 + 10), (int) (Math.random() * 480 + 10), null, null);
    }
    public Game addPlayer(Integer xPos, Integer yPos, Integer xVec, Integer yVec) {
        // remove background
        Game sg = this;
        this.frame.remove(this);

        // add player to frame at random position
        Player p = new Player(xPos, yPos);
        this.frame.add(p);
        this.players.add(p);
        this.frame.setVisible(true);

        // change player direction/speed
        if (xVec == null || yVec == null) p.randVector();
        else p.setVector(new int[] { xVec, yVec });

        // add timer for moving player
        Timer t1 = new Timer(sg.speed, null);
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

                        int[] ballPos = sg.ball.getPosition();
                        int[] playerPos = p.getPosition();
                        int[] playerVec = p.getVector();
                        if (ballPos[0] < playerPos[0])
                            p.setVector(new int[] { Math.abs(playerVec[0]) * -1, playerVec[1] });
                        if (ballPos[1] < playerPos[1])
                            p.setVector(new int[] { playerVec[0], Math.abs(playerVec[1]) * -1 });
                    }
                }
            });
        t2.start();

        // keep track of timers
        p.setTimers(t1, t2);

        // re-add background
        this.frame.add(this);
        this.frame.setVisible(true);

        // display new players
        this.repaint();
        
        // chain
        return this;
    }

    // (chainable) mutator method for playing (starting or resuming) game
    public Game play() {
        this.play = true;
        this.playButton.setText("Pause");
        // chain
        return this;
    }

    // (chainable) mutator method for pausing (stopping or freezing) game
    public Game pause() {
        this.play = false;
        this.playButton.setText("Play");
        // chain
        return this;
    }

    // (chainable) mutator method for game speed instance field
    public Game setSpeed(int s) {
        this.speed = s;
        this.speedSlider.setValue(s);
        // chain
        return this;
    }

    // (chainable) mutator method for clearing players from field
    public Game clear() {
        // if playing, pause temporarily
        boolean wasPlaying = this.play;
        if (this.play) this.pause();
        // loop through players
        for (Player p : this.players) {
            // stop and remove player timers
            for (Timer t : p.getTimers())
                t.stop();
            p.setTimers(null, null);
            // remove player from frame
            this.frame.remove(p);
        }
        // remove all players from memory
        this.players.clear();
        // if game was playing before, unpause
        if (wasPlaying) this.play();
        // clear players from screen
        this.repaint();
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

    // method for reading data file and loading data into game
    public void readFile() {
        this.clear();
        try {
            CSVReader reader = new CSVReader(new FileReader(this.filepath), ',', '"');
            String[] nextLine;
            while((nextLine = reader.readNext()) != null) {
                if (nextLine[0].equals("Player")) {
                     // parse player data and add players to game
                    this.addPlayer(Integer.parseInt(nextLine[1]), Integer.parseInt(nextLine[2]), Integer.parseInt(nextLine[3]), Integer.parseInt(nextLine[4]));
                } else if (nextLine[0].equals("Score")) {
                    // parse and set game score
                    this.score = Integer.parseInt(nextLine[1]);
                } else if (nextLine[0].equals("Ball")) {
                    // parse and set ball vector and position
                    this.ball.setVector(new int[] { Integer.parseInt(nextLine[3]), Integer.parseInt(nextLine[4]) });
                    this.ball.setPosition(new int[] { Integer.parseInt(nextLine[1]), Integer.parseInt(nextLine[2]) });
                } else if (nextLine[0].equals("Speed")) {
                    // parse and set game speed
                    this.speed = Integer.parseInt(nextLine[1]);        
                    this.speedSlider.setValue(Integer.parseInt(nextLine[1]));
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File could not be read - File not found: " + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("File could not be read - IO Exception: " + ioe.getMessage());
        }
    }

    // method for saving game data to CSV file
    public void saveFile() {
        FileWriter writer;
        try {
            writer = new FileWriter(this.filepath);
            writer.write("");
            int[] ballPosition = this.ball.getPosition();
            int[] ballVector = this.ball.getVector();
            writer.append("\"Score\"," + "\"" + this.score + "\"\n");
            writer.append("\"Speed\"," + "\"" + this.speed + "\"\n");
            writer.append("\"Ball\"," + "\"" + ballPosition[0] + "\"," + "\"" + ballPosition[1] + "\"," + "\"" + ballVector[0] +"\"," + "\"" + ballVector[1] + "\"\n");
            for (Player p : this.players) {
                int[] positions = p.getPosition();
                int[] vectors = p.getVector();
                writer.append("\"Player\"," +"\"" + positions[0] +"\"," +"\"" + positions[1] + "\"," + "\"" + vectors[0] +"\","  +"\"" + vectors[1] +"\"\n");
            }
            writer.close();
        } catch (IOException ioe) {
            System.out.println("File could not be saved - IO Exception: " + ioe.getMessage());
        }
    }
}
