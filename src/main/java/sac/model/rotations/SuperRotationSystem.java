package sac.model.rotations;

import sac.model.Model;
import sac.model.Piece;
import sac.model.Point;
import sac.utils.Utility;

import java.util.*;

public class SuperRotationSystem implements RotationSystem{

    private static final String I_TRUE_ROTATION = "1 0 0 2 2 0 0 1"; // ORZL order
    private static final String JLSTZ_TRUE_ROTATION = "1 0 0 1 1 1 1 1";
    private static final String O_TRUE_ROTATION = "0 0 0 1 1 1 1 0";

    private static final String I_OFFSET[] = {"0 0 -1 0 2 0 -1 0 2 0",
                                            "-1 0 0 0 0 0 0 1 0 -2",
                                            "-1 1 1 1 -2 1 1 0 -2 0",
                                            "0 1 0 1 0 1 0 -1 0 2"};
    private static final String JLSTZ_OFFSET[] = {"0 0 0 0 0 0 0 0 0 0",
                                                "0 0 1 0 1 -1 0 2 1 2",
                                                "0 0 0 0 0 0 0 0 0 0",
                                                "0 0 -1 0 -1 -1 0 2 -1 2"};
    private static final String O_OFFSET[] = {"0 0", "0 -1", "-1 -1", "-1 0"};

    private static final SuperRotationState[] superRotationStates = SuperRotationState.values();


    @Override
    public RotationState getInitialState() {
        SuperRotationState.OState.resetTestCnt();
        return SuperRotationState.OState;
    }

    @Override
    public RotationState getNextState(RotationState currentState, Model.MoveType moveType) {
        SuperRotationState nextState = computeNextSuperRotationState((SuperRotationState) currentState, moveType);
        nextState.resetTestCnt();
        return nextState;
    }

    @Override
    public RotationState restore(RotationState currentState) {
        ((SuperRotationState) currentState).resetTestCnt();
        return currentState;
    }


    public enum SuperRotationState implements RotationState {
        OState, // spawn state
        RState, // state resulting from a clockwise rotation ("right") from spawn
        ZState, // state resulting from a counter-clockwise ("left") rotation from spawn
        LState; // state resulting from 2 successive rotations in either direction from spawn.

        private int testCnt;
        @Override
        public Point getRotationOffset(Piece activePiece, Model.MoveType moveType) {
            SuperRotationState nextState = computeNextSuperRotationState(this, moveType);
            Point offset = getOffsetTestData(activePiece.type, this, nextState, testCnt);
            testCnt++;
            if (activePiece.type == Piece.PieceType.O) {
                testCnt = 5;
            }
            System.out.println(this.name() + ": " + testCnt);
            return offset;
        }

        @Override
        public boolean hasNextTest() {
            return testCnt < 5;
        }

        public void resetTestCnt() {
            this.testCnt = 0;
        }

        public int getTestCnt() {return this.testCnt;}

    }
    private static Map<Piece.PieceType, Map<SuperRotationState, Point>> trueRotationCenter;
    private static Map<Piece.PieceType, Map<SuperRotationState, List<Point>>> offsetData;

    private Model model;

    public SuperRotationSystem() {

    }

    public static SuperRotationState computeNextSuperRotationState(SuperRotationState currentState, Model.MoveType moveType) {
        int transition = 0;
        switch (moveType) {
            case ROTATE_LEFT -> transition = -1;
            case ROTATE_RIGHT -> transition = 1;
        }
        int targetOrdinal = currentState.ordinal() + transition;
        targetOrdinal = (targetOrdinal + 4) % 4;
        return superRotationStates[targetOrdinal];
    }

    public static Point getOffsetTestData(Piece.PieceType type, SuperRotationState src, SuperRotationState dest, int testCnt) {
        if (offsetData == null) {
            offsetData = new HashMap<>();

            Map<SuperRotationState, List<Point>> inside = new HashMap<>();
            int cnt = 0;
            for (SuperRotationState state : SuperRotationState.values()) {
                inside.put(state, Utility.parsePointsString(I_OFFSET[cnt]));
                cnt++;
            }
            offsetData.put(Piece.PieceType.I, inside);

            inside = new HashMap<>();
            cnt = 0;
            for (SuperRotationState state : SuperRotationState.values()) {
                inside.put(state, Utility.parsePointsString(JLSTZ_OFFSET[cnt]));
                cnt++;
            }
            offsetData.put(Piece.PieceType.J, inside);
            offsetData.put(Piece.PieceType.L, inside);
            offsetData.put(Piece.PieceType.S, inside);
            offsetData.put(Piece.PieceType.T, inside);
            offsetData.put(Piece.PieceType.Z, inside);

            inside = new HashMap<>();
            cnt = 0;
            for (SuperRotationState state : SuperRotationState.values()) {
                inside.put(state, Utility.parsePointsString(O_OFFSET[cnt]));
                cnt++;
            }
            offsetData.put(Piece.PieceType.O, inside);
        }
        Point offset1 = offsetData.get(type).get(src).get(testCnt).diff(offsetData.get(type).get(dest).get(testCnt));
        Point offset2 = getTrueRotationOffset(type, src, dest);
        return offset1.offset(offset2);
    }

    public static Point getTrueRotationOffset(Piece.PieceType type, SuperRotationState src, SuperRotationState dest) {
        if (trueRotationCenter == null) {
            trueRotationCenter = new HashMap<>();

            ArrayList<Point> list = Utility.parsePointsString(I_TRUE_ROTATION);
            Map<SuperRotationState, Point> inside = new HashMap<>();
            int cnt = 0;
            for (SuperRotationState state : SuperRotationState.values()) {
                inside.put(state, list.get(cnt));
                cnt++;
            }
            trueRotationCenter.put(Piece.PieceType.I, inside);

            list = Utility.parsePointsString(JLSTZ_TRUE_ROTATION);
            inside = new HashMap<>();
            cnt = 0;
            for (SuperRotationState state : SuperRotationState.values()) {
                inside.put(state, list.get(cnt));
                cnt++;
            }
            trueRotationCenter.put(Piece.PieceType.J, inside);
            trueRotationCenter.put(Piece.PieceType.L, inside);
            trueRotationCenter.put(Piece.PieceType.S, inside);
            trueRotationCenter.put(Piece.PieceType.T, inside);
            trueRotationCenter.put(Piece.PieceType.Z, inside);

            list = Utility.parsePointsString(O_TRUE_ROTATION);
            inside = new HashMap<>();
            cnt = 0;
            for (SuperRotationState state : SuperRotationState.values()) {
                inside.put(state, list.get(cnt));
                cnt++;
            }
            trueRotationCenter.put(Piece.PieceType.O, inside);
        }

        return trueRotationCenter.get(type).get(src).diff(trueRotationCenter.get(type).get(dest));
    }

}
