package sac.model.generators;

import sac.model.Piece;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * PerfectGenerator guarantees the set-up of Perfect Clear.
 *
 * @author Kai
 */
public class PerfectGenerator implements Generator{

    private final Piece.PieceType[] initialPackType = new Piece.PieceType[]{Piece.PieceType.O,
                                                                        Piece.PieceType.L,
                                                                        Piece.PieceType.J,
                                                                        Piece.PieceType.S,
                                                                        Piece.PieceType.Z,
                                                                        Piece.PieceType.T,
                                                                        Piece.PieceType.I};

    private final Piece.PieceType[] followUpType = new Piece.PieceType[]{Piece.PieceType.J,
                                                                    Piece.PieceType.T,
                                                                    Piece.PieceType.I};

    private ArrayList<Piece> initialPack;
    private ArrayList<Piece> followUp;
    private Queue<Piece> pack;

    /**
     * Initialize a set-up pack (including O,L,J,S,Z,T,I once) and a follow-up pack (including J,T,I once).
     */
    public PerfectGenerator() {
        initialPack = new ArrayList<>();
        for (Piece.PieceType type : initialPackType) {
            if (type != Piece.PieceType.Other) {
                initialPack.add(Piece.generate(type));
            }
        }
        followUp = new ArrayList<>();
        for (Piece.PieceType type : followUpType) {
            if (type != Piece.PieceType.Other) {
                followUp.add(Piece.generate(type));
            }
        }
        pack = new ArrayBlockingQueue<>(initialPack.size() + followUp.size());
    }

    @Override
    public Piece nextPiece() {
        if (pack.isEmpty()) {
            Collections.shuffle(initialPack);
            Collections.shuffle(followUp);
            for (Piece piece : initialPack) {
                pack.add(piece);
            }
            for (Piece piece : followUp) {
                pack.add(piece);
            }
        }
        System.out.println(pack.size());
        return pack.poll();
    }

    @Override
    public void reset() {
        pack.clear();
    }

}
