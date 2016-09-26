// import classes
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Stroke;
import java.awt.BasicStroke;
// class SoccerGame
public class SoccerGame extends JPanel {
    public int width;
    public int height;
    public final Color darkGreen = new Color(20, 170, 20);
    public final Color lightGreen = new Color(40, 190, 40);
    public boolean light;
    public SoccerGame(int w, int h, boolean l) {
        this.width = w;
        this.height = h;
        this.light = l;
        this.setBackground(darkGreen);
    }

    protected void paintComponent(Graphics g) {
        //*The Following is the drawing of the field*
        //  background
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //  field grass
        final int rects = 5;
        for (int i = 0; i < rects; i++) {
            if (light) g.setColor(this.lightGreen);
            else g.setColor(this.darkGreen);
            g.fillRect(0, i * this.height/rects, this.width, this.height/rects);
            light = !light;
        }
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
        //*drawing of field finished
    }
    
    public void readFile() {
    }
    public void saveFile() {
    }
}
