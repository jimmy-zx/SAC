package sac.model.gamemodes;

import sac.model.Model;
import sac.model.observers.LinearObserver;
import sac.model.observers.RowClearObserver;
import sac.model.observers.ScoreObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class GameMode {
    private Model model;
    private ArrayList<ScoreObserver> scoreObservers;
    private ArrayList<RowClearObserver> rowClearObservers;

    public GameMode(Model model) {
        this.model = model;
        scoreObservers = new ArrayList<>(List.of(new LinearObserver()));
        rowClearObservers = new ArrayList<>();
        rowClearObservers.addAll(scoreObservers);
    }

    public boolean getGameEnd() {
        return !this.model.getGameOn();
    }
    public int getScore() {
        int totalScore = 0;
        for (ScoreObserver scoreObserver : scoreObservers) {
            totalScore += scoreObserver.getScore();
        }
        return totalScore;
    }

    public ArrayList<RowClearObserver> getRowClearObservers() {
        return rowClearObservers;
    }
}
