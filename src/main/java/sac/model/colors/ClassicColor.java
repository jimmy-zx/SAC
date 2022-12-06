package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

/**
 * The color scheme that a classic Tetris game would use.
 */
public class ClassicColor implements Colorscheme {
    @Override
    public Color render(Piece.PieceType type) {
        switch (type) {
            case I: return Color.LIGHTBLUE;
            case J: return Color.DARKBLUE;
            case L: return Color.ORANGE;
            case O: return Color.YELLOW;
            case S: return Color.GREEN;
            case Z: return Color.RED;
            case T: return Color.MAGENTA;
        }
        return Color.WHITE;
    }

    @Override
    public Color backgroundColor() {
        return Color.GRAY;
    }
}
