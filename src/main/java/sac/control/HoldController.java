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

    private final int yPixel(int y) {
        return (this.height - (y+1) * blockSpace) + blockMargin;
    }
    private final int xPixel(int x) {
        return x * blockSpace + blockMargin;
    }

    /**
     * Redraw the hold canvas as model ticks.
     */
    @Override
    public void update() {
        gc.setFill(Color.valueOf("#2a2f33"));
        gc.fillRect(0, 0, width, height);

        if (ControllerFactory.model.getHoldPiece() != null) {
            Piece holdPiece = Piece.generate(ControllerFactory.model.getHoldPiece());
            gc.setFill(ControllerFactory.colorscheme.render(holdPiece.type));

            final float xOffset = (float) width / 2 - (float) (holdPiece.width * blockSpace) / 2 ;
            final float yOffset = (float) height / 2 - (float) (holdPiece.height * blockSpace) / 2 ;

            for (Point position : holdPiece.body) {
                gc.fillRect(xPixel(position.x()) + xOffset, yPixel(position.y()) - yOffset, blockWidth, blockWidth);
            }
        }
    }
}
