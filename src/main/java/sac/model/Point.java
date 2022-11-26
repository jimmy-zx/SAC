package sac.model;

public record Point(int x, int y) implements Comparable<Point> {
    @Override
    public int compareTo(Point o) {
        if (this.x != o.x) {
            return this.x - o.x;
        }
        return this.y - o.y;
    }

    /**
     * Move this point to a new place with `origin` as new origin.
     * @param origin
     * @return new position of this point
     */
    public Point offset(Point origin) {
        return new Point(x + origin.x, y + origin.y);
    }
}
