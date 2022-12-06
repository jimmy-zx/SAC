package sac.control;

import javafx.stage.Stage;
import sac.model.Model;
import sac.model.colors.Colorscheme;
import sac.model.gamemodes.*;

public class ControllerFactory {

    protected static GameMode gameMode;
    protected static Model model;

    protected static Colorscheme colorscheme;

    protected static ScoreSystemLayer scoreSystemLayer;
    protected static TimedLayer timedLayer;
    protected static LineCountLayer lineCountLayer;

    public static Stage welcomeMenu;
    protected static Stage gameStage;
    protected static Stage stopMenu;

    protected static int blockWidth = 20; //width of block on display
    protected static int blockMargin = 1;
    protected static int blockSpace = blockWidth + 2 * blockMargin;

}
