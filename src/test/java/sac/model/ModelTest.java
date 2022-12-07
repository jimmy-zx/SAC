package sac.model;

import org.junit.jupiter.api.Test;
import sac.model.gamemodes.DefaultCore;
import sac.model.gamemodes.GameMode;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {
    @Test
    void testModel() {
        Model model = new Model();
        GameMode gameMode = new GameMode(new DefaultCore(), new Stack<>());
        model.setGameMode(gameMode);
        model.newGame();
        model.startGame();
        model.modelTick(Model.MoveType.DOWN);
        model.modelTick(Model.MoveType.ROTATE_RIGHT);
        model.modelTick(Model.MoveType.ROTATE_LEFT);
        model.modelTick(Model.MoveType.LEFT);
        model.modelTick(Model.MoveType.RIGHT);
        assertNotNull(model.getGhostPiecePositions());
        assertNotNull(model.getPreview());
        model.modelTick(Model.MoveType.HARD_DROP);
        assertNull(model.getHoldPiece());
        model.modelTick(Model.MoveType.HOLD);
        assertNotNull(model.getHoldPiece());
        model.modelTick(Model.MoveType.HOLD);
        while (model.isGameOn()) {  // this test might take ~20secs
            model.modelTick(Model.MoveType.DOWN);
        }
    }
}
