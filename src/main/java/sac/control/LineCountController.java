package sac.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sac.model.gamemodes.LineCountLayer;

/**
 * Control the message pane for showing the number of lines cleared.
 */
public class LineCountController extends FloatController implements UpdatableController {

    LineCountLayer lineCountLayer;

    @FXML
    VBox messageVBox;
    @FXML
    Label messageTitleLabel;
    @FXML
    Label messageLabel;

    public LineCountController(VBox container) {
        super(container);
    }


    @Override
    protected void init() {
        root = messageVBox;

        messageTitleLabel.setText("Line");
        messageLabel.setText("0");

        lineCountLayer = ControllerFactory.lineCountLayer;
        ControllerFactory.gameMode.addLayer(lineCountLayer);
    }

    @Override
    public void update() {
        messageLabel.setText("" + lineCountLayer.getCnt());
    }
}

