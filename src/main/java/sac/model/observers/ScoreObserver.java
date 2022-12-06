package sac.model.observers;

public abstract class ScoreObserver implements Observer<DataPackage> {
    public abstract int getScore();
}
