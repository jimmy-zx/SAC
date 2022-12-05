package sac.model.observers;

public abstract class ScoreCalculator implements Observer<DataPackage> {
    public abstract int getScore();
}
