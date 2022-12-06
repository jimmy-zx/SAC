package sac.model.observers;

import org.junit.jupiter.api.Test;
import sac.model.Model;
import sac.model.Piece;
import sac.model.rotations.RotationState;
import sac.model.rotations.SuperRotationSystem;

import static org.junit.jupiter.api.Assertions.*;

public class TSpinObserverTest {
    @Test
    void testTSpinObserver() {
        ScoreObserver observer = new TSpinObserver();
        RotationState rotationState = SuperRotationSystem.SuperRotationState.OState;
        Piece piece = Piece.generate(Piece.PieceType.T);
        DataPackage dataPackage = new DataPackage();

        dataPackage.rotationState = rotationState;
        dataPackage.activePiece = piece;

        assertEquals(0, observer.getScore());

        dataPackage.rowCleared = 1;
        dataPackage.validMove = true;
        rotationState.getRotationOffset(piece, Model.MoveType.ROTATE_RIGHT);
        observer.update(dataPackage);
        assertEquals(8, observer.getScore());

        rotationState.getRotationOffset(piece, Model.MoveType.ROTATE_RIGHT);
        observer.update(dataPackage);
        assertEquals(8, observer.getScore());
    }
}
