package sac.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sac.model.gamemodes.TimedLayer;

/**
 * Control the message pane for showing the elapsed time.
 */
public class TimedController extends FloatController implements UpdatableController {

    TimedLayer timedLayer;

    @FXML
    VBox messageVBox;
    @FXML
    Label messageTitleLabel;
    @FXML
    Label messageLabel;

    public TimedController(VBox container) {
        super(container);
    }

    @Override
    protected void init() {
        root = messageVBox;

        messageTitleLabel.setText("Time");
        messageLabel.setText("00:00");

        timedLayer = ControllerFactory.timedLayer;
        ControllerFactory.gameMode.addLayer(timedLayer);
    }

    @Override
    public void update() {
        messageLabel.setText(timedLayer.getCurrentTime());
    }
}
