package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

/**
 * Define a color scheme.
 */
public interface Colorscheme {

    Color render(Piece.PieceType type);

    Color backgroundColor();
}
