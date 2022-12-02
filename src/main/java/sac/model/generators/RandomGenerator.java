package sac.model.generators;

import sac.model.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class RandomGenerator implements Generator {

    private ArrayList<Piece> allPieces;
    private Queue<Piece> randomBag;
    private Random random;

    private final Piece.PieceType[] typePool = new Piece.PieceType[]{Piece.PieceType.O,
                                                                    Piece.PieceType.L,
                                                                    Piece.PieceType.J,
                                                                    Piece.PieceType.S,
                                                                    Piece.PieceType.Z,
                                                                    Piece.PieceType.T,
                                                                    Piece.PieceType.I};

    public RandomGenerator() {
        generateAllPieces();
        randomBag = new ArrayBlockingQueue<>(allPieces.size());
        random = new Random();
    }

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
}
