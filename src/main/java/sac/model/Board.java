package sac.model;

public class Board {
    public final int width;
    public final int height;

    private Piece.PieceType[][] grid;
    private Piece.PieceType[][] backupGrid;
    private boolean committed;
    private int[] rowCounts;

    public enum PlacePieceStatus {
        ADD_OK, ADD_ROW_FILED, ADD_OUT_BOUNDS, ADD_BAD
    }

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Piece.PieceType[width][height];
        this.rowCounts = new int[height];
        this.committed = true;
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public PlacePieceStatus placePiece(Piece piece, int x, int y) {
        throw new UnsupportedOperationException();
    }

    public int clearRows() {
        throw new UnsupportedOperationException();
    }

    private void backup() {
        throw new UnsupportedOperationException();
    }

    public void undo() {
        throw new UnsupportedOperationException();
    }

    public void commit() {
        throw new UnsupportedOperationException();
    }

    public Piece.PieceType getGrid(int x, int y) {
        return this.grid[x][y];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isCommitted() {
        return committed;
    }

    public String toString() {
        throw new UnsupportedOperationException();
    }
}
