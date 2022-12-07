package sac.model.gamemodes;

import org.junit.jupiter.api.Test;
import sac.model.Board;
import sac.model.Piece;
import sac.model.Point;
import sac.model.generators.RandomGenerator;
import sac.model.rotations.BasicRotationSystem;

import static org.junit.jupiter.api.Assertions.*;

public class CustomizeCoreTest {
    @Test
    void testCustomizeCore() {
        CustomizeCore core = new CustomizeCore() {
            @Override
            public Point getSpawnPosition(Piece piece) {
                return new Point((getWidth() - piece.width)/2, getHeight() - piece.height);
            }
        };
        core.setSize(10, 20, 4);
        core.setGenerator(new RandomGenerator());
        core.setRotationSystem(new BasicRotationSystem());
        Board board = new Board(10, 24);
        assertNotNull(core.getPieceGenerator());
        assertNotNull(core.getRotationSystem());
        Point point = core.getSpawnPosition(Piece.generate(Piece.PieceType.O));
        assertNotNull(point);
        assertTrue(board.isValidPoint(point));
        assertTrue(core.getWidth() > 0);
        assertTrue(core.getHeight() > 0);
        assertTrue(core.getBuffer() > 0);
    }
}
