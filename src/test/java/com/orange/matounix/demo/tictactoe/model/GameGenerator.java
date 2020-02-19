package com.orange.matounix.demo.tictactoe.model;

import com.orange.matounix.demo.tictactoe.Player;
import com.orange.matounix.demo.tictactoe.model.GameModel.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.jqwik.api.Arbitraries;

import java.util.*;
import java.util.stream.Stream;

import static com.orange.matounix.demo.tictactoe.model.GameGenerator.BoardIndex.*;
import static java.util.Collections.shuffle;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;

public class GameGenerator {


    public List<GameModel.ActionAndResult> generateActionsAndResultSequence() {
        State initialState = Arbitraries.of(State.X_TURNS , State.O_TURNS).sample();
        return streamOfActionAndResult(initialState, new Board(), createShuffledStackOfFreePositions() ).collect(toList());
    }

    /**
     * Recursive method that build a sequence of ActionAndResult
     * @param previousState
     * @param board
     * @param freePositions
     * @return
     */
    Stream<GameModel.ActionAndResult> streamOfActionAndResult(State previousState, Board board, Deque<BoardCoord> freePositions) {
        GameModel.ActionAndResult actionAndResult = new GameModel.ActionAndResult();

        if (freePositions.isEmpty()) {
            actionAndResult.state = State.DRAW_GAME;
            return of(actionAndResult);
        }
        // next nextPlayer plays...
        actionAndResult.player = previousState.nextPlayer;

        // ... at next free position ...
        BoardCoord boardCoord = freePositions.pop();
        actionAndResult.position = toPosition(boardCoord);
        board.put(boardCoord, actionAndResult.player);

        // ... and might win
        if (isWinnig(board)) {
            actionAndResult.state = State.WINNING_GAME;
            return of(actionAndResult);
        }

        // switch players
        actionAndResult.state = switchPlayers(previousState);

        return concat(of(actionAndResult), streamOfActionAndResult(actionAndResult.state, board, freePositions));

    }


    /**
     * Return true if the board 
     * @param board
     * @return
     */
    private boolean isWinnig(Board board) {
        return      isAligned( board,    coord(A, A), coord(A, B), coord(A, C)) // row 1
                ||  isAligned( board,    coord(B, A), coord(B, B), coord(B, C)) // row 2
                ||  isAligned( board,    coord(C, A), coord(C, B), coord(C, C)) // row 3
                ||  isAligned( board,    coord(A, A), coord(B, A), coord(C, A)) // col 1
                ||  isAligned( board,    coord(A, B), coord(B, B), coord(C, B)) // col 2
                ||  isAligned( board,    coord(A, C), coord(B, C), coord(C, C)) // col 3
                ||  isAligned( board,    coord(A, A), coord(B, B), coord(C, B)) // diag 1
                ||  isAligned( board,    coord(A, C), coord(B, B), coord(A, C)) ;  // diag 2
    }



    private boolean isAligned(Board board, BoardCoord c1, BoardCoord c2, BoardCoord c3) {
        return (board.get(c1) != null) && (board.get(c1) == board.get(c2)) && (board.get(c2) == board.get(c3));
    }


    private Deque<BoardCoord> createShuffledStackOfFreePositions() {
        LinkedList<BoardCoord> freePositions = new LinkedList();
        freePositions.addAll(ALL_CELL_COORDS);
        Collections.shuffle(freePositions);
        return freePositions;
    }


    private GameModel.Position toPosition(BoardCoord boardCoord) {
        GameModel.Position position = new GameModel.Position();
        position.row = boardCoord.row.ordinal() + 1;
        position.column = boardCoord.column.ordinal() + 1;
        return position;
    }


    private State switchPlayers(State previousState) {
        return previousState == State.X_TURNS ? State.O_TURNS : State.X_TURNS;
    }

    private LinkedList<GameModel.ActionAndResult> createList(GameModel.ActionAndResult actionAndResult) {
        return new LinkedList<>(Collections.singletonList(actionAndResult));
    }

    public static class Board extends HashMap<BoardCoord, Player> {
    }

    enum BoardIndex {
        A,
        B,
        C
    }

    @Data
    @AllArgsConstructor
    static class BoardCoord {
        private BoardIndex row;
        private BoardIndex column;
    }




    public static BoardCoord coord(BoardIndex row, BoardIndex column) {
        return new BoardCoord(row, column);
    }


    static List<BoardCoord> ALL_CELL_COORDS = Arrays.asList(
            coord(A, A), coord(A, B), coord(A, C),
            coord(B, A), coord(B, B), coord(B, C),
            coord(C, A), coord(C, B), coord(C, C)
    );
}
