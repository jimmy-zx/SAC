package sac.model.colors;

import org.junit.jupiter.api.Test;
import sac.model.Piece;
import sac.model.colors.ClassicColor;
import sac.model.colors.Colorscheme;
import sac.model.colors.HighContrastColor;
import sac.model.colors.SplatoonColor;

import static org.junit.jupiter.api.Assertions.*;

public class ColorTest {
    @Test
    void testClassic() {
        exhaustiveTest(new ClassicColor());
    }

    @Test
    void testHighContrast() {
        exhaustiveTest(new HighContrastColor());
    }

    @Test
    void testSplatoon() {
        exhaustiveTest(new SplatoonColor());
    }

    private void exhaustiveTest(Colorscheme colorscheme) {
        for (Piece.PieceType type : Piece.PieceType.values()) {
            if (type == Piece.PieceType.Other) {
                continue;
            }
            assertNotNull(colorscheme.render(type));
        }
        assertNotNull(colorscheme.render(null));
    }
}
