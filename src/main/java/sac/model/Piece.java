package sac.model;

import jdk.jshell.spi.ExecutionControl;
import sac.model.rotations.RotationState;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

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
    private PieceType type;
    private RotationState state;

    /* sorted */
    private ArrayList<ArrayList<Boolean>> body;

    private ArrayList<Integer> lowestYVals;
    private Map<PieceType, ArrayList<Piece>> cache;
    private int width, height;

    public Piece(PieceType type, ArrayList<ArrayList<Boolean>> body) {
        this.body = body;
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
        return this.state.rotateLeft();
    }

    public Piece rotateRight() {
        return this.state.rotateRight();
    }

    public String toString() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}