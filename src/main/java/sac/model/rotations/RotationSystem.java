package sac.model.rotations;

import sac.model.Model;

public interface RotationSystem {
    public RotationState getInitialState();
    public RotationState getNextState(RotationState currentState, Model.MoveType moveType);
    public RotationState restore(RotationState currentState);
}
