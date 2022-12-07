package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

/**
 * Define a color scheme.
 */
public interface Colorscheme {

    /**
     * Render different pieces with different color.
     * @param type - the type of piece to be rendered
     * @return the color of current piece
     */
    Color render(Piece.PieceType type);

    /**
     * @return the color of the background (Tetris board)
     */
    Color backgroundColor();
}
