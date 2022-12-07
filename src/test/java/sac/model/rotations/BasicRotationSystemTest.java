package sac.model.rotations;

import org.junit.jupiter.api.Test;
import sac.model.Model;

import static org.junit.jupiter.api.Assertions.*;

public class BasicRotationSystemTest {
    @Test
    void testBasicRotationSystem() {
        BasicRotationSystem system = new BasicRotationSystem();
        assertNotNull(system.getInitialState());
        assertNotNull(system.getNextState(system.getInitialState(), Model.MoveType.ROTATE_RIGHT));
        assertNotNull(system.restore(system.getInitialState()));
    }
}
