package sac.control;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sac.model.Piece;
import sac.model.Point;

/**
 * This class controls the hold canvas.
 * The size of the canvas is adjusted according to the blockWidth defined by ControllerFactory.
 *
 */
public class HoldController extends FloatController implements UpdatableController{

    @FXML
    VBox canvasVBox;
    @FXML
    Label canvasTitleLabel;
    @FXML
    Canvas canvas;

    private int blockWidth = ControllerFactory.blockWidth; //width of block on display
    private int blockMargin = ControllerFactory.blockMargin;
    private int blockSpace = ControllerFactory.blockSpace;
    private int height;
    private int width;

    private int max_width = 4;

    private VBox container;

    private GraphicsContext gc;

    public HoldController(VBox container) {
        super(container);
    }


    @Override
    protected void init() {
        root = canvasVBox;

        canvasTitleLabel.setText("HOLD");

        this.height = max_width * blockSpace;
        this.width = this.height;

        canvas.setHeight(this.height);
        canvas.setWidth(this.width);

        gc = canvas.getGraphicsContext2D();
    }

}
