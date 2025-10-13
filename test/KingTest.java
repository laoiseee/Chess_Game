import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KingTest {
    private Board board;
    private King king;

    @BeforeEach
    void setUp() {
        board = new Board();
        king = new King(true);
        board.clearBoard();
        board.insertPiece(4,4, 'K');
    }

    @Test
    void testD1() {

        assertTrue(king.validMove(4, 4, 4, 5, board)); //down

    }

    @Test
    void testD2() {
        assertTrue(king.validMove(4, 4, 4, 3, board));//up
    }
    @Test
    void testD3(){
        assertTrue(king.validMove(4, 5, 4, 4, board)); //L/R
    }

    @Test
    void testD4(){
        assertTrue(king.validMove(4, 3, 4, 5, board)); //L/R

    }
    @Test
    void diagonal1(){
        assertTrue(king.validMove(4, 5, 4, 5, board)); //diagonals
    }
    @Test
    void diagonal2(){
        assertTrue(king.validMove(4, 3, 4, 3, board));
    }

    @Test
    void diagonal3(){
        assertTrue(king.validMove(4, 5, 4, 3, board));

    }

    @Test
    void diagonal4(){
        assertTrue(king.validMove(4, 5, 4, 3, board));

    }
    @Test
    void invalidMove(){
        assertThrows(IllegalArgumentException.class,
                () -> king.validMove(4, 2, 4, 5, board));

    }

    @Test
    void takePiece(){
        board.insertPiece(5, 3, 'p');
        assertTrue(king.validMove(4, 3, 4, 5, board));

    }

    @Test
    void takeOwnPiece(){
        board.insertPiece(5, 3, 'P');
        assertThrows(IllegalArgumentException.class,
        ()-> king.validMove(4, 3, 5, 3, board));
    }
}