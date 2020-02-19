import com.orange.matounix.demo.tictactoe.GameOverException;
import com.orange.matounix.demo.tictactoe.Player;
import com.orange.matounix.demo.tictactoe.TicTacToe;
import com.orange.matounix.demo.tictactoe.legacy.LegacyTicTacToe;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TicTacToeTest {


    TicTacToe myTicTacToeUnderTest = new LegacyTicTacToe();

    @Test
    void player_X_should_win_with_3_horizontally_aligned_pieces() {

        // GIVEN
        myTicTacToeUnderTest.reset();

        // WHEN
        try {
            myTicTacToeUnderTest.play(1,1, Player.X );
            myTicTacToeUnderTest.play(2,1, Player.O );
            myTicTacToeUnderTest.play(1,2, Player.X );
            myTicTacToeUnderTest.play(3,1, Player.O );
            myTicTacToeUnderTest.play(1,3, Player.X );
            // Here :
            //    X | X | X
            //    O |   |
            //    O |   |
            fail("X should have won");
        } catch (GameOverException e) {

            // THEN
            assertThat(e.getWinner()).isEqualTo(Player.X);
        }
    }


    @Test
    void player_O_should_win_with_3_vertically_aligned_pieces() {

        // GIVEN
        myTicTacToeUnderTest.reset();

        // WHEN
        try {
            myTicTacToeUnderTest.play(1,1, Player.X );
            myTicTacToeUnderTest.play(1,2, Player.O );
            myTicTacToeUnderTest.play(3,3, Player.X );
            myTicTacToeUnderTest.play(2,2, Player.O );
            myTicTacToeUnderTest.play(1,3, Player.X );
            myTicTacToeUnderTest.play(3,2, Player.O );
            // Here :
            //    X | O | X
            //      | O |
            //      | O | X
            fail("O should have won");
        } catch (GameOverException e) {
            // THEN
            assertThat(e.getWinner()).isEqualTo(Player.O);
        }
    }


    @Test
    void should_game_over_when_board_is_filled() {

        // GIVEN
        myTicTacToeUnderTest.reset();

        // WHEN
        try {
            myTicTacToeUnderTest.play(1,1, Player.X );
            myTicTacToeUnderTest.play(1,2, Player.O );
            myTicTacToeUnderTest.play(1,3, Player.X );
            myTicTacToeUnderTest.play(2,1, Player.O );
            myTicTacToeUnderTest.play(2,2, Player.X );
            myTicTacToeUnderTest.play(2,3, Player.O );
            myTicTacToeUnderTest.play(3,1, Player.X );
            myTicTacToeUnderTest.play(3,2, Player.O );
            myTicTacToeUnderTest.play(3,3, Player.X );
            // Here :
            //    X | O | X
            //    O | X | O
            //    X | O | X
            fail("Cannot play anymore !");
        } catch (GameOverException e) {
            // THEN
            assertThat(e.getWinner()).isNull();
    }
    }


    @Test
    void player_X_should_win_with_3_diagonally_aligned_pieces() {

        // GIVEN
        myTicTacToeUnderTest.reset();

        // WHEN
        try {
            myTicTacToeUnderTest.play(1,1, Player.X );
            myTicTacToeUnderTest.play(2,1, Player.O );
            myTicTacToeUnderTest.play(3,3, Player.X );
            myTicTacToeUnderTest.play(3,2, Player.O );
            myTicTacToeUnderTest.play(2,2, Player.X );
            // Here :
            //    X |   |
            //    O | X |
            //      | O | X
            fail("X should have won");
        } catch (GameOverException e) {
            // THEN
            assertThat(e.getWinner()).isEqualTo(Player.X);
        }
    }

    @Test
    void should_not_play_out_of_bounds() {

        // GIVEN
        myTicTacToeUnderTest.reset();

        // WHEN
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {

            try {
                myTicTacToeUnderTest.play(4,1, Player.X );
                fail("Should not play out of board bounds");
            } catch (GameOverException e) {
                // THEN
                fail("Expected 'ArrayOutOfBoundException'");
            }

        });
    }


    @Test
    void should_not_play_twice_at_same_location() {

        // GIVEN
        myTicTacToeUnderTest.reset();

        // WHEN
        assertThrows(IllegalArgumentException.class, () -> {

            try {
                myTicTacToeUnderTest.play(3,1, Player.X );
                myTicTacToeUnderTest.play(3,1, Player.X );
                fail("Should not play twice at the same location");
            } catch (GameOverException e) {
                // THEN
                fail("Expected 'IllegalArgumentException'");
            }

        });
    }
}
