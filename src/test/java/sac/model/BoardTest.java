package sac.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    void testInit() {
        Board board = new Board(10, 24);
        assertEquals(10, board.width);
        assertEquals(24, board.height);
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.width; y++) {
                assertNull(board.getGrid(new Point(x, y)));
            }
        }
    }

    @Test
    void testIsValidPoint() {
        Board board = new Board(10, 24);
        assertTrue(board.isValidPoint(new Point(0, 0)));
        assertTrue(board.isValidPoint(new Point(9, 23)));
        assertTrue(board.isValidPoint(new Point(1, 1)));
        assertFalse(board.isValidPoint(new Point(-1, -1)));
        assertFalse(board.isValidPoint(new Point(10, 24)));
    }

    private static void checkPoints(Set<Point> points, Board board, boolean iff) {
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.width; y++) {
                Point point = new Point(x, y);
                if (points.contains(point)) {
                    assertNotNull(board.getGrid(point));
                } else if (iff) {
                    assertNull(board.getGrid(point));
                }
            }
        }
    }

    @Test
    void testPlacePiece() {
        Board board = new Board(10, 24);
        Piece piece = Piece.generate(Piece.PieceType.O);
        HashSet<Point> points = new HashSet<>(List.of(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)));

        assertEquals(Board.PlacePieceStatus.ADD_OUT_BOUNDS, board.placePiece(piece, new Point(9, 0)));
        checkPoints(new HashSet<>(), board, true);

        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(piece, new Point(0, 0)));
        checkPoints(points, board, true);

        assertEquals(Board.PlacePieceStatus.ADD_BAD, board.placePiece(piece, new Point(1, 0)));
        checkPoints(points, board, true);
    }

    @Test
    void testClearRowsFull() {
        Board board = new Board(4, 4);
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 0)));
        assertEquals(Board.PlacePieceStatus.ADD_ROW_FILLED, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(2, 0)));
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 2)));
        assertEquals(Board.PlacePieceStatus.ADD_ROW_FILLED, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(2, 2)));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(Piece.PieceType.O, board.getGrid(new Point(i, j)));
            }
        }
        assertEquals(4, board.clearRows());
        checkPoints(new HashSet<>(), board, true);
    }

    @Test
    void testClearRowSingle() {
        Board board = new Board(4, 4);
        assertEquals(Board.PlacePieceStatus.ADD_ROW_FILLED, board.placePiece(Piece.generate(Piece.PieceType.I), new Point(0, 0)));
        for (int i = 0; i < 4; i++) {
            assertEquals(Piece.PieceType.I, board.getGrid(new Point(i, 0)));
        }
        assertEquals(1, board.clearRows());
        checkPoints(new HashSet<>(), board, true);
    }

    @Test
    void testClearRowPullDown() {
        Board board = new Board(4, 4);
        assertEquals(Board.PlacePieceStatus.ADD_ROW_FILLED, board.placePiece(Piece.generate(Piece.PieceType.I), new Point(0, 0)));
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 1)));
        assertEquals(1, board.clearRows());
        HashSet<Point> points = new HashSet<>(List.of(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)));
        checkPoints(points, board, true);
    }

    @Test
    void testClearRowEmpty() {
        Board board = new Board(4, 4);
        assertEquals(0, board.clearRows());
        checkPoints(new HashSet<>(), board, true);
    }

    @Test
    void testClearRowNoFilled() {
        Board board = new Board(4, 4);
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 0)));
        assertEquals(0, board.clearRows());
        HashSet<Point> points = new HashSet<>(List.of(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)));
        checkPoints(points, board, true);
    }

    @Test
    void testBackup() {
        Board board = new Board(4, 4);
        board.backup();
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 0)));
        board.undo();
        checkPoints(new HashSet<>(), board, true);
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 0)));
        board.backup();
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.O), new Point(0, 2)));
        board.undo();
        HashSet<Point> points = new HashSet<>(List.of(new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1)));
        checkPoints(points, board, true);
    }

    @Test
    void testDropPosition() {
        Board board = new Board(4, 20);
        assertEquals(Board.PlacePieceStatus.ADD_OK, board.placePiece(Piece.generate(Piece.PieceType.L), new Point(0, 0)));
        assertEquals(new Point(0, 1), board.dropPosition(Piece.generate(Piece.PieceType.O), new Point(0, 10)));
    }
}
