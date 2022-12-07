package sac.model.rotations;

import org.junit.jupiter.api.Test;
import sac.model.Model;
import sac.model.Piece;

import static org.junit.jupiter.api.Assertions.*;

public class SuperRotationSystemTest {
    @Test
    void testSuperRotationSystem() {
        SuperRotationSystem srs = new SuperRotationSystem();
        RotationState init = srs.getInitialState();
        assertNotNull(init);
        RotationState next = srs.getNextState(init, Model.MoveType.ROTATE_RIGHT);
        assertNotNull(next);
        RotationState rest = srs.restore(next);
        assertNotNull(rest);
    }

    @Test
    void testSuperRotationState() {
        SuperRotationSystem srs = new SuperRotationSystem();
        RotationState init = srs.getInitialState();
        assertTrue(init.hasNextTest());
        while (init.hasNextTest()) {
            assertNotNull(init.getRotationOffset(Piece.generate(Piece.PieceType.O), Model.MoveType.ROTATE_RIGHT));
        }
    }
}
