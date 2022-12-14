package sac.model.rotations;

import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;

/**
 * Rotation state generated by BasicRotationSystem.
 */
public class BasicRotationState implements RotationState{

    private boolean isTested;

    @Override
    public Point getRotationOffset(Piece activePiece, Model.MoveType moveType) {
        setTested(true);
        Piece newPiece = activePiece.rotateRight();
        return new Point((activePiece.width - newPiece.width)/2, (activePiece.height - newPiece.height)/2);
    }

    @Override
    public boolean hasNextTest() {
        return !isTested;
    }

    public void setTested(boolean isTested) {
        this.isTested = isTested;
    }
}
