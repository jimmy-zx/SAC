package sac.model.gamemodes;

import sac.model.observers.DataPackage;

/**
 * Timed the game.
 * End the game when the elapsed time is greater than the set value.
 */
public class TimedLayer implements GameLayer{

    private long startTime;
    private long timer;

    /**
     * Start a timer.
     */
    @Override
    public void onGameStart() {
        startTime = System.currentTimeMillis();
    }

    /**
     * Check if the elapsed time is greater than the set time.
     * @return true iff the elapsed time is greater than the set time
     */
    @Override
    public boolean isGameEnd() {
        return System.currentTimeMillis() - startTime > timer;
    }

    /**
     * Set a time limit to the game.
     * @param timer - the time limit in the form of "mm:ss"
     */
    public void setTimer(String timer) {
        // mm:ss
        String[] time = timer.split(":");
        int min = Integer.parseInt(time[0]);
        int sec = Integer.parseInt(time[1]);

        this.timer = (min * 60 + sec) * 1000;
    }



    /**
     * Do nothing. Unrelated to this game layer.
     * @param dataPackage - relavent data received from game
     */
    @Override
    public void notifyAllObservers(DataPackage dataPackage) {

    }
}
