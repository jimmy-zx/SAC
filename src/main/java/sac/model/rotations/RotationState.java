package sac.model.rotations;

import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;

public interface RotationState {

    Point getRotationOffset(Piece activePiece, Model.MoveType moveType);

    public boolean hasNextTest();

    public RotationState getNextState(Model.MoveType moveType);

    public void restore();
}
