package sac.model.gamemodes;

import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;
import sac.model.generators.Generator;
import sac.model.generators.RandomGenerator;
import sac.model.rotations.RotationState;
import sac.model.rotations.RotationSystem;
import sac.model.rotations.SuperRotationSystem;

public class ClassicGameMode extends GameMode {
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;
    public static final int BUFFER = 4;

    private Generator pieceGenerator;
    private RotationSystem rotationSystem;

    public ClassicGameMode(Model model) {
        super(model);
        pieceGenerator = new RandomGenerator();
        rotationSystem = new SuperRotationSystem();
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public boolean isGameEnd() {
        return !model.isGameOn();
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public int getBuffer() {
        return BUFFER;
    }

    @Override
    public Piece nextPiece() {
        return pieceGenerator.nextPiece();
    }

    @Override
    public Point getSpawnPosition(Piece.PieceType type) {
        return null;
    }

    @Override
    public RotationState getInitialRotationState() {
        return rotationSystem.getInitialState();
    }

    @Override
    public void onRowClear() {
    }

    @Override
    public RotationSystem getRotationSystem() {
        return rotationSystem;
    }
}
