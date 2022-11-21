package sac.model.observers;

import sac.model.Model;

public class LinearObserver extends ScoreObserver {
    private int score = 0;

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void update(Model obj) {
        this.score += 1;
    }
}
