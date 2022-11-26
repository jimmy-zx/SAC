package sac.model.gamemodes;

import sac.model.Model;
import sac.model.Piece;
import sac.model.generators.Generator;
import sac.model.observers.LinearObserver;
import sac.model.observers.RowClearObserver;
import sac.model.observers.ScoreObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GameMode {
    private Model model;
    private ArrayList<RowClearObserver> rowClearObservers;
    /**
     * Stores all observers that calculate score.
     * <p>
     * Must be a subset of rowClearObservers
     */
    private ArrayList<ScoreObserver> scoreObservers;

    /**
     * A queue that holds the upcoming pieces
     */
    private Queue<Piece> preview;
    private Generator pieceGenerator;

    public GameMode(Model model) {
        this.model = model;
        scoreObservers = new ArrayList<>(List.of(new LinearObserver()));
        rowClearObservers = new ArrayList<>();
        rowClearObservers.addAll(scoreObservers);
    }

    /**
     * Check if the game can continue.
     * <p>
     * Note: this functions depends on whether the game can continue,
     * which is different from Model.gameOn, which is determined by the
     * user.
     * @return If the game can continue.
     */
    public boolean isGameOn() {
        throw new UnsupportedOperationException();
    }

    /**
     * Calculate the total score.
     * @return The total score.
     */
    public int getScore() {
        int totalScore = 0;
        for (ScoreObserver scoreObserver : scoreObservers) {
            totalScore += scoreObserver.getScore();
        }
        return totalScore;
    }

    /**
     * Trigger when at least one row is cleared.
     *
     * @param count The number of rows cleared.
     */
    public void onRowClear(int count) {
        for (RowClearObserver observer : rowClearObservers) {
            observer.update(model);
        }
    }

    /**
     * Get the next piece of the game.
     * @return The generated piece.
     */
    public Piece nextPiece() {
        preview.add(pieceGenerator.nextPiece());
        return preview.remove();
    }

    /**
     * Get the upcoming pieces.
     * @return An arraylist containing the upcoming pieces.
     */
    public ArrayList<Piece> getPreview() {
        return new ArrayList<>(preview);
    }
}
