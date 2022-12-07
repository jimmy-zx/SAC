package sac.model.observers;

import sac.model.Model;
import sac.model.Piece;
import sac.model.rotations.RotationState;

/**
 * A tuple of attributes from Model, used for score calculation.
 */
public class DataPackage {
    public Model.MoveType moveType;
    public boolean validMove;
    public RotationState rotationState;
    public Piece activePiece;
    public int rowCleared;

}
