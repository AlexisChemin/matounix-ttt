package com.orange.matounix.demo.tictactoe.legacy;

import com.orange.matounix.demo.tictactoe.GameOverException;
import com.orange.matounix.demo.tictactoe.Player;
import com.orange.matounix.demo.tictactoe.TicTacToe;

public class LegacyTicTacToe implements TicTacToe {

    Player[][] board;

    public LegacyTicTacToe() {
        reset();
    }

    public void reset() {
        board = new Player[4][4];
    }

    public void play(int row, int column, Player player) throws GameOverException {

        if (row > 3) {
            throw new ArrayIndexOutOfBoundsException("invalid row value : " + row);
        }
        if (column > 3) {
            throw new ArrayIndexOutOfBoundsException("invalid column value : " + column);
        }

        if (board[row][column] != null) {
            throw new IllegalArgumentException();
        }

        board[row][column] = player;
        checkEndGame();
    }

    private void checkEndGame() throws GameOverException {
        // check if board is already filled
        if (isFilled()) {
            throw new GameOverException();
        }

        // check if a player aligned 3 pieces
        checkHorizontalAlignments();
        checkVerticalAlignments();
        checkDiagonalAlignments();
    }


    private boolean isFilled() {
        for(int i = 1; i <=3; i++) {
            for(int j = 1; j <=3; j++) {
                if (board[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkHorizontalAlignments() throws GameOverException {
        // for every row
        for(int r = 1; r <=3; r++) {
            int alignedPieceCounter = 1;
            for(int c = 1; c <=3; c++) {
                if (board[r][c] == null) {
                    break;
                }
                if (board[r][c] == board[r][c - 1]) {
                    alignedPieceCounter++;
                }
            }
            if (alignedPieceCounter==3) {
                throw new GameOverException(board[r][1]);
            }
        }
    }


    private void checkVerticalAlignments() throws GameOverException {
        // for every column
        for(int c = 1; c <=3; c++) {
            int alignedPieceCounter = 1;
            for(int r = 1; r <=3; r++) {
                if (board[r][c] == null) {
                    break;
                }
                if (board[r][c] == board[r - 1][c]) {
                    alignedPieceCounter++;
                }
            }
            if (alignedPieceCounter==3) {
                throw new GameOverException(board[1][c]);
            }
        }
    }


    private void checkDiagonalAlignments() throws GameOverException {
        // for every column
        int alignedPieceCounter = 1;
        for(int i = 1; i <=3; i++) {
            if (board[i][i] == null) {
                break;
            }
            if (board[i][i] == board[i - 1][i - 1]) {
                alignedPieceCounter++;
            }
        }
        if (alignedPieceCounter==3) {
            throw new GameOverException(board[1][1]);
        }
    }
}
