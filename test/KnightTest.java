import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnightTest {
    private Knight knight;
    private Board board;

    @BeforeEach
    void setUp() {
        knight = new Knight(true);
        board = new Board();
        board.clearBoard();
        board.insertPiece(4,4, 'N');
    }
    @Test
    void validD1() {
        assertTrue(knight.validMove(4, 6, 4, 5, board));//valid moves

    }

    @Test
    void validD2() {
        assertTrue(knight.validMove(4, 2, 4, 5, board));
    }
    @Test
    void invalidMove(){

        assertThrows(IllegalArgumentException.class,
                ()-> knight.validMove(4, 6, 4, 4, board));

    }

    @Test
    void takeOwnPiece(){
        board.insertPiece(2,5, 'P');
        assertThrows(IllegalArgumentException.class,
                ()-> knight.validMove(4, 2, 4, 5, board));
    }

    @Test
    void capturePiece(){
        board.insertPiece(2,5, 'p');
        assertTrue(knight.validMove(4, 2, 4, 5, board));
    }

}