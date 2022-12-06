package sac.model.gamemodes;

import sac.model.generators.Generator;
import sac.model.rotations.RotationSystem;

/**
 * A handy class for quickly customize a game core.
 */
public abstract class CustomizeCore implements GameCore{

    private int width;
    private int height;
    private int buffer;

    private Generator generator;
    private RotationSystem rotationSystem;

    @Override
    public Generator getPieceGenerator() {
        return this.generator;
    }

    @Override
    public RotationSystem getRotationSystem() {
        return rotationSystem;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height + buffer;
    }

    @Override
    public int getBuffer() {
        return buffer;
    }

    /**
     * @param rotationSystem
     * @return this
     */
    public CustomizeCore setRotationSystem(RotationSystem rotationSystem) {
        this.rotationSystem = rotationSystem;
        return this;
    }

    /**
     * @param generator
     * @return this
     */
    public CustomizeCore setGenerator(Generator generator) {
        this.generator = generator;
        return this;
    }

    /**
     * Set the size of the board.
     * @param width
     * @param height - buffer not included
     * @param buffer
     * @return this
     */
    public CustomizeCore setSize(int width, int height, int buffer) {
        this.width = width;
        this.height = height;
        this.buffer = buffer;
        return this;
    }
}
