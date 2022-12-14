package sac.model.observers;

import sac.model.Piece;
import sac.model.rotations.SuperRotationSystem;

/**
 * A ScoreObserver to calculate score based on consecutive Tetris or T-Spin.
 */
public class BackToBackObserver extends ScoreObserver{

    boolean lastTspin = false;
    boolean lastTetris = false;

    int score = 0;

    @Override
    public void update(DataPackage obj) {

        if (obj.rowCleared < 4) {
            if (obj.activePiece.type == Piece.PieceType.T && obj.validMove) {
                if (obj.rotationState instanceof SuperRotationSystem.SuperRotationState) {
                    if (((SuperRotationSystem.SuperRotationState) obj.rotationState).getTestCnt() > 0) {
                        lastTspin = true;
                        if (lastTetris) {
                            score += 1000;
                        }
                    }
                }
            } else {
                lastTspin = false;
            }
            lastTetris = false;
        } else {
            lastTetris = true;
            if (lastTspin) {
                score += 1000;
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
