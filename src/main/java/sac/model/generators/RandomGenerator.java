package sac.model.generators;

import sac.model.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * The generator classic Tetris would use.
 * Random Generator generates a sequence of all seven one-sided tetrominoes (I, J, L, O, S, T, Z) permuted randomly,
 * as if they were drawn from a bag.
 *
 * @author Kai
 */
public class RandomGenerator implements Generator {

    /**
     * Define possible piece types.
     */
    private final Piece.PieceType[] typePool = new Piece.PieceType[]{Piece.PieceType.O,
                                                                    Piece.PieceType.L,
                                                                    Piece.PieceType.J,
                                                                    Piece.PieceType.S,
                                                                    Piece.PieceType.Z,
                                                                    Piece.PieceType.T,
                                                                    Piece.PieceType.I};

    /**
     * Store all possible pieces generate from typePool.
     */
    private ArrayList<Piece> allPieces;

    /**
     * A random bag that stores
     */
    private Queue<Piece> randomBag;


    /**
     * Initialize a random bag with size equal the number of possible piece types (7 in classic Tetris).
     */
    public RandomGenerator() {
        generateAllPieces();
        randomBag = new ArrayBlockingQueue<>(allPieces.size());
    }

    /**
     * Generate all pieces from typePool and store them in allPieces.
     */
    private void generateAllPieces() {
        allPieces = new ArrayList<>();
        for (Piece.PieceType type : typePool) {
            if (type != Piece.PieceType.Other) {
                 allPieces.add(Piece.generate(type));
            }
        }
    }

    @Override
    public Piece nextPiece() {
        if (randomBag.isEmpty()) {
            Collections.shuffle(allPieces);
            for (Piece piece : allPieces) {
                randomBag.add(piece);
            }
        }
        return randomBag.poll();
    }

    @Override
    public void reset() {
        randomBag.clear();
    }
}
