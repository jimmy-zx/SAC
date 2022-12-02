package sac.utils;

import sac.model.Point;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Utility {
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
