import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BishopTest {
    private Bishop bishop;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        bishop = new Bishop(true);
        board.clearBoard();
        board.insertPiece(4,4, 'B');
    }
    @Test
    void validD1() {
        assertTrue(bishop.validMove(4, 7, 4, 1, board)); //diagonal


    }

    @Test
    void validD2() {
        assertTrue(bishop.validMove(4, 1, 4, 1, board)); //diagonal other way
    }

    @Test
    void validD3(){
        assertTrue(bishop.validMove(4, 1, 4, 7, board));//diagonal other way

    }

    @Test
    void validD4(){
        assertTrue(bishop.validMove(4, 7, 4, 7, board)); //diagonal other wat
    }

    @Test
    void invalid(){

        assertThrows(IllegalArgumentException.class,
                () -> bishop.validMove(4, 4, 4, 8, board));//forward
    }

    @Test
    void takeOwnPiece(){
        board.insertPiece(7,7, 'P');
        assertThrows(IllegalArgumentException.class,
                () -> bishop.validMove(4, 7, 4, 7, board));//taking own piece

    }

    @Test
    void capturePiece() {
        board.insertPiece(7, 7, 'p');
        assertTrue(bishop.validMove(4, 7, 4, 7, board));

    }
    


}