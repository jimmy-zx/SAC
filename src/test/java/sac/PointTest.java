package sac;

import sac.model.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    @Test
    void testPoint() {
        Point point = new Point(1, 2);
        assertEquals(1, point.x());
        assertEquals(2, point.y());
    }

    @Test
    void testCompare() {
        Point p11 = new Point(1, 1);
        Point dp11 = new Point(1, 1);
        Point p12 = new Point(1, 2);
        Point p21 = new Point(2, 1);
        Point p22 = new Point(2, 2);
        Point np11 = new Point(-1, -1);
        assertEquals(p11, dp11);
        assertTrue(p11.compareTo(p12) < 0);
        assertTrue(p12.compareTo(p11) > 0);
        assertTrue(p11.compareTo(p21) < 0);
        assertTrue(p11.compareTo(p22) < 0);
        assertTrue(p11.compareTo(np11) > 0);
    }
}
