package sac.model;

import sac.model.gamemodes.GameMode;
import sac.model.rotations.RotationState;

import java.util.ArrayDeque;


/**
 * The Tetris Game model.
 * <p>
 * Serves a "driver" for the game. The game details are controlled by GameMode
 */
public class Model {
    private Board board;
    private Piece activePiece;
    private Point currentPosition;
    private RotationState currentState;
    private boolean gameOn;
    private GameMode gameMode;

    public enum MoveType {
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

    public void startGame() {
        throw new UnsupportedOperationException();
    }

    public Piece nextPiece() {
        throw new UnsupportedOperationException();
    }

    public Board.PlacePieceStatus placePiece(Piece piece, Point position) {
        throw new UnsupportedOperationException();
    }

    public void modelTick(MoveType movement) {
        /*
         * 1. Check gameOn and gameMode.isGameOn(). If false, return.
         * 2. Perform movement on currentPiece.
         * 3. If the movement is valid, return.
         * 4. If the movement is invalid, and the movement is not down, return.
         * 5. If the movement is invalid, and the movement is down, generate a new Piece.
         */
        throw new UnsupportedOperationException();
    }

    public boolean isGameOn() {
        return this.gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public void putGhostPiece() {throw new UnsupportedOperationException();}

    public int getScore() {
        return gameMode.getScore();
    }
}
