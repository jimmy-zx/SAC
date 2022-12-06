package sac.utils;

import sac.model.Point;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Some useful helper functions.
 */
public class Utility {
    /**
     * Convert to a space-seperated string of coordinates to points.
     * <p>
     * Format: x0 y0 x1 y1 ...
     * @param string A space-seperated string of coordinates.
     * @return A list of points.
     */
    public static ArrayList<Point> parsePointsString(String string) {
        ArrayList<Point> points = new ArrayList<>();
        StringTokenizer tok = new StringTokenizer(string);
        while(tok.hasMoreTokens()) {
            int x = Integer.parseInt(tok.nextToken());
            int y = Integer.parseInt(tok.nextToken());

            points.add(new Point(x, y));
        }
        return points;
    }
}
