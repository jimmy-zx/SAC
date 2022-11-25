package sac.model;

import sac.model.rotations.RotationState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * An immutable representation of a Tetris Tetromino.
 * Each piece is defined by the points that make up its body.
 */
public class Piece {
    /**
     * Based on https://tetris.wiki/Tetromino
     */
    public static enum PieceType {
        O,
        Z,
        S,
        L,
        J,
        T,
        I,
        Other,
    }
    public final PieceType type;
    public final int width, height;

    /**
     * Sorted representation of the points that make up the piece.
     */
    public final ArrayList<Point> body;

    /**
     * The lowest y value for each column.
     */
    public final ArrayList<Integer> lowestYVals;

    /**
     * Cache for the rotations of a specific piece
     */
    private static Map<PieceType, ArrayList<Piece>> cache;

    public Piece(PieceType type, ArrayList<Point> body) {
        this.body = new ArrayList<>(body);
        Collections.sort(this.body);
        this.type = type;
        throw new UnsupportedOperationException();
    }

    public static Piece generate(PieceType type) {
        return null;
    }

    public Piece rotateLeft() {
        throw new UnsupportedOperationException();
    }

    public Piece rotateRight() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}