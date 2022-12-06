package sac.model;

import sac.model.gamemodes.GameMode;
import sac.model.observers.DataPackage;
import sac.model.rotations.RotationState;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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
    //    private Generator pieceGenerator;
    private GameMode gameMode;

    private DataPackage dataPackage;

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
//        this.gameMode = new ClassicGameMode(this); // temporary
//        this.board = new Board(gameMode.getWidth(), gameMode.getHeight());
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void newGame() {
        this.board = new Board(gameMode.getWidth(), gameMode.getHeight());
        gameMode.getPieceGenerator().reset();
        holdPiece = null;
    }

    public void startGame() {
        gameOn = true;
        gameMode.onGameStart();

        lockReset();

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
                rowCleared = lock(0);
                break;
            case HOLD:
                hold();
                spawnPiece();

        }

        if (validMove) {
            lockReset();
        } else {
            if (activePiece != null) placePiece(activePiece, currentPosition);
        }

        if (reachedBottom()) {
            if (moveType != MoveType.HARD_DROP) {
                rowCleared = lock(1000);
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

    /**
     * Lock current active piece.
     *
     * @param delay in ms
     * @return the number of rows cleared due to this lock
     */
    private int lock(long delay) {
        if (delayStartTime < 0) {
            delayStartTime = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - delayStartTime < delay) {
            return 0;
        } else {
            int rowCleared = board.clearRows();
            spawnPiece();
            lockReset();
            return rowCleared;
        }
    }

    private void lockReset() {
        delayStartTime = -1;
    }

    private void hold() {
        if (holdPiece != null) {
            preview.addFirst(Piece.generate(holdPiece));
        }
        holdPiece = activePiece.type;
    }

    public void modelTick(MoveType moveType) {
        if (!isGameOn() || gameMode.isGameEnd()) return;

        executeMove(moveType);
    }

    public boolean isGameOn() {
        return this.gameOn;
    }

    private void stopGame() {
        this.gameOn = false;
    }

    public List<Point> getGhostPiecePositions() {
        // do not know piece type
        List<Point> ghostPiecePositions = new ArrayList<>();
        Point origin = board.dropPosition(activePiece, currentPosition);
        for (Point point : activePiece.body) {
            ghostPiecePositions.add(point.offset(origin));
        }
        return ghostPiecePositions;
    }

    public Piece.PieceType getHoldPiece() {
        return holdPiece;
    }

    public ArrayDeque<Piece> getPreview() {
        return this.preview.clone();
    }
}
