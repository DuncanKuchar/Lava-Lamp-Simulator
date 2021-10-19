
/**
 * Class "Display" makes multiple Bubble objects with numerous parameters 
 * defining how the bubble moves, shapes, and looks.
 * 
 * Each bubble ends up a Polygon ellipse consisting of 600 points
 *
 * @author Duncan Kuchar
 * @version 6/7/2021
 */
import java.awt.*;
import java.util.*;

public class Bubble {
    int bubbleNumPoints; // Each bubble has 600 points
    int bubbleMiddleX; // Center of X
    int bubbleMiddleY; // Center of Y
    int bubbleWidthHalf; // Width /2
    int bubbleHeightHalf; // Height /2
    int bubbleWidth; // Width
    int bubbleHeight; // height

    int bubbleTimer = 0; // For occilation
    int moveTimer = 0; // For movement

    int offSet = 0; // Offset from base y (525)

    int speed = 1;

    int total = 0;

    int h, k, a, b; // To make ellipse (h = center of x, k = center of y,
                    // a = width /2, b = height /2)

    boolean light; // if base bubble (the light)

    int[] bubbleX;
    int[] bubbleY;

    Color bColor = new Color(100, 20, 200); // origional color of bubbles

    ArrayList<Integer> bubbleXlist = new ArrayList<Integer>();
    ArrayList<Integer> bubbleYlist = new ArrayList<Integer>();

    public Bubble(int xL, int yL, int width, int height, int speed2, int bubbleMT, int bubbleBT, int oSet, int wAndH,
            boolean isLight, Color c) {
        bubbleMiddleX = xL;
        bubbleMiddleY = yL;
        bubbleWidthHalf = width / 2;
        bubbleHeightHalf = height / 2;
        bubbleWidth = width;
        bubbleHeight = height;
        offSet = oSet;
        total = wAndH;
        light = isLight;
        bColor = c;
        int bubbleMiddleShift = bubbleMiddleX - bubbleWidthHalf;

        bubbleTimer = bubbleBT;
        moveTimer = bubbleMT;
        speed = speed2;

        // Top of Ellipse, from left to right
        for (int i = 0; i < 300; i++) {
            int xVal = bubbleMiddleShift + i;
            bubbleXlist.add(xVal);

            h = bubbleMiddleX; // Center of X
            k = bubbleMiddleY; // Center of Y
            a = bubbleWidthHalf; // width /2
            b = bubbleHeightHalf; // height /2

            Point updatedY = updatePoints(h, k, a, b, xVal);

            bubbleYlist.add(updatedY.x); // Top y value for given X
        }
        // Bottom of Ellipse, from right to left
        for (int i = 299; i >= 0; i--) {
            int xVal = bubbleMiddleShift + i;
            bubbleXlist.add(xVal);

            h = bubbleMiddleX; // Center of X
            k = bubbleMiddleY; // Center of Y
            a = bubbleWidthHalf; // width /2
            b = bubbleHeightHalf; // height /2

            Point updatedY = updatePoints(h, k, a, b, xVal);

            bubbleYlist.add(updatedY.y); // Bottom y value for given X
        }
        bubbleNumPoints = bubbleYlist.size(); // 600
        // ArrayLists to Arrays
        bubbleX = Display.convertIntegers(bubbleXlist);
        bubbleY = Display.convertIntegers(bubbleYlist);
    }

    /**
     * Uses the equation for an ellipse to obtain the 2 y values for any given x
     * 
     * @param h1
     * @param k1
     * @param a1
     * @param b1
     * @param x
     * @return Point object "newYvalues" consisting of x (top y) and y (bottom y)
     */
    public Point updatePoints(int h1, int k1, int a1, int b1, int x) {
        double c = (x - h1) * (x - h1) * (b1 * b1);
        int y1 = k1 + (int) ((Math.sqrt((b1 * b1) - c / (a1 * a1)))); // Top y value
        int y2 = k1 + -1 * (int) ((Math.sqrt((b1 * b1) - c / (a1 * a1)))); // Bottom y value

        Point newYvalues = new Point(y1, y2);
        return newYvalues;
    }
}
