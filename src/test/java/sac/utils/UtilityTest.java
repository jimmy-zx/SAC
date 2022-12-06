package sac.utils;

import org.junit.jupiter.api.Test;
import sac.model.Point;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UtilityTest {
    @Test
    void testParsePointsString() {
        ArrayList<Point> points = Utility.parsePointsString("0 0 0 1 1 0 1 1");
        assertEquals(List.of(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)), points);
    }
}
