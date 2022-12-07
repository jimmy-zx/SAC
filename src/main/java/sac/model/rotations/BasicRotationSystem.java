package sac.model.rotations;

import sac.model.Model;

/**
 * A naive rotation scheme (rotate about the center of the piece).
 * Inspired by Assignment 2.
 */
public class BasicRotationSystem implements RotationSystem{

    private BasicRotationState basic;

    public BasicRotationSystem() {
        basic = new BasicRotationState();
    }

    @Override
    public RotationState getInitialState() {
        basic.setTested(false);
        return basic;
    }

    @Override
    public RotationState getNextState(RotationState currentState, Model.MoveType moveType) {
        return getInitialState();
    }

    @Override
    public RotationState restore(RotationState currentState) {
        return getInitialState();
    }
}
