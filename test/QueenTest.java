import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueenTest {
    private Queen queen;
    private Board board;
    @BeforeEach
    void setUp() {
        queen = new Queen(true);
        board = new Board();
        board.clearBoard();
        board.insertPiece(4, 4, 'Q');

    }

    /*Diagonal Tests*/
    @Test
    void testD1() {
        assertTrue(queen.validMove(4, 6, 4, 6, board));
    }

    @Test
    void testD2() {
        assertTrue(queen.validMove(4, 3, 4, 3, board));
    }

    @Test
    void testD3() {
        assertTrue(queen.validMove(4, 5, 4, 3, board));
    }

    @Test
    void testD4() {
        assertTrue(queen.validMove(4, 3, 4, 5, board));
    }

    /*Vertical/Horizontal Tests*/
    @Test
    void testH1(){
        assertTrue(queen.validMove(4, 4, 4, 6, board));//valid
    }

    @Test
    void testH2(){
        assertTrue(queen.validMove(4, 4, 4, 2, board));//valid
    }

    @Test
    void testH3(){
        assertTrue(queen.validMove(4, 6, 4, 4, board));//valid
    }

    @Test
    void testH4(){
        assertTrue(queen.validMove(4, 3, 4, 4, board));//valid
    }

    /*Invalid move checks*/
    @Test
    void invalidMoveDiag(){
        assertThrows(IllegalArgumentException.class,
                () -> queen.validMove(4, 6, 4, 5, board));//invalid
    }

    @Test
    void invalidMoveVH(){
        assertThrows(IllegalArgumentException.class,
                () -> queen.validMove(4, 6, 4, 5, board));//invalid
    }

    /*Capturing Pieces tests*/

    @Test
    void captureOwnPiece(){
        board.insertPiece(6, 6, 'P');
        assertThrows(IllegalArgumentException.class,
                () -> queen.validMove(4, 6, 4, 6, board));


    }

    @Test
    void capturePiece(){
        board.insertPiece(6, 6, 'p');
        assertTrue(queen.validMove(4, 6, 4, 6, board));
    }


}