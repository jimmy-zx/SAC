package sac.control;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import sac.model.gamemodes.ScoreSystemLayer;

public class ScoreController extends FloatController implements UpdatableController {

    ScoreSystemLayer scoreLayer;

    @FXML
    VBox messageVBox;
    @FXML
    Label messageTitleLabel;
    @FXML
    Label messageLabel;

    public ScoreController(VBox container) {
        super(container);
    }

    protected void setScoreLayer(ScoreSystemLayer scoreLayer) {
        this.scoreLayer = scoreLayer;
    }

    @Override
    protected void init() {
        root = messageVBox;
//        messageVBox.setPrefHeight(Math.max(container.getHeight(), messageVBox.getHeight()));
//        messageVBox.setPrefWidth(Math.max(container.getWidth(), messageVBox.getWidth()));

        messageTitleLabel.setText("SCORE");
        messageLabel.setText("0");

        scoreLayer = ControllerFactory.scoreSystemLayer;
        ControllerFactory.gameMode.addLayer(scoreLayer);
    }

    @Override
    public void update() {
        messageLabel.setText("" + scoreLayer.getScore());
    }
}
