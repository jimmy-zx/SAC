package sac.model;

import java.util.Objects;

/**
 * A two-dimension point.
 * @param x The first coordinate.
 * @param y The second coordinate.
 */
public record Point(int x, int y) implements Comparable<Point> {
    /**
     * Perform x-major comparison.
     *
     * @param o the object to be compared.
     * @return Returns whether the x coordinate of this point is larger.
     * If they are the same, then compare the y coordinate.
     */
    @Override
    public int compareTo(Point o) {
//        if (this.x != o.x) {
//            return this.x - o.x;
//        }
//        return this.y - o.y;
        if (o.x == this.x && o.y == this.y) return 0;
        if (this.x > o.x || this.x == o.x && this.y > o.y) return 1;
        return -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Move this point to a new place with `origin` as new origin.
     *
     * @param origin The origin point.
     * @return The new position of this point.
     */
    public Point offset(Point origin) {
        return new Point(x + origin.x, y + origin.y);
    }

    /**
     * Move this point to a new place with (x, y) as new origin.
     *
     * @param x The x coordinate of the origin.
     * @param y The y coordinate of the origin.
     * @return the new position of this point.
     */
    public Point offset(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    /**
     * Calculate the difference between two points.
     *
     * @param other The point to be subtracted.
     * @return The difference between two points.
     */
    public Point diff(Point other) {
        return new Point(x - other.x, y - other.y);
    }
}
