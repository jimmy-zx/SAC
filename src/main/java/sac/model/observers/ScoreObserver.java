package sac.model.observers;

/**
 * An Observer to calculate (non-cumulative) scores.
 */
public abstract class ScoreObserver implements Observer<DataPackage> {
    /**
     * Calculate the current score.
     * @return The non-cumulative score.
     */
    public abstract int getScore();
}
