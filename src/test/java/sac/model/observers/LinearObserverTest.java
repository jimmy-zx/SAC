package sac.model.observers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinearObserverTest {
    @Test
    void testLinearObserver() {
        ScoreObserver observer = new LinearObserver();
        assertEquals(0, observer.getScore());
        DataPackage dataPackage = new DataPackage();
        dataPackage.rowCleared = 0;
        observer.update(dataPackage);
        assertEquals(0, observer.getScore());
        dataPackage.rowCleared = 1;
        observer.update(dataPackage);
        assertEquals(100, observer.getScore());
        dataPackage.rowCleared = 4;
        observer.update(dataPackage);
        assertEquals(800, observer.getScore());
        dataPackage.rowCleared = 3;
        observer.update(dataPackage);
        assertEquals(500, observer.getScore());
        dataPackage.rowCleared = 2;
        observer.update(dataPackage);
        assertEquals(200, observer.getScore());
    }
}
