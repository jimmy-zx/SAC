package sac.model;

import sac.utils.Utility;

import java.util.*;

/**
 * An immutable representation of a Tetris Tetromino.
 * Each piece is defined by the points that make up its body.
 */
public class Piece {
    /**
     * Based on https://tetris.wiki/Tetromino
     */
    public static enum PieceType {
        O("0 0 0 1 1 0 1 1"),
        Z("0 1 1 0 1 1 2 0"),
        S("0 0 1 0 1 1 2 1"),
        L("0 0 1 0 2 0 2 1"),
        J("0 0 0 1 1 0 2 0"),
        T("0 0 1 0 1 1 2 0"),
        I("0 0 1 0 2 0 3 0"),
        Other;

        private String body;
        private PieceType(String body) {
            this.body = body;
        }
        private PieceType() {}
        public String getBodyString() {
            return body;
        }
        public void setBodyString(String body) {
            this.body = body;
        }
    }
    public final PieceType type;
    public final int width, height;

    /**
     * Sorted representation of the points that make up the piece.
     */
    public final ArrayList<Point> body;

    /**
     * The lowest y value for each column.
     */
    public final ArrayList<Integer> lowestYVals;

    private Piece next;

    private Piece(PieceType type, ArrayList<Point> body) {
        this.body = new ArrayList<>(body);
        Collections.sort(body);

        this.type = type;

        width = body.get(body.size() - 1).x() + 1;

        Integer[] lowestYValsArray = new Integer[width];

        int maxHeight = 0;
        for (Point point : body) {
            maxHeight = Math.max(maxHeight, point.y() + 1);
        }
        height = maxHeight;

        Arrays.fill(lowestYValsArray, height);
        for (Point point : body) {
            lowestYValsArray[point.x()] = Math.min(lowestYValsArray[point.x()], point.y());
        }
        lowestYVals = new ArrayList<>(Arrays.asList(lowestYValsArray));
    }

    public static Piece generate(PieceType type) {
        return makeFastRotations(new Piece(type, Utility.parsePointsString(type.getBodyString())));
    }

    /**
     * Returns a new piece that is 90 degrees clockwise
     * rotated from `origin` piece.
     *
     * @param origin: the origin piece
     * @return the next rotation of the given piece
     */
    private static Piece computeNextRotation(Piece origin) {
        ArrayList<Point> points = new ArrayList<>();
        for(Point point : origin.body) {
            points.add(new Point(point.y(), - point.x() + origin.width - 1));
        }
        return new Piece(origin.type, points);
    }

    private static Piece makeFastRotations(Piece root) {
        Piece cur = root;
        Piece next = computeNextRotation(cur);
        while (!root.equals(next)) {
            cur.next = next;
            cur = next;
            next = computeNextRotation(cur);
        }
        cur.next = root;
        return root;
    }

    /**
     * Counter-clockwise
     * @return
     */
    public Piece rotateLeft() {
        return this.next.next.next;
    }

    /**
     * clockwise
     * @return
     */
    public Piece rotateRight() {
        return this.next;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        boolean[][] grid = new boolean[height][width];
        for (Point point : body) {
            grid[point.y()][point.x()] = true;
        }
        for (int y = height - 1; y >= 0 ; y--) {
            for (int x = 0; x < width; x++) {

                if (grid[y][x]) {
                    result.append(type.name());
                } else {
                    result.append(" ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Piece other) {
            return this.body.equals(other.body);
        }
        return false;
    }
}