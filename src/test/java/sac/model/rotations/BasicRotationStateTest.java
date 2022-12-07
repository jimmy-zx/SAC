package sac.model.rotations;

import org.junit.jupiter.api.Test;
import sac.model.Model;
import sac.model.Piece;

import static org.junit.jupiter.api.Assertions.*;

public class BasicRotationStateTest {
    @Test
    void testBasicRotationState() {
        BasicRotationState state = new BasicRotationState();
        assertTrue(state.hasNextTest());
        assertNotNull(state.getRotationOffset(Piece.generate(Piece.PieceType.O), Model.MoveType.ROTATE_RIGHT));
        assertFalse(state.hasNextTest());
        state.setTested(false);
        assertTrue(state.hasNextTest());
    }
}
