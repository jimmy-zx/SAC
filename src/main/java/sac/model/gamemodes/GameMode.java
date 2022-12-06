package sac.model.gamemodes;

import sac.model.Piece;
import sac.model.Point;
import sac.model.generators.Generator;
import sac.model.observers.DataPackage;
import sac.model.rotations.RotationSystem;


import java.util.Stack;

/**
 * Define a game mode.
 * This mode can be customized by replacing its game core or adding game layers.
 * In particular, a game mode can be used as a game core or game layer set to another game mode.
 */
public class GameMode implements GameCore, GameLayer{
    private GameCore core;
    private Stack<GameLayer> layers;

    /**
     * Set the game core and Add the layer.
     *
     * @param core - the game core to be used
     * @param layer - the layer to be added
     */
    public GameMode(GameCore core, GameLayer layer) {
        this.core = core;
        layers = new Stack<>();
        layers.add(layer);
    }

    /**
     * Set the game core and the layers.
     * @param core - the game core to be used
     * @param layers - the layer stack to be used
     */
    public GameMode(GameCore core, Stack<GameLayer> layers) {
        this.core = core;
        this.layers = layers;
    }
    @Override
    public Generator getPieceGenerator() {
        return core.getPieceGenerator();
    }
    @Override
    public RotationSystem getRotationSystem() {
        return core.getRotationSystem();
    }
    @Override
    public Point getSpawnPosition(Piece piece) {
        return core.getSpawnPosition(piece);
    }
    @Override
    public int getWidth() {
        return core.getWidth();
    }
    @Override
    public int getHeight() {
        return core.getHeight();
    }
    @Override
    public int getBuffer() {
        return core.getBuffer();
    }

    /**
     * Template method. Initialize every game layer maintained by this game mode when game starts.
     * Subclass should not override this method.
     */
    @Override
    public final void onGameStart() {
        for (GameLayer layer : layers) {
            layer.onGameStart();
        }
    }

    /**
     * Template method. Check if the game-over condition of every game layer is met.
     * Subclass should not override this method.
     *
     * @return true if the game-over condition of at least one of the game layer is met
     */
    @Override
    public final boolean isGameEnd() {
        for (GameLayer layer : layers) {
            if (layer.isGameEnd()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Template method. Delegate the notifying task to game layers.
     * Subclass should not override this method.
     */
    @Override
    public final void notifyAllObservers(DataPackage dataPackage) {
        if (layers.size() > 0) {
            for (GameLayer layer : layers) {
                layer.notifyAllObservers(dataPackage);
            }
        }
    }

    /**
     * @return the game core this game mode is using
     */
    public GameCore getCore() {
        return core;
    }

    /**
     * Set the game core of this game mode.
     * @param core
     */
    public void setCore(GameCore core) {
        this.core = core;
    }

    /**
     * Add a layer into this game mode.
     * @param layer - the layer to be added
     */
    public void addLayer(GameLayer layer) {
        this.layers.add(layer);
    }

    /**
     * Remove layer from this game mode.
     * @param layer - the layer to be removed
     * @return true iff the removal is successful
     */
    public boolean removeLayer(GameLayer layer) {
        return this.layers.remove(layer);
    }
}
