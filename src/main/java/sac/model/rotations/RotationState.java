package sac.model.rotations;

import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;

/**
 * A utility to perform advanced rotation.
 * <p>
 * A RotationState object corresponds to rotation states of a piece.
 * For example, the L piece have four rotation states.
 * There are four corresponding RotationState objects.
 */
public interface RotationState {

    /**
     * Get the one possible position after the rotation.
     * <p>
     * Note that there are many possible positions after the rotation.
     * Call this method again if the placement fails and {@link #hasNextTest()} is true.
     *
     * @param activePiece The piece to rotate.
     * @param moveType The movement type.
     * @return One possible coordinate after rotation.
     */
    Point getRotationOffset(Piece activePiece, Model.MoveType moveType);

    /**
     * Check whether there are other possible positions.
     *
     * @return If there are at least one possible position.
     */
    public boolean hasNextTest();

    /**
     * Resets the possible rotation positions.
     */
    public void resetTests();

    /**
     * Get the next rotation state.
     * <p>
     * Note: the new RotationState object corresponds to the rotated piece.
     *
     * @param moveType The movement type.
     * @return A RotationState object, which is able to perform rotations on the rotated piece.
     */
    public RotationState getNextState(Model.MoveType moveType);
}
