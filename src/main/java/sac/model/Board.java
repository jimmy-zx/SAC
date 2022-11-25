package sac.model;

/**
 * Represents the playground of Tetris.
 */
public class Board {
    public final int width;
    public final int height;

    /**
     * Stores what kind of Piece occupies the coordinate.
     * If nothing occupies the coordinate, stores null.
     * Note: access the grid by grid[x][y]
     */
    private Piece.PieceType[][] grid;

    /**
     * Stores how many coordinates are occupied in each row.
     */
    private int[] rowCounts;

    private Piece.PieceType[][] backupGrid;

    public enum PlacePieceStatus {
        ADD_OK, ADD_ROW_FILLED, ADD_OUT_BOUNDS, ADD_BAD
    }

    /**
     * Initializes a new Board object.
     *
     * @param height The height of the board.
     * @param width The width of the board.
     */
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Piece.PieceType[width][height];
        this.rowCounts = new int[height];
    }

    /**
     * Place a piece at a specific position.
     *
     * @param piece The piece to be placed.
     * @param position The coordinate of the bottom-left corner of the piece.
     * @return
     *  ADD_OUT_BOUNDS when the placement is out of bound,
     *  ADD_BAD when the piece is placed on some position that is already occupied,
     *  ADD_ROW_FILLED when arbitrary row is filled after the piece is placed,
     *  ADD_OK in all the other cases.
     */
    public PlacePieceStatus placePiece(Piece piece, Point position) {
        throw new UnsupportedOperationException();
    }

    /**
     * Clear all the rows in the board that is filled.
     *
     * @return The number of rows cleared.
     */
    public int clearRows() {
        throw new UnsupportedOperationException();
    }

    /**
     * Backup the grid.
     * This function should be only called by the client.
     */
    public void backup() {
        throw new UnsupportedOperationException();
    }

    /**
     * Restore the grid before the backup.
     * This function should be only called by the client.
     */
    public void undo() {
        throw new UnsupportedOperationException();
    }

    /**
     * Check what type of piece occupies the position.
     *
     * @param pos The position to be checked.
     * @return The type of piece that occupies the position. null if no piece occupies such position.
     */
    public Piece.PieceType getGrid(Point pos) {
        return this.grid[pos.x][pos.y];
    }

    public String toString() {
        throw new UnsupportedOperationException();
    }
}
