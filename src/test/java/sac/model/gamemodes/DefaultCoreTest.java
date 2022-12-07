package sac.model.gamemodes;

import org.junit.jupiter.api.Test;
import sac.model.Board;
import sac.model.Piece;
import sac.model.Point;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultCoreTest {
    @Test
    void testDefaultCore() {
        GameCore core = new DefaultCore();
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
