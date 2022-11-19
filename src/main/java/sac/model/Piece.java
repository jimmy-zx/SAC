package sac.model;

import sac.model.rotations.RotationState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class Piece {
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
    private final PieceType type;

    /* sorted */
    private final ArrayList<Point> body;

    private ArrayList<Integer> lowestYVals;
    private static Map<PieceType, ArrayList<Piece>> cache;
    private int width, height;

    public Piece(PieceType type, ArrayList<Point> body) {
        this.body = new ArrayList<>(body);
        Collections.sort(this.body);
        this.type = type;
        throw new UnsupportedOperationException();
    }

    public static Piece generate(PieceType type) {
        return null;
    }

    public PieceType getType() {
        return this.type;
    }

    public RotationState getState() {
        return this.state;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getBody(int x, int y) {
        return body.get(x).get(y);
    }

    public void setState(RotationState state) {
        this.state = state;
    }

    public int getLowestYVal(int x) {
        return this.lowestYVals.get(x);
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