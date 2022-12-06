package sac.model.observers;

import sac.model.Model;

public class ComboObserver extends ScoreObserver{

    private int combo = 0;
    private int score = 0;

    @Override
    public void update(DataPackage obj) {
        if (obj.rowCleared > 0) {
            score += 50 * combo ++;
        } else {
            if (obj.moveType == Model.MoveType.HARD_DROP) combo = 0;
        }
    }

    @Override
    public int getScore() {
        int result = score;
        score = 0;
        return result;
    }
}
