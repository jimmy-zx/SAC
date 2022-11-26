package sac.model;

public record Point(int x, int y) implements Comparable<Point> {
    @Override
    public int compareTo(Point o) {
        if (this.x != o.x) {
            return this.x - o.x;
        }
        return this.y - o.y;
    }

    public Point add(Point rhs) {
        return new Point(x + rhs.x, y + rhs.y);
    }
}
