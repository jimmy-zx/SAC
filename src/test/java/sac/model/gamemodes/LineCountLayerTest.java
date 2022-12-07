package sac.model.gamemodes;

import org.junit.jupiter.api.Test;
import sac.model.observers.DataPackage;

import static org.junit.jupiter.api.Assertions.*;

public class LineCountLayerTest {
    @Test
    void testLineCountLayer() {
        LineCountLayer layer = new LineCountLayer();
        layer.setTotal(5);
        layer.onGameStart();
        assertEquals(0, layer.getCnt());
        assertFalse(layer.isGameEnd());
        DataPackage dataPackage = new DataPackage();
        dataPackage.rowCleared = 2;
        layer.notifyAllObservers(dataPackage);
        assertEquals(2, layer.getCnt());
        assertFalse(layer.isGameEnd());
        layer.notifyAllObservers(dataPackage);
        layer.notifyAllObservers(dataPackage);
        assertEquals(6, layer.getCnt());
        assertTrue(layer.isGameEnd());
        layer.onGameStart();
        assertEquals(0, layer.getCnt());
    }
}
