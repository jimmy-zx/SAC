package sac.model.observers;

public class TSpinObserver extends ScoreCalculator{

    private int score = 0;

    @Override
    public void update(DataPackage obj) {
        

    }

    @Override
    public int getScore() {
        return score;
    }
}
