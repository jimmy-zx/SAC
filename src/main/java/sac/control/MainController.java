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


    private final int yPixel(int y) {
        return (this.height - (y+1) * blockSpace) + blockMargin;
    }
    private final int xPixel(int x) {
        return x * blockSpace + blockMargin;
    }

    private void paintBlock(Point position) {
        gc.fillRect(xPixel(position.x()), yPixel(position.y()), blockWidth, blockWidth);
    }

    /**
     * Draw the board
     */
    private void paintBoard() {

        // Draw a rectangle around the whole screen
        gc.setStroke(Color.GRAY);
        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, this.width, this.height);

        // Draw the line separating the top area on the screen
        gc.setFill(Color.BLACK);
        int spacerY = yPixel(this.gameMode.getHeight() - this.gameMode.getBuffer() - 1) - 2 * blockMargin;
//        System.out.println(spacerY);
        gc.fillRect(0, spacerY, this.width, 2 * blockMargin);


        /**
         * draw ghost piece
         */
        for (Point ghostPoint : model.getGhostPiecePositions()) {
            gc.setFill(Color.WHEAT);
            paintBlock(ghostPoint);
            gc.setFill(Color.GRAY);
        }

        int x, y;
        // Loop through and draw all the blocks; sizes of blocks are calibrated relative to screen size
        for (x=0; x<gameMode.getWidth(); x++) {
            // draw from 0 up to the col height
//            final int yHeight = this.model.getBoard().getColumnHeight(x);
            for (y=0; y<gameMode.getHeight(); y++) {
                if (this.model.board.getGrid(new Point(x, y)) != null) {
                    gc.setFill(colorscheme.render(this.model.board.getGrid(new Point(x, y))));
                    paintBlock(new Point(x, y));
                    gc.setFill(Color.GRAY);
                }
            }
        }
    }

    @FXML
    ImageView menuIcon;

    @FXML
    private void onMenuMouseEntered() {
        Bloom bloom = new Bloom(0.1);
        menuIcon.setEffect(bloom);
    }

    @FXML
    private void onMenuMouseExited() {
        menuIcon.setEffect(null);
    }

    @FXML
    private void onMenuMouseClicked() {
        openStopMenu();
    }

    private void openStopMenu() {
        this.paused = true;
        if (ControllerFactory.stopMenu == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("StopMenu.fxml"));
            StopMenuController stopMenuController = new StopMenuController(this);
            fxmlLoader.setController(stopMenuController);
            try {
                Stage stopMenu = new Stage();
                stopMenu.setScene(new Scene(fxmlLoader.load()));
                stopMenu.setTitle("PAUSED");
                stopMenu.show();
                ControllerFactory.stopMenu = stopMenu;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            ControllerFactory.stopMenu.show();
        }
    }

    private void addFloatController (FloatController fc) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MessagePane.fxml"));
        fxmlLoader.setController(fc);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (fc instanceof UpdatableController) {
            updatableControllers.add((UpdatableController) fc);
        }
    }

    
}
