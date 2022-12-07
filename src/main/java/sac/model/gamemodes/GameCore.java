package sac.model.gamemodes;

import sac.model.Piece;
import sac.model.Point;
import sac.model.generators.Generator;
import sac.model.rotations.RotationSystem;

/**
 * Define core features of a game.
 */
public interface GameCore {
    /**
     * @return the pieceGenerator this game would use
     */
    public Generator getPieceGenerator();

    /**
     * @return the rotationSystem this game would use
     */
    public RotationSystem getRotationSystem();

    /**
     * Compute the spawn position of a piece in terms of its left bottom corner.
     *
     * @param piece - current piece spawned
     * @return the spawn position
     */
    public Point getSpawnPosition(Piece piece);

    /**
     * @return width of the board
     */
    public int getWidth();

    /**
     * @return height of the board (buffer included)
     */
    public int getHeight();

    /**
     * @return height of the buffer zone of the board
     */
    public int getBuffer();
}
