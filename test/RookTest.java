import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RookTest {
    private Board board;
    private Rook rook;

    @BeforeEach
    void setUp() {
        board = new Board();
        rook = new Rook(true);
        board.clearBoard();
        board.insertPiece(4, 4, 'R');
    }

    @Test
    void testd1() {
        assertTrue(rook.validMove(4, 4, 4, 7, board));//valid moves

    }

    @Test
    void testd2(){
        assertTrue(rook.validMove(4, 7, 4, 4, board));
    }


    @Test
    void testd3(){
        assertTrue(rook.validMove(4, 0, 4, 4, board));

    }

    @Test
    void testd4(){
        assertTrue(rook.validMove(4, 4, 4, 0, board));
    }

    @Test
    void takePiece(){
        board.insertPiece(4, 0, 'r');
        assertTrue(rook.validMove(4, 4, 4, 0, board));//take a piece
    }

    @Test
    void invalidMove(){
        assertThrows(IllegalArgumentException.class,
                () -> rook.validMove(4, 7, 4, 7, board)); //invalid move
    }

    @Test
    void takeOwnPiece(){
        board.insertPiece(7, 7, 'R');
        assertThrows(IllegalArgumentException.class,
                () -> rook.validMove(4, 7, 4, 7, board));//taking own piece
    }


}