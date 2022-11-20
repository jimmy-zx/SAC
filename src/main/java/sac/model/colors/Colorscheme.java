package sac.model.colors;

import javafx.scene.paint.Color;
import sac.model.Piece;

public interface Colorscheme {
    Color render(Piece piece);
}
