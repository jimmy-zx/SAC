package sac.model;

import javafx.scene.paint.Color;

public class Board {
    public final int width;
    public final int height;

    private final Color[][] grid;
    private final Color[][] backupGrid;
    private boolean committed;
    private final int[] rowCounts;

    public enum PlacePieceStatus {
        ADD_OK, ADD_ROW_FILED, ADD_OUT_BOUNDS, ADD_BAD
    }

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Color[width][height];
        this.backupGrid = new Color[width][height];
        this.rowCounts = new int[height];
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

    public Color getGrid(int x, int y) {
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
