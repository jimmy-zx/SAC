package sac.control;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Define a controller whose view element is floating (can be adjusted to different container).
 */
public abstract class FloatController {
    protected VBox container;
    protected Node root;

    public FloatController(VBox container) {
        this.container = container;
    }

    /**
     * Template method.
     * Set the root view element controlled into the container.
     * Subclass should not override this method.
     */
    @FXML
    protected void initialize() {
        init();
        container.getChildren().add(root);
    }

    /**
     * Init current controller.
     */
    protected abstract void init();
}
