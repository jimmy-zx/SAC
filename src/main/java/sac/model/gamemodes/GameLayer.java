package sac.model.gamemodes;

import sac.model.observers.DataPackage;

/**
 * Manage add-on functions of the game.
 */
public interface GameLayer {
    /**
     * Define what to initialize before the game starts.
     */
    public void onGameStart();

    /**
     * Get whether the game-over condition is met.
     * @return true iff game should end
     */
    public boolean isGameEnd();

    /**
     * Notify all observers that listen to this GameLayer.
     * @param dataPackage - relavent data received from game
     */
    public void notifyAllObservers(DataPackage dataPackage);
}
