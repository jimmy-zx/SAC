package sac.model.observers;

import org.junit.jupiter.api.Test;
import sac.model.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

public class ComboObserverTest {
    @Test
    void testComboObserverTest() {
        ScoreObserver observer = new ComboObserver();
        assertEquals(0, observer.getScore());
        DataPackage dataPackage = new DataPackage();
        dataPackage.rowCleared = 1;
        observer.update(dataPackage);
        assertEquals(0, observer.getScore());
        observer.update(dataPackage);
        assertEquals(50, observer.getScore());
        observer.update(dataPackage);
        assertEquals(100, observer.getScore());
        dataPackage.moveType = Model.MoveType.HARD_DROP;
        dataPackage.rowCleared = 0;
        observer.update(dataPackage);
        assertEquals(0, observer.getScore());
    }
}
