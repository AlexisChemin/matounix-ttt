package com.orange.matounix.demo.tictactoe;

public interface TicTacToe {


    /**
     * Starts/Continues a TicTacToeGame
     * @param row Indicates the row of the square where the 'player' plays, between 1 and 3
     * @param column Indicates the column of the square where the 'player', between 1 and 3
     * @param player the current player, X or O
     * @throws GameOverException when the game is over, i.e when the board is filled or on of the player has aligned
     * 3 pieces
     */
    void play(int row, int column, Player player) throws GameOverException;

    /**
     * Reset the game
     */
    void reset();



}
