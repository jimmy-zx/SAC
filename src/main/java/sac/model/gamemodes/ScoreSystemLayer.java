package sac.model.gamemodes;

import sac.model.observers.DataPackage;
import sac.model.observers.ScoreObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Score system layer is responsible for calculating the scores.
 * The score calculation scheme can be changed by change the subscription of score observers.
 */
public class ScoreSystemLayer implements GameLayer{

    private List<ScoreObserver> scoreObservers;
    private int score = 0;

    public ScoreSystemLayer() {
        scoreObservers = new ArrayList<>();
    }

    @Override
    public void onGameStart() {
//        scoreObservers = new ArrayList<>();
    }

    @Override
    public boolean isGameEnd() {
        return false;
    }

    /**
     * Notify all score observers to calculate score incremented.
     * @param dataPackage - relavent data received from game
     */
    @Override
    public void notifyAllObservers(DataPackage dataPackage) {
        if (scoreObservers.size() > 0) {
            for (ScoreObserver o : scoreObservers) {
                o.update(dataPackage);
            }
        }
    }

    /**
     * Get the score incremented during one tick.
     * @return
     */
    public int getScore() {
        if (scoreObservers.size() > 0) {
            for (ScoreObserver o : scoreObservers) {
                score += o.getScore();
            }
        } else {
            score = 0;
        }
        return score;
    }

    /**
     * Add a score observer (Add a score calculation scheme).
     * @param scoreObserver
     * @return this
     */
    public ScoreSystemLayer addScoreObserver(ScoreObserver scoreObserver) {
        scoreObservers.add(scoreObserver);
        return this;
    }

}
