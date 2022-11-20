package sac.model.rotations;

import sac.model.Piece;

public interface RotationState {
    Piece rotateLeft();
    Piece rotateRight();
}
