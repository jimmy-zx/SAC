package sac.model;

import sac.model.gamemodes.GameMode;
import sac.model.observers.DataPackage;
import sac.model.rotations.RotationState;
import sac.utils.Lock;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * The model for a Tetris Game.
 * <p>
 *     This class acts as a wrapper for Board with extra functionality.
 * </p>
 */
public class Model {
    public Board board;
    private Piece activePiece;
    private Point currentPosition; // the position of current activePiece (bottom-left)

    private RotationState currentState;

    private ArrayDeque<Piece> preview;
    private int previewNumber = 5; // temp

    private Piece.PieceType holdPiece;

    private int score;
    private boolean gameOn;
    private long delayStartTime;

    Lock lock;

    //    private Generator pieceGenerator;
    private GameMode gameMode;

    private DataPackage dataPackage;

    /**
     * The type of movement.
     */
    public enum MoveType {
        /**
         * Rotate the current piece 90 degrees counterclockwise.
         */
        ROTATE_LEFT,
        /**
         * Rotate the current piece 90 degrees clockwise.
         */
        ROTATE_RIGHT,
        /**
         * Move the piece left by one unit.
         */
        LEFT,
        /**
         * Move the piece right by one unit.
         */
        RIGHT,
        /**
         * Move the piece down by one unit.
         */
        DOWN,
        /**
         * Drop the current piece.
         */
        HARD_DROP,
        /**
         * Hold the current piece and replace it with a new one.
         */
        HOLD,
    }

    /**
     * Initialize a new model.
     */
    public Model() {
        // this.board = new Board();
        this.gameOn = false;
        lock = new Lock();
//        this.gameMode = new ClassicGameMode(this); // temporary
//        this.board = new Board(gameMode.getWidth(), gameMode.getHeight());
    }

    /**
     * Set the game mode.
     * <p>
     *     Note: GameMode stores the actual game logic.
     * </p>
     * @param gameMode The desired GameMode.
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Clear the current game and create a new one.
     */
    public void newGame() {
        this.board = new Board(gameMode.getWidth(), gameMode.getHeight());
        gameMode.getPieceGenerator().reset();
        holdPiece = null;
    }

    /**
     * Start the game.
     */
    public void startGame() {
        gameOn = true;
        gameMode.onGameStart();

        lock.unlock();

        preview = new ArrayDeque<>();
        for (int i = 0; i < previewNumber; i++) {
            preview.add(gameMode.getPieceGenerator().nextPiece());
        }

        spawnPiece();
    }

    private Piece nextPiece() {
        preview.add(gameMode.getPieceGenerator().nextPiece());
        return preview.poll();
    }

    private void spawnPiece() {
        board.commit();
        activePiece = null;

        Piece piece = nextPiece();
        currentState = gameMode.getRotationSystem().getInitialState();

        Point spawnPosition = gameMode.getSpawnPosition(piece);

        Board.PlacePieceStatus result = placePiece(piece, spawnPosition);

        if (!result.isSuccess()) {
            stopGame();
        } else {
            activePiece = piece;
        }

        dataPackage = new DataPackage();
    }

    /**
     * Attempt to place the piece at a given board position
     */
    private Board.PlacePieceStatus placePiece(Piece piece, Point position) {
        board.backup();

        Board.PlacePieceStatus result = board.placePiece(piece, position);

        if (result.isSuccess()) { //success
            currentPosition = position;
        } else {
            board.undo();
        }
        return result;
    }

    /**
     * Translation: Only vertical or horizontal.
     *
     * @param type
     * @return
     */
    private Point computeTranslationPosition(MoveType type) {
        Point newPosition = currentPosition;
        switch (type) {
            case LEFT -> newPosition = currentPosition.offset(-1, 0);
            case RIGHT -> newPosition = currentPosition.offset(1, 0);
            case DOWN -> newPosition = currentPosition.offset(0, -1);
        }
        return newPosition;
    }

    private Piece getRotatedPiece(MoveType type) {
        Piece newPiece = activePiece;
        switch (type) {
            case ROTATE_LEFT -> newPiece = activePiece.rotateLeft();
            case ROTATE_RIGHT -> newPiece = activePiece.rotateRight();
        }
        return newPiece;
    }

    private void executeMove(MoveType moveType) {
        if (activePiece != null) {
            board.undo();    // remove the piece from its old position
        }

        boolean validMove = true;

        Piece newPiece = activePiece;
        Point newPosition = currentPosition;

        int rowCleared = 0;

        switch (moveType) {
            case LEFT:
            case RIGHT:
            case DOWN:
                newPosition = computeTranslationPosition(moveType);
                validMove = placePiece(activePiece, newPosition).isSuccess();
                break;
            case ROTATE_LEFT:
            case ROTATE_RIGHT:
                newPiece = getRotatedPiece(moveType);
                while (currentState.hasNextTest()) {
                    newPosition = currentPosition.offset(currentState.getRotationOffset(activePiece, moveType));
                    validMove = placePiece(newPiece, newPosition).isSuccess();
                    if (validMove) {
                        activePiece = newPiece;
                        currentState = gameMode.getRotationSystem().getNextState(currentState, moveType);
                        break;
                    }
                }

                break;
            case HARD_DROP:
                newPosition = board.dropPosition(activePiece, currentPosition);
                placePiece(activePiece, newPosition);
                lock.unlock();
                break;
            case HOLD:
                hold();
                spawnPiece();

        }

        if (validMove) {
        } else {
            if (activePiece != null) placePiece(activePiece, currentPosition);
        }

        if (reachedBottom()) {
            if (moveType != MoveType.HARD_DROP) {
                if (!lock.isStarted()) {
                    lock.lock(1000);
                } else if (moveType != MoveType.DOWN) {
                    lock.lock(500);
                }
            }
            if (!lock.isLocked()) {
                lock.unlock();
                rowCleared = board.clearRows();
                spawnPiece();
            }
        }

        dataPackage.rowCleared = rowCleared;
        dataPackage.moveType = moveType;
        dataPackage.validMove = validMove;
        dataPackage.rotationState = currentState;
        dataPackage.activePiece = activePiece;

        currentState = gameMode.getRotationSystem().restore(currentState);

        gameMode.notifyAllObservers(dataPackage);

        if (gameMode.isGameEnd()) {
            stopGame();
        }
    }

    private boolean reachedBottom() {
        return currentPosition.equals(board.dropPosition(activePiece, currentPosition));
    }

    private void hold() {
        if (holdPiece != null) {
            preview.addFirst(Piece.generate(holdPiece));
        }
        holdPiece = activePiece.type;
    }

    /**
     * Update the model by moveType
     * @param moveType The movement.
     */
    public void modelTick(MoveType moveType) {
        if (!isGameOn() || gameMode.isGameEnd()) return;

        executeMove(moveType);
    }

    /**
     * Check if the game is on.
     * @return If the game is on.
     */
    public boolean isGameOn() {
        return this.gameOn;
    }

    /**
     * Stop the current game.
     */
    private void stopGame() {
        this.gameOn = false;
    }

    /**
     * Calculate the ghost piece of current piece.
     * @return A list of point representing the body of the ghost piece.
     */
    public List<Point> getGhostPiecePositions() {
        // do not know piece type
        List<Point> ghostPiecePositions = new ArrayList<>();
        Point origin = board.dropPosition(activePiece, currentPosition);
        for (Point point : activePiece.body) {
            ghostPiecePositions.add(point.offset(origin));
        }
        return ghostPiecePositions;
    }

    /**
     * Get the current piece being held.
     * @return The piece being held.
     */
    public Piece.PieceType getHoldPiece() {
        return holdPiece;
    }

    /**
     * Get the upcoming pieces.
     * @return A list of upcoming pieces.
     */
    public ArrayDeque<Piece> getPreview() {
        return this.preview.clone();
    }
}
