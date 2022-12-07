package sac.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {
    @Test
    void testPieceGenerate() {
        for (Piece.PieceType type : Piece.PieceType.values()) {
            if (type == Piece.PieceType.Other) {
                continue;
            }
            Piece.generate(type);
        }
    }

    @Test
    void testPieceAttrs() {
        Piece piece = Piece.generate(Piece.PieceType.O);
        assertEquals(Piece.PieceType.O, piece.type);
        assertEquals(2, piece.height);
        assertEquals(2, piece.width);
        assertEquals(List.of(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)), piece.body);
        assertEquals(List.of(0, 0), piece.lowestYVals);
    }

    @Test
    void testPieceRotateRight() {
        Piece piece = Piece.generate(Piece.PieceType.L);
        Piece rot1 = piece.rotateRight();
        assertEquals(List.of(new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 0)), rot1.body);
    }

    @Test
    void testPieceRotateLeft() {
        Piece piece = Piece.generate(Piece.PieceType.L);
        Piece rot1 = piece.rotateLeft();
        assertEquals(List.of(new Point(0, 2), new Point(1, 0), new Point(1, 1), new Point(1, 2)), rot1.body);
    }

    @Test
    void testEquals() {
        Piece piece = Piece.generate(Piece.PieceType.L);
        assertEquals(piece, piece.rotateLeft().rotateRight());
    }

    @Test
    void testToString() {
        Piece piece = Piece.generate(Piece.PieceType.L);
        assertTrue(piece.toString().length() > 0);
    }
}
