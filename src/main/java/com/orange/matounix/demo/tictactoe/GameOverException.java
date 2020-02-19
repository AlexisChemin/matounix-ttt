package com.orange.matounix.demo.tictactoe;

public class GameOverException extends Exception {

    private Player winner;

    public GameOverException() {
        winner = null;
    }

    public GameOverException(Player winner) {
        this.winner = winner;
    }

    public Player getWinner() {
        return winner;
    }

    @Override
    public String getMessage() {
        if (winner == null) {
            return "Board filled";
        }
        return winner.name() + " wins";
    }
}
