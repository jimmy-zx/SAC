package sac.model.gamemodes;

import org.junit.jupiter.api.Test;
import sac.model.Piece;
import sac.model.observers.DataPackage;
import sac.model.observers.LinearObserver;
import sac.model.observers.ScoreObserver;

import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class GameModeTest {
    @Test
    void testGameModeSingleLayer() {
        GameCore core = new DefaultCore();
        GameLayer layer = new TimedLayer();
        GameMode mode = new GameMode(core, layer);
        assertSame(core, mode.getCore());
        assertNotNull(mode.getPieceGenerator());
        assertNotNull(mode.getRotationSystem());
        assertNotNull(mode.getSpawnPosition(Piece.generate(Piece.PieceType.O)));
        assertTrue(mode.getWidth() > 0);
        assertTrue(mode.getHeight() > 0);
        assertTrue(mode.getBuffer() >= 0);
    }

    @Test
    void testGameModeMultiLayer() {
        GameCore core = new DefaultCore();
        Stack<GameLayer> layers = new Stack<>();
        LineCountLayer layer1 = new LineCountLayer();
        layer1.setTotal(100);
        ScoreSystemLayer layer2 = new ScoreSystemLayer();
        ScoreObserver observer = new LinearObserver();
        layer2.addScoreObserver(observer);
        boolean[] status = {false, false, false};
        GameLayer layer3 = new GameLayer() {
            @Override
            public void onGameStart() {
                status[0] = true;
            }

            @Override
            public boolean isGameEnd() {
                return status[2];
            }

            @Override
            public void notifyAllObservers(DataPackage dataPackage) {
                status[1] = true;
            }
        };
        layers.push(layer1);
        layers.push(layer2);
        layers.push(layer3);
        GameMode mode = new GameMode(core, layers);

        assertSame(core, mode.getCore());
        assertNotNull(mode.getPieceGenerator());
        assertNotNull(mode.getRotationSystem());
        assertNotNull(mode.getSpawnPosition(Piece.generate(Piece.PieceType.O)));
        assertTrue(mode.getWidth() > 0);
        assertTrue(mode.getHeight() > 0);
        assertTrue(mode.getBuffer() >= 0);

        mode.onGameStart();
        assertTrue(status[0]);
        assertFalse(status[1]);

        assertFalse(mode.isGameEnd());
        status[2] = true;
        assertTrue(mode.isGameEnd());

        mode.notifyAllObservers(new DataPackage());
        assertTrue(status[1]);

        GameCore newCore = new DefaultCore();
        mode.setCore(newCore);
        assertNotSame(core, newCore);
        assertSame(newCore, mode.getCore());

        mode.removeLayer(layer3);
        status[0] = false;
        status[1] = false;
        mode.notifyAllObservers(new DataPackage());
        assertFalse(status[1]);

        mode.addLayer(layer3);
        mode.notifyAllObservers(new DataPackage());
        assertTrue(status[1]);
    }
}
