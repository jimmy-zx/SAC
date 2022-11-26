package sac.model;

import sac.model.gamemodes.GameMode;
import sac.model.generators.Generator;
import sac.model.observers.RowClearObserver;
import sac.model.observers.ScoreObserver;
import sac.model.rotations.RotationState;

import java.util.List;

public class Model {
    private Board board;
    private Piece currentPiece;
    private RotationState currentState;
    private List<Piece> preview;
    private List<RowClearObserver> rowClearObservers;
    private int score;
    private boolean gameOn;
    private Generator pieceGenerator;
    private GameMode gameMode;

    public enum Movement {
        ROTATE_LEFT,
        ROTATE_RIGHT,
        LEFT,
        RIGHT,
        DOWN,
        HARD_DROP,
        HOLD,
    }

    public Model() {
        // this.board = new Board();
        this.gameOn = false;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void newGame() {
        throw new UnsupportedOperationException();
    }

    public void modelTick(Movement movement) {
        throw new UnsupportedOperationException();
    }

    public boolean getGameOn() {
        return this.gameOn;
    }

    public void putGhostPiece() {throw new UnsupportedOperationException();}

    public void notifyScoreObservers() {
        for (RowClearObserver observer : rowClearObservers) {
            observer.update(this);
        }
    }

    private void calculateScore() {
        for (RowClearObserver observer : rowClearObservers) {
            if (observer instanceof ScoreObserver) {
                this.score += ((ScoreObserver) observer).getScore();
            }
        }
    }

    public int getScore() {
        return this.score;
    }
}
