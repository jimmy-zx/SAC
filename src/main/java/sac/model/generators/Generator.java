package sac.model.generators;

import sac.model.Piece;

public interface Generator {
    /**
     * @return the next Piece generated by this generator
     */
    public Piece nextPiece();

    /**
     * Reset this generator for new game.
     */
    public void reset();
}
