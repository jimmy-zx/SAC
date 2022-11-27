package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

public class HighContrastColor implements Colorscheme{

    @Override
    public Color render(Piece piece) {
        return switch (piece.type) {
            case S -> Color.rgb(105,255,50);
            case I -> Color.rgb(100,205,255);
            case J -> Color.BLUE;
            case L -> Color.rgb(255,135,0);
            case O -> Color.rgb(235, 235, 0);
            case T -> Color.rgb(128,0,255);
            case Z -> Color.RED;
            default -> Color.BLACK;
        };
    }
}
