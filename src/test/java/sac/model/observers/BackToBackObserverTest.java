package sac.model.observers;

import org.junit.jupiter.api.Test;
import sac.model.Model;
import sac.model.Piece;
import sac.model.rotations.SuperRotationSystem;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BackToBackObserverTest {
    @Test
    void testBackToBackObserver() {
        ScoreObserver observer = new BackToBackObserver();
        DataPackage dataPackage = new DataPackage();
        Piece piece = Piece.generate(Piece.PieceType.T);
        SuperRotationSystem.SuperRotationState rotationState = SuperRotationSystem.SuperRotationState.OState;
        dataPackage.activePiece = piece;
        dataPackage.rotationState = rotationState;
        dataPackage.validMove = true;
        rotationState.resetTestCnt();
        rotationState.getRotationOffset(piece, Model.MoveType.ROTATE_RIGHT);
        rotationState.getRotationOffset(piece, Model.MoveType.ROTATE_RIGHT);
        assertEquals(2, rotationState.getTestCnt());
        observer.update(dataPackage);
        assertEquals(0, observer.getScore());
        dataPackage.rowCleared = 4;
        observer.update(dataPackage);
        assertEquals(1000, observer.getScore());
        dataPackage.rowCleared = 0;
        observer.update(dataPackage);
        assertEquals(1000, observer.getScore());
        observer.update(dataPackage);
        assertEquals(0, observer.getScore());
    }
}
