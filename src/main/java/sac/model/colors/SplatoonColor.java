package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

/**
 * A color scheme inspired by Splatoon 3.
 */
public class SplatoonColor implements Colorscheme{
    @Override
    public Color render(Piece.PieceType type) {
        switch (type) {
            case I: return Color.valueOf("#15fefd");
            case J: return Color.valueOf("#0e5cfc");
            case L: return Color.valueOf("#fe860e");
            case O: return Color.valueOf("#ffff14");
            case S: return Color.valueOf("#59fe3e");
            case Z: return Color.valueOf("#ff49a0");
            case T: return Color.valueOf("#cc4bfe");
        }
        return Color.WHITE;
    }

    @Override
    public Color backgroundColor() {
        return Color.valueOf("#2c2c2c");
    }
}
