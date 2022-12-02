package sac.model;

/**
 * Represents the playground of Tetris.
 */
public class Board {
    /**
     * The width of the board.
     */
    public final int width;

    /**
     * The height of the board.
     */
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

    /**
     * The backup grid
     */
    private Piece.PieceType[][] backupGrid;

    /**
     * The return status for PlacePiece
     */
    public enum PlacePieceStatus {
        /**
         * The placement is successful and no row is filled.
         */
        ADD_OK,
        /**
         * The placement is successful and some row is filled.
         */
        ADD_ROW_FILLED,
        /**
         * The placement failed due to out of bound.
         */
        ADD_OUT_BOUNDS,
        /**
         * The placement failed due to conflict between placed pieces.
         */
        ADD_BAD

        public boolean isSuccess() {
            return this.ordinal() <= ADD_ROW_FILLED.ordinal();
        }
    }

    /**
     * Initializes a new Board object.
     *
     * @param width The width of the board.
     * @param height The height of the board.
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Piece.PieceType[width][height];
        this.rowCounts = new int[height];
    }

    public boolean isValidPoint(Point point) {
        return 0 <= point.x() && point.x() < width && 0 <= point.y() && point.y() < height;
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
        boolean filled = false;
        for (Point point : piece.body) {
            Point newPoint = position.offset(point);
            if (!isValidPoint(newPoint)) {
                return PlacePieceStatus.ADD_OUT_BOUNDS;
            }
            if (getGrid(newPoint) != null) {
                return PlacePieceStatus.ADD_BAD;
            }
        }
        for (Point point : piece.body) {
            Point newPoint = position.offset(point);
            grid[newPoint.x()][newPoint.y()] = piece.type;
            rowCounts[newPoint.y()]++;
            if (rowCounts[newPoint.y()] == width) {
                filled = true;
            }
        }
        if (filled) {
            return PlacePieceStatus.ADD_ROW_FILLED;
        }
        return PlacePieceStatus.ADD_OK;
    }

    /**
     * Clear all the rows in the board that is filled.
     *
     * @return The number of rows cleared.
     */
    public int clearRows() {
        int skip = 0;  // current number of full rows encountered
        for (int y = 0; y < height; y++) {
            if (rowCounts[y] == width) {  // a full row
                for (int x = 0; x < width; x++) {
                    grid[x][y] = null;
                }
                skip++;
            } else if (skip > 0) {  // pull this row down
                for (int x = 0; x < width; x++) {
                    grid[x][y - skip] = grid[x][y];
                }
            }
        }
        // renew the rowCount
        for (int y = 0; y < height; y++) {
            int count = 0;
            for (int x = 0; x < width; x++) {
                if (grid[x][y] != null) {
                    count++;
                }
            }
            rowCounts[y] = count;
        }
        return skip;
    }

    /**
     * Backup the grid.
     * <p>
     * This function should be only called by the client.
     */
    public void backup() {
        if (backupGrid != null) {
            throw new RuntimeException("Cannot backup the grid. Commit or undo first.");
        }
        backupGrid = grid.clone();
    }

    /**
     * Restore the grid before the backup.
     * <p>
     * This function should be only called by the client.
     */
    public void undo() {
        if (this.backupGrid == null) {
            throw new RuntimeException("Nothing to undo.");
        }
        grid = backupGrid;
        commit();
    }

    /**
     * Commit the change.
     */
    public void commit() {
        if (backupGrid == null) {
            throw new RuntimeException("Nothing to commit");
        }
        backupGrid = null;
    }

    /**
     * Check what type of piece occupies the position.
     *
     * @param pos The position to be checked.
     * @return The type of piece that occupies the position. null if no piece occupies such position.
     */
    public Piece.PieceType getGrid(Point pos) {
        return grid[pos.x()][pos.y()];
    }

    /**
     * Generates the string representation of the board.
     *
     * @return A string representation of the board.
     */
    public String toString() {
        StringBuffer builder = new StringBuffer();
        builder.append(String.format("Board with (%d, %d)\n", height, width));
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                if (grid[x][y] == null) {
                    builder.append(" ");
                } else {
                    builder.append(grid[x][y].ordinal());
                }
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
