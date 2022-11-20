package sac.model;

import sac.model.gamemodes.GameMode;
import sac.model.generators.Generator;
import sac.model.observers.RowClearObserver;
import sac.model.rotations.RotationState;

import java.util.List;

public class Model {
    private Board board;
    private Piece currentPiece;
    private RotationState currentState;
    private List<RowClearObserver> rowClearObservers;
    private boolean gameOn;
    private Generator pieceGenerator;
    private GameMode gameMode;

    public enum Movement {
        ROTATE,
        LEFT,
        RIGHT,
        DROP,
        DOWN,
        KEEP,
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
}
