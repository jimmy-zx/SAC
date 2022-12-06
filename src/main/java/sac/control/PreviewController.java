package sac.control;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sac.model.Piece;
import sac.model.Point;

import java.util.ArrayDeque;

/**
 * This class controls the piece preview canvas.
 * The size of the canvas is adjusted according to the number of the piece previewed.
 */
public class PreviewController implements UpdatableController{

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

    private int previewNumber = 5;
    private ArrayDeque<Piece> preview;

    /**
     * The max width among all pieces.
     * Open to modification.
     */
    private int max_width = 4;

    private VBox container;

    private GraphicsContext gc;

    public PreviewController(VBox container) {
        this.container = container;
    }

    @FXML
    protected void initialize() {
        canvasTitleLabel.setText("NEXT");

        this.width = max_width * blockSpace;
        this.height = this.width * previewNumber;

        canvas.setHeight(this.height);
        canvas.setWidth(this.width);

        gc = canvas.getGraphicsContext2D();

        this.container.getChildren().add(canvasVBox);
    }

    private final int yPixel(int y) {
        return (this.width - (y+1) * blockSpace) + blockMargin; // using width which will be offset later.
    }
    private final int xPixel(int x) {
        return x * blockSpace + blockMargin;
    }

    /**
     * Redraw the preview pieces as model ticks.
     */
    @Override
    public void update() {
        gc.setFill(Color.valueOf("#2a2f33"));
        gc.fillRect(0, 0, width, height);

        preview = ControllerFactory.model.getPreview();

        for(int i = 0; i < previewNumber; i++) {
            Piece piece = preview.poll();
            gc.setFill(ControllerFactory.colorscheme.render(piece.type));

            float xOffset = (float) this.width / 2 - (float) (piece.width * blockSpace) / 2;
            float yOffset = (float) this.width / 2 - (float) (piece.height * blockSpace) / 2;

            for (Point position : piece.body) {
                gc.fillRect(xPixel(position.x()) + xOffset, yPixel(position.y()) - yOffset + (i * this.width), blockWidth, blockWidth);
            }
        }
    }
}
