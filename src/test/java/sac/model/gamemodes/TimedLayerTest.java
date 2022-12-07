package sac.model.gamemodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimedLayerTest {
    @Test
    void testTimedLayer() throws InterruptedException {
        TimedLayer layer = new TimedLayer();
        layer.setTimer("00:01");
        layer.onGameStart();
        assertTrue(layer.getCurrentTime().length() > 0);
        assertFalse(layer.isGameEnd());
        Thread.sleep(1100);
        assertTrue(layer.isGameEnd());
        assertTrue(layer.getCurrentTime().length() > 0);
    }
}
