import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 * Displays all graphics within the window (updated every timer activation)
 * 
 * @author Duncan
 * @version 6/7/2021
 */
public class Display extends JPanel {
    Font myFont;
    int x = 0;
    int y = 0;
    int w, h;

    Color c1 = new Color(100, 20, 200); // Starting color of bubbles

    int lampPointsBase, lampPointsMid, lampPointsTop, bubbleNumPoints;
    int lampPointsOutLeft, lampPointsOutRight;

    ArrayList<Integer> lampBaseX = new ArrayList<Integer>();
    ArrayList<Integer> lampBaseY = new ArrayList<Integer>();
    ArrayList<Integer> lampMidX = new ArrayList<Integer>();
    ArrayList<Integer> lampMidY = new ArrayList<Integer>();
    ArrayList<Integer> lampTopX = new ArrayList<Integer>();
    ArrayList<Integer> lampTopY = new ArrayList<Integer>();

    // Base of Lamp
    int[] arrayXbase = { 300, 650, 550, 400 };
    int[] arrayYbase = { 675, 675, 575, 575 };
    // Top of base of lamp
    int[] arrayXmid = { 325, 625, 550, 400 };
    int[] arrayYmid = { 475, 475, 575, 575 };
    // Top of lamp
    int[] arrayXtop = { 450, 500, 530, 420 };
    int[] arrayYtop = { 10, 10, 110, 110 };
    // Main lamp
    int[] lampX = { 620, 330, 425, 525 };
    int[] lampY = { 475, 475, 110, 110 };
    // Used to prevent bubbles showing outside of lamp
    int[] outLeftX = { 330, 425, 420, 450, 300, 300, 315, 400, 400 };
    int[] outLeftY = { 475, 110, 110, 10, 10, 475, 655, 575, 475 };
    int[] outRightX = { 620, 525, 530, 500, 650, 650, 635, 550, 550 };
    int[] outRightY = { 475, 110, 110, 10, 10, 475, 655, 575, 475 };
    // Center x is 475, Center y is 525
    // Parameters below:
    // (x, y, width, height, speed, moveTimer, bubbleTimer, y offset (y - 525),
    // total (x + y), light, color)
    Bubble b1 = new Bubble(475, 525, 70, 70, 1, 0, 0, 0, 140, false, c1);
    Bubble b2 = new Bubble(450, 525, 50, 50, 2, 0, 0, 0, 100, false, c1);
    Bubble b3 = new Bubble(500, 530, 50, 50, 3, 0, 0, 5, 100, false, c1);
    Bubble b4 = new Bubble(425, 600, 40, 40, 2, 0, 0, 75, 80, false, c1);
    Bubble b5 = new Bubble(525, 600, 60, 60, 1, 0, 0, 75, 120, false, c1);
    // Bubble at the base (lamp)
    Bubble lampBubble = new Bubble(475, 485, 250, 10, 0, 0, 0, 0, 220, true, c1);
    // To store all bubble objects
    ArrayList<Bubble> bubbles = new ArrayList<Bubble>();

    /**
     * CONSTRUCTOR
     */
    public Display(int w, int h) {

        this.myFont = new Font("Verdana", Font.BOLD, 16); // font for display
        this.w = w;
        this.h = h;
        setSize(new Dimension(w, h)); // size of display

        // BASE
        LampPiece lampBase = new LampPiece(arrayXbase, arrayYbase);
        // MID
        LampPiece lampMid = new LampPiece(arrayXmid, arrayYmid);
        // TOP
        LampPiece lampTop = new LampPiece(arrayXtop, arrayYtop);
        // OUTER LEFT
        LampPiece outLeft = new LampPiece(outLeftX, outLeftY);
        // OUTER RIGHT
        LampPiece outRight = new LampPiece(outRightX, outRightY);
        // num of points
        lampPointsBase = lampBase.points.size();
        lampPointsMid = lampMid.points.size();
        lampPointsTop = lampTop.points.size();
        lampPointsOutLeft = outLeft.points.size();
        lampPointsOutRight = outRight.points.size();

        // Bubbles
        bubbles.add(b1);
        bubbles.add(b2);
        bubbles.add(b3);
        bubbles.add(b4);
        bubbles.add(b5);
        bubbles.add(lampBubble);
    }

    /**
     * Draw text, lamp, and bubbles; called in Window class by a timer calling
     * repaint()
     * 
     * @param g the graphics object, automatically sent when repaint() is called
     */
    public void paintComponent(Graphics g) {
        // Main Lamp
        Polygon lamp = new Polygon(lampX, lampY, lampX.length);
        // makes the drawings cleaner:
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int lineThickness = 1;
        g2.setStroke(new BasicStroke(lineThickness));

        super.paintComponent(g); // clear canvas
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, w, h);

        // LAMP
        g.setColor(new Color(100, 100, 100, 50));
        g.fillPolygon(lamp);

        // BUBBLES
        for (Bubble b : bubbles) {
            g.setColor(b.bColor);
            g.fillPolygon(b.bubbleX, b.bubbleY, b.bubbleNumPoints);
        }

        // OUTER
        g.setColor(Color.BLACK);
        g.fillPolygon(outLeftX, outLeftY, lampPointsOutLeft);
        g.fillPolygon(outRightX, outRightY, lampPointsOutRight);

        g2.setStroke(new BasicStroke(5));
        // LAMP BORDER
        g.setColor(new Color(55, 55, 55));
        g.drawLine(620, 475, 525, 110); // Right
        g.drawLine(330, 475, 425, 110); // Left
        // BASE
        g.setColor(new Color(110, 110, 110));
        g.fillPolygon(arrayXbase, arrayYbase, lampPointsBase);
        // MID
        g.setColor(new Color(100, 100, 100));
        g.fillPolygon(arrayXmid, arrayYmid, lampPointsMid);
        // TOP
        g.fillPolygon(arrayXtop, arrayYtop, lampPointsTop);
    }

    /**
     * Converts an Integer ArrayList to an int Array
     * 
     * @param integers
     * @return Integer array "ret"
     */
    public static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().intValue();
        }
        return ret;
    }

    /**
     * Comverts an int Array to an Integer ArrayList (Not currently used, but for
     * future purposes)
     * 
     * @param numbers
     * @return Integer ArrayList "nums"
     */
    public static ArrayList printArray(int[] numbers) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 0; i < numbers.length; i++) {
            nums.add(numbers[i]);
        }
        return nums;
    }
}
