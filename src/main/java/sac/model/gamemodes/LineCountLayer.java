package sac.model.gamemodes;

import sac.model.observers.DataPackage;

/**
 * Count the number of lines cleared in the game.
 * End the game if the target number is reached.
 */
public class LineCountLayer implements GameLayer{

    private int cnt = 0;
    private int total;

    /**
     * Set a target number of lines to be cleared.
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return current number of lines cleared
     */
    public int getCnt() {
        return cnt;
    }

    @Override
    public void onGameStart() {
        cnt = 0;
    }

    /**
     * Check if the target number is reached.
     * @return ture iff the number of lines cleared is greater than or equal to the target number
     */
    @Override
    public boolean isGameEnd() {
        return cnt >= total;
    }

    @Override
    public void notifyAllObservers(DataPackage dataPackage) {
        cnt += dataPackage.rowCleared;
    }
}
