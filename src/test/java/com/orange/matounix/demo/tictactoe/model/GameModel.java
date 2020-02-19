package com.orange.matounix.demo.tictactoe.model;

import com.orange.matounix.demo.tictactoe.Player;

public class GameModel {


    public static class ActionAndResult {
        public Position position;
        public Player player;
        public State state;
    }

    public enum State {
        X_TURNS(Player.X),
        O_TURNS(Player.O),
        WINNING_GAME(null),
        DRAW_GAME(null);

        public final Player nextPlayer;

        State(Player nextPlayer) {
            this.nextPlayer = nextPlayer;
        }
    }


    public static class Position {
        public int row;
        public int column;
    }



}
