package sac.control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sac.App;
import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;
import sac.model.colors.ClassicColor;
import sac.model.colors.HighContrastColor;
import sac.model.colors.SplatoonColor;
import sac.model.gamemodes.*;
import sac.model.generators.Generator;
import sac.model.generators.PerfectGenerator;
import sac.model.generators.RandomGenerator;
import sac.model.observers.BackToBackObserver;
import sac.model.observers.ComboObserver;
import sac.model.observers.LinearObserver;
import sac.model.observers.TSpinObserver;
import sac.model.rotations.BasicRotationSystem;
import sac.model.rotations.RotationSystem;
import sac.model.rotations.SuperRotationSystem;

import java.io.IOException;
import java.util.Stack;

public class WelcomeMenuController {
    @FXML
    ToggleGroup generator;

    @FXML
    ToggleGroup rotation;

    @FXML
    ToggleGroup mode;

    @FXML
    ToggleGroup color;

    @FXML
    TextField widthField;
    @FXML
    TextField heightField;
    @FXML
    TextField bufferField;


    @FXML
    ToggleButton lineScore;
    @FXML
    ToggleButton tSpinScore;
    @FXML
    ToggleButton backToBackScore;
    @FXML
    ToggleButton comboScore;

    @FXML
    ToggleButton lineCountToggle;
    @FXML
    ToggleButton timerToggle;

    @FXML
    TextField lineCountField;
    @FXML
    TextField timerField;

    @FXML
    Button startButton;

    void init() {

        GameCore gameCore = new DefaultCore();

        switch (((ToggleButton) mode.getSelectedToggle()).getText()) {
            case "Default" -> {}
            case "Custom" -> gameCore = customGameCore();
        }

        ScoreSystemLayer scoreLayer = new ScoreSystemLayer();

        if (lineScore.isSelected()) scoreLayer.addScoreObserver(new LinearObserver());
        if (tSpinScore.isSelected()) scoreLayer.addScoreObserver(new TSpinObserver());
        if (backToBackScore.isSelected()) scoreLayer.addScoreObserver(new BackToBackObserver());
        if (comboScore.isSelected()) scoreLayer.addScoreObserver(new ComboObserver());

        ControllerFactory.scoreSystemLayer = scoreLayer;

        if (timerToggle.isSelected()) {
            TimedLayer timedLayer = new TimedLayer();
            timedLayer.setTimer(timerField.getText());
            ControllerFactory.timedLayer = timedLayer;
        }

        if (lineCountToggle.isSelected()) {
            LineCountLayer lineCountLayer = new LineCountLayer();
            lineCountLayer.setTotal(Integer.parseInt(lineCountField.getText()));
            ControllerFactory.lineCountLayer = lineCountLayer;
        }

        ControllerFactory.model = new Model();
        ControllerFactory.gameMode = new GameMode(gameCore, new Stack<>());
        ControllerFactory.model.setGameMode(ControllerFactory.gameMode);
        ControllerFactory.model.newGame();

        setColorScheme();
    }

    GameCore customGameCore() {
        int width = Integer.parseInt(widthField.getText());
        int height = Integer.parseInt(heightField.getText());
        int buffer = Integer.parseInt(bufferField.getText());

        Generator pieceGenerator = new RandomGenerator();
        RotationSystem rotationSystem = new SuperRotationSystem();

        switch (((ToggleButton) generator.getSelectedToggle()).getText()) {
            case "Random" -> {}
            case "Perfect" -> pieceGenerator = new PerfectGenerator();
        }

        switch (((ToggleButton) rotation.getSelectedToggle()).getText()) {
            case "Super" -> {}
            case "Basic" -> rotationSystem = new BasicRotationSystem();
        }

        CustomizeCore gameCore = new CustomizeCore() {
            @Override
            public Point getSpawnPosition(Piece piece) {
                return new Point((getWidth() - piece.width)/2, getHeight() - piece.height);
            }
        };

        gameCore.setSize(width, height, buffer).setRotationSystem(rotationSystem).setGenerator(pieceGenerator);

        return gameCore;
    }

    void setColorScheme() {
        switch (((ToggleButton) color.getSelectedToggle()).getText()) {
            case "Classic" -> ControllerFactory.colorscheme = new ClassicColor();
            case "Splatoon" -> ControllerFactory.colorscheme = new SplatoonColor();
            case "High Contrast" -> { ControllerFactory.colorscheme = new HighContrastColor(); }
        }
    }

    @FXML
    void onStartButtonClicked() throws IOException {
//        System.out.println(((ToggleButton) generator.getSelectedToggle()).getText());
        init();
        ((Stage) startButton.getScene().getWindow()).hide();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MainView.fxml"));
//
        MainController mc = new MainController();
        fxmlLoader.setController(mc);

        Stage stage = new Stage();

        stage.setScene(new Scene(fxmlLoader.load()));

        stage.setTitle("Tetris");

        ControllerFactory.gameStage = stage;

        stage.show();
    }
}
