import java.util.*;

/**
 * Takes either an array or arrayList of x and y values and makes an arrayList
 * of points Used when making lamp pieces
 *
 * @author Duncan Kuchar
 * @version 6/4/2021
 */
public class LampPiece {
    int[] xPoints, yPoints;
    ArrayList<Integer> xPointsList = new ArrayList<Integer>();
    ArrayList<Integer> yPointsList = new ArrayList<Integer>();
    ArrayList<Point> points = new ArrayList<Point>();

    /**
     * Takes Arrays of x values and y values and combines them to make an ArrayList
     * of point objects
     * 
     * @param x
     * @param y
     */
    public LampPiece(int[] x, int[] y) {
        xPoints = x;
        yPoints = y;

        for (int i = 0; i < xPoints.length; i++) {
            points.add(new Point(xPoints[i], yPoints[i]));
        }
    }

    /**
     * Takes ArrayLists of x values and y values and combines them to make an
     * ArrayList of point objects
     * 
     * (Not currently used, but implemented for future use)
     * 
     * @param x
     * @param y
     */
    public LampPiece(ArrayList x, ArrayList y) {
        ArrayList<Integer> xPoints = x;
        ArrayList<Integer> yPoints = y;

        for (int i = 0; i < xPoints.size(); i++) {
            points.add(new Point(xPoints.get(i), yPoints.get(i)));
        }
    }
}
