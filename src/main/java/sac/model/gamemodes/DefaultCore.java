package sac.model.gamemodes;

import sac.model.Piece;
import sac.model.Point;
import sac.model.generators.Generator;
import sac.model.generators.RandomGenerator;
import sac.model.rotations.RotationSystem;
import sac.model.rotations.SuperRotationSystem;

/**
 * The default game core used by classic Tetris game mode.
 */
public class DefaultCore implements GameCore{
    private Generator defaultGenerator = new RandomGenerator();
    private RotationSystem defaultRotationSystem = new SuperRotationSystem();
    private int defaultWidth = 10;
    private int defaultHeight = 20;
    private int defaultBuffer = 4;

    @Override
    public Generator getPieceGenerator() {
        return defaultGenerator;
    }

    @Override
    public RotationSystem getRotationSystem() {
        return defaultRotationSystem;
    }

    /**
     * Spawn the piece in the middle of the board.
     *
     * @param piece - current piece spawned
     * @return the spawn position
     */
    @Override
    public Point getSpawnPosition(Piece piece) {
        return new Point((getWidth() - piece.width)/2, getHeight() - piece.height);
    }

    @Override
    public int getWidth() {
        return defaultWidth;
    }

    @Override
    public int getHeight() {
        return defaultHeight + defaultBuffer;
    }

    @Override
    public int getBuffer() {
        return defaultBuffer;
    }
}
