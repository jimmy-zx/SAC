package sac.model.generators;

import org.junit.jupiter.api.Test;
import sac.model.Piece;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class RandomGeneratorTest {
    @Test
    void testRandomGenerator() {
        RandomGenerator generator = new RandomGenerator();
        HashSet<Piece> currentPieces = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            if (i % 7 == 0) {
                currentPieces.clear();
            }
            Piece piece = generator.nextPiece();
            assertFalse(currentPieces.contains(piece));
            currentPieces.add(piece);
        }
        generator.reset();
        for (int i = 0; i < 100; i++) {
            if (i % 7 == 0) {
                currentPieces.clear();
            }
            Piece piece = generator.nextPiece();
            assertFalse(currentPieces.contains(piece));
            currentPieces.add(piece);
        }
    }
}
