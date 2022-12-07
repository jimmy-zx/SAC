package sac.control;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StopMenuController {
    @FXML
    Button resumeButton;

    @FXML
    Button newGameButton;

    @FXML
    Button mainMenuButton;

    @FXML
    Button exitButton;

    MainController mc;

    StopMenuController(MainController mc) {
        this.mc = mc;
    }

    @FXML
    void onResumeButtonClicked() {
//        ((Stage) resumeButton.getScene().getWindow()).hide();
        ControllerFactory.stopMenu.hide();
        mc.paused = false;
        mc.mainPane.requestFocus();
    }

    @FXML
    void onNewGameButtonClicked() {
        ControllerFactory.model.newGame();
        ControllerFactory.model.startGame();
        onResumeButtonClicked();
    }

    @FXML
    void onMainMenuButtonClicked() {
        ControllerFactory.gameStage.close();
        ControllerFactory.gameStage = null;

        ControllerFactory.stopMenu.close();
        ControllerFactory.stopMenu = null;

        ControllerFactory.welcomeMenu.show();
    }

    @FXML
    void onExitButtonClicked() {
        Platform.exit();
    }
}
