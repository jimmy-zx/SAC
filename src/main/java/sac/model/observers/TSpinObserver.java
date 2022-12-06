package sac.model.observers;

import sac.model.rotations.SuperRotationSystem;

public class TSpinObserver extends ScoreObserver {

    private int score = 0;
    private SuperRotationSystem.SuperRotationState state;

    @Override
    public void update(DataPackage obj) {
        this.state = (SuperRotationSystem.SuperRotationState) obj.rotationState;
        int rowCleared = obj.rowCleared;
        if(rowCleared == 0){
            return;
        }
        switch (this.state){
            case LState, RState -> score += rowCleared * 10;
            case OState -> score += rowCleared;
            case ZState -> score += rowCleared * 20;
        }
    }

    @Override
    public int getScore() {
        return score;
    }
}
