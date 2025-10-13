import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class gameStartTest {

    @Test
    void newNames(){
        Scanner input = new Scanner("Laoise\nTara\n");
        gameStart game = new gameStart(input);
        assertEquals("Laoise", game.name1);
        assertEquals("Tara", game.name2);
        assertFalse(game.gameEnd);

    }

    @Test
    void whiteQuit(){
        Scanner input = new Scanner("Laoise\nTara\nquit");
        gameStart game = new gameStart(input);
        Board board = new Board();
        game.turn(board);
        assertTrue(game.gameEnd);

    }

    @Test
    void blackQuit(){
        Scanner input = new Scanner("Laoise\nTara\nquit");
        gameStart game = new gameStart(input);
        game.whiteTurn = false;
        Board board = new Board();
        game.turn(board);
        assertTrue(game.gameEnd);

    }

    @Test
    void whiteQuitUpperCase(){
        Scanner input = new Scanner("Laoise\nTara\nQUIT");
        gameStart game = new gameStart(input);
        Board board = new Board();
        game.turn(board);
        assertTrue(game.gameEnd);

    }

    @Test
    void quitMidTurn(){
        Scanner input = new Scanner("Laoise\nTara\ne2 quit");
        gameStart game = new gameStart(input);
        Board board = new Board();
        game.turn(board);
        assertTrue(game.gameEnd);


    }

}