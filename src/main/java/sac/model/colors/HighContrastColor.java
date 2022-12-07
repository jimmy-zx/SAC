package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;


/**
 * A color scheme with high contrast.
 */
public class HighContrastColor implements Colorscheme{

    @Override
    public Color render(Piece.PieceType type) {
        if (type == null) {
            return backgroundColor();
        }
        switch (type) {
            case S : return Color.rgb(105,255,50);
            case I : return Color.rgb(100,205,255);
            case J : return Color.BLUE;
            case L : return Color.rgb(255,135,0);
            case O : return Color.rgb(235, 235, 0);
            case T : return Color.rgb(128,0,255);
            case Z : return Color.RED;
            default : return Color.BLACK;
        }
    }

    @Override
    public Color backgroundColor() {
        return Color.valueOf("#a4b0be");
    }
}
