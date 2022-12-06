package sac.model.observers;

import sac.model.Model;

public class LinearObserver extends ScoreCalculator {
    private int score = 0;

    @Override
    public int getScore() {
        int result = score * level;
        score = 0;
        return result;
    }

    @Override
    public void update(DataPackage obj) {
        switch (obj.rowCleared) {
            case 1 -> score = 100;
            case 2 -> score = 200;
            case 3 -> score = 500;
            case 4 -> score = 800;
        }
    }
}
