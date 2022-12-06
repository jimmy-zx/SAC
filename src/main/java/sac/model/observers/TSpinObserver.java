package sac.model.observers;

import sac.model.Piece;
import sac.model.rotations.SuperRotationSystem;

public class TSpinObserver extends ScoreObserver {

    private int score;

    @Override
    public void update(DataPackage obj) {
        int rowCleared = obj.rowCleared;
        if (obj.activePiece.type == Piece.PieceType.T && obj.validMove) {
            if (obj.rotationState instanceof SuperRotationSystem.SuperRotationState) {
                if (((SuperRotationSystem.SuperRotationState) obj.rotationState).getTestCnt() > 0) {
                    score = (obj.rowCleared + 1) * 4;
                }
            }
        }
    }

    @Override
    public int getScore() {
        int result = score;
        score = 0;
        return result;
    }
}
