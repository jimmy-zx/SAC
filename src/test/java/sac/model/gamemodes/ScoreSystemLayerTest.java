package sac.model.gamemodes;

import org.junit.jupiter.api.Test;
import sac.model.observers.ComboObserver;
import sac.model.observers.DataPackage;
import sac.model.observers.LinearObserver;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreSystemLayerTest {
    @Test
    void multipleObserverTest() {
        ScoreSystemLayer layer = new ScoreSystemLayer();
        layer.addScoreObserver(new ComboObserver());
        layer.addScoreObserver(new LinearObserver());
        DataPackage dataPackage = new DataPackage();
        dataPackage.rowCleared = 3;
        layer.notifyAllObservers(dataPackage);
        assertEquals(500, layer.getScore());
        layer.notifyAllObservers(dataPackage);
        assertEquals(1050, layer.getScore());
    }

    @Test
    void emptyObserverTest() {
        ScoreSystemLayer layer = new ScoreSystemLayer();
        DataPackage dataPackage = new DataPackage();
        dataPackage.rowCleared = 3;
        layer.notifyAllObservers(dataPackage);
        assertEquals(0, layer.getScore());
    }
}
