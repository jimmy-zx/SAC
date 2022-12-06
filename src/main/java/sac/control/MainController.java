package sac.control;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sac.App;
import sac.model.Model;
import sac.model.Point;
import sac.model.colors.Colorscheme;
import sac.model.gamemodes.GameMode;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainController {

    GameMode gameMode;
    Model model;

    int height;
    int width;
    protected boolean paused;

    private int blockWidth = ControllerFactory.blockWidth; //width of block on display
    private int blockMargin = ControllerFactory.blockMargin;
    private int blockSpace = ControllerFactory.blockSpace;

    @FXML
    protected Canvas mainCanvas;

    @FXML
    protected BorderPane mainPane;

    @FXML
    VBox leftVBox;

    @FXML
    VBox rightVBox;

//    @FXML
//    Button testButton;

    Timeline timeline;

    GraphicsContext gc;

    Colorscheme colorscheme;

    HoldController hc;

    List<UpdatableController> updatableControllers;

    @FXML
    private void initialize() {
        gameMode = ControllerFactory.gameMode;
        model = ControllerFactory.model;

        height = gameMode.getHeight() * blockSpace;
        width = gameMode.getWidth() * blockSpace;

        mainCanvas.setHeight(height);
        mainCanvas.setWidth(width);

        gc = mainCanvas.getGraphicsContext2D();

        updatableControllers = new ArrayList<>();


        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("SubCanvas.fxml"));
        hc = new HoldController(leftVBox);
//        mc.setGameMode(gameMode, model);
        fxmlLoader.setController(hc);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updatableControllers.add(hc);

        fxmlLoader = new FXMLLoader(App.class.getResource("SubCanvas.fxml"));
        PreviewController pc = new PreviewController(rightVBox);
//        mc.setGameMode(gameMode, model);
        fxmlLoader.setController(pc);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updatableControllers.add(pc);


        if (ControllerFactory.scoreSystemLayer != null) {
            ScoreController c = new ScoreController(leftVBox);
            addFloatController(c);
        }
        if (ControllerFactory.timedLayer != null) {
            TimedController c = new TimedController(leftVBox);
            addFloatController(c);
        }
        if (ControllerFactory.lineCountLayer != null) {
            LineCountController c = new LineCountController(leftVBox);
            addFloatController(c);
        }


        colorscheme = ControllerFactory.colorscheme;
        paused = false;

        model.startGame();

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.25), e -> updateBoard()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        mainPane.requestFocus();
//        timeLabel.setText("init");
//        scoreLabel.setText("" + gameMode.getHeight());
    }

    
    @FXML
    private void onMainPaneKeyPressed(KeyEvent k){
        switch (k.getCode()) {
            case W -> {
                model.modelTick(Model.MoveType.HARD_DROP);
            }
            case A -> model.modelTick(Model.MoveType.LEFT);
            case S -> model.modelTick(Model.MoveType.DOWN);
            case D -> model.modelTick(Model.MoveType.RIGHT);
            case J -> model.modelTick(Model.MoveType.ROTATE_LEFT);
            case K -> model.modelTick(Model.MoveType.ROTATE_RIGHT);
            case H -> {
                model.modelTick(Model.MoveType.HOLD);
                hc.update();
            }
            case R -> {
                model.newGame();
                model.startGame();
                mainPane.requestFocus();
                paused = false;
            }
            case P -> paused = !paused;
            case ESCAPE -> openStopMenu();
        }
        paintBoard();
    }

    private void updateBoard() {
        if (this.paused != true) {
            paintBoard();
            this.model.modelTick(Model.MoveType.DOWN);
            for (UpdatableController c : updatableControllers) {
                c.update();
            }

            if (!model.isGameOn()) {
                paused = true;
            }
        }
    }


}
