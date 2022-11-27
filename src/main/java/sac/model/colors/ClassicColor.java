package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

public class ClassicColor implements Colorscheme {

    @Override
    public Color render(Piece piece) {
        return switch (piece.type) {
            case S -> Color.GREEN;
            case I -> Color.LIGHTBLUE;
            case J -> Color.BLUE;
            case L -> Color.ORANGE;
            case O -> Color.YELLOW;
            case T -> Color.PURPLE;
            case Z -> Color.RED;
            default -> Color.BLACK;
        };
    }
}
