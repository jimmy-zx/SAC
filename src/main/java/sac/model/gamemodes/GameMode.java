package sac.model.gamemodes;

import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;
import sac.model.rotations.RotationState;
import sac.model.rotations.RotationSystem;

public abstract class GameMode {
    protected Model model;

    public GameMode(Model model) {
        this.model = model;
    }

    public abstract void onGameStart();

    public abstract boolean isGameEnd();

    public abstract int getScore();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract int getBuffer();

    public abstract Piece nextPiece();

    public abstract Point getSpawnPosition(Piece piece);

    public abstract RotationState getInitialRotationState();

    public abstract void onRowClear();

    public abstract void onInvalidMove();

    public abstract RotationSystem getRotationSystem();  // TODO: Add RotationState.getNextState

    public void initModel() {
        model.setGameMode(this);
    }
}
