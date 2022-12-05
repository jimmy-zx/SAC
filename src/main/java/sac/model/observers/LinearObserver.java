package sac.model.observers;

import sac.model.Model;

public class LinearObserver extends ScoreCalculator {
    private int score = 0;

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void update(DataPackage obj) {
        this.score += obj.rowCleared;
    }
}
