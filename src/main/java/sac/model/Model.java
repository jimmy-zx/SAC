package sac.model;

import sac.model.gamemodes.GameMode;
import sac.model.observers.DataPackage;
import sac.model.rotations.RotationState;
import sac.utils.Lock;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    private Lock lock;
    private ArrayDeque<Piece> preview;
    private int previewNumber = 5;

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
        board = new Board(gameMode.getWidth(), gameMode.getHeight());
        startGame();
    }

    public void startGame() {
        setGameOn(true);
        preview = new ArrayDeque<>();
        for (int i = 0; i < previewNumber; i++) {
            preview.add(gameMode.getPieceGenerator().nextPiece());
        }
        spawnPiece();
    }

    public Piece nextPiece() {
        preview.add(gameMode.getPieceGenerator().nextPiece());
        return preview.poll();
    }

    private boolean spawnPiece() {
        activePiece = null;  // clear activePiece anyway

        Piece piece = nextPiece();
        currentState = gameMode.getRotationSystem().getInitialState();
        Point spawnPosition = gameMode.getSpawnPosition(piece);
        Board.PlacePieceStatus result = placePiece(piece, spawnPosition);  // try to place a piece
        if (!result.isSuccess()) {
            return false;
        } else {
            activePiece = piece;
            return true;
        }
    }

    public Board.PlacePieceStatus placePiece(Piece piece, Point position) {
        Board.PlacePieceStatus result = board.placePiece(piece, position);
        if (result.isSuccess()) {
            currentPosition = position;
        }
        return result;
    }

    private Point computeTransitionPosition(MoveType type) {
        return currentPosition.offset(
                switch (type) {
                    case LEFT -> new Point(-1, 0);
                    case RIGHT -> new Point(1, 0);
                    case DOWN -> new Point(0, -1);
                    default -> new Point(0, 0);
                }
        );
    }

    private Piece getRotatedPiece(MoveType type) {
        return switch (type) {
            case ROTATE_LEFT -> activePiece.rotateLeft();
            case ROTATE_RIGHT -> activePiece.rotateRight();
            default -> activePiece;
        };
    }

    private void executeMove(MoveType moveType) {
        if (activePiece == null) {
            throw new RuntimeException("Unable to execute move on piece null");
        }
        board.undo();
        board.backup();

        Board.PlacePieceStatus placePieceStatus = null;

        Piece newPiece;
        Point newPosition;

        switch (moveType) {
            case LEFT, RIGHT, DOWN -> {
                newPosition = computeTransitionPosition(moveType);
                placePieceStatus = placePiece(activePiece, newPosition);
            }
            case ROTATE_LEFT, ROTATE_RIGHT -> {
                newPiece = getRotatedPiece(moveType);
                while (currentState.hasNextTest()) {
                    newPosition = currentPosition.offset(currentState.getRotationOffset(newPiece,
                            moveType));
                    placePieceStatus = placePiece(newPiece, newPosition);
                    if (placePieceStatus.isSuccess()) {
                        activePiece = newPiece;
                        currentState = gameMode.getRotationSystem().getNextState(currentState,
                                moveType);
                        break;
                    }
                }
            }
            case HARD_DROP -> {
                newPosition = board.dropPosition(activePiece, currentPosition);
                placePieceStatus = placePiece(activePiece, newPosition);
            }
            case HOLD -> throw new UnsupportedOperationException();
        }
        if (!Objects.requireNonNull(placePieceStatus).isSuccess()) {
            gameMode.getRotationSystem().restore(currentState);
            // gameMode.onInvalidMove();
        }
        if (reachedBottom()) {
            if (moveType == MoveType.HARD_DROP) {  // if HARD_DROP, immediately unlock
                lock.unlock();
            } else if (!lock.isStarted()) {  // if there isn't a lock, lock the piece
                lock.lock(2000);
            } else if (moveType == MoveType.DOWN) {  // if there is a lock, but the user does not response
                lock.unlock();
            }
            if (!lock.isLocked()) {  // if a lock has expired OR there is no lock
                lock.unlock();
                int rowCleared = board.clearRows();
                DataPackage dp = new DataPackage();
                dp.moveType = moveType;
                dp.validMove = placePieceStatus.isSuccess();
                dp.rotationState = currentState;
                dp.activePiece = activePiece;
                dp.rowCleared = rowCleared;
                if (!spawnPiece()) {
                    gameOn = false;
                }
            }
        }
    }

    private boolean reachedBottom() {
        return currentPosition.equals(board.dropPosition(activePiece, currentPosition));
    }

    public void modelTick(MoveType moveType) {
        /*
         * 1. Check gameOn and gameMode.isGameOn(). If false, return.
         * 2. Perform movement on currentPiece.
         * 3. If the movement is valid, return.
         * 4. If the movement is invalid, and the movement is not down, return.
         * 5. If the movement is invalid, and the movement is down, generate a new Piece.
         */
        if (!isGameOn() || gameMode.isGameEnd()) {
            return;
        }
        executeMove(moveType);
    }

    public boolean isGameOn() {
        return this.gameOn;
    }

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    public ArrayDeque<Piece> getPreview() {
        return preview;
    }
}
