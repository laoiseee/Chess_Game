import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class KnightTest {
    @Test
    void validMove() {
        Knight knight = new Knight(true);
        Board board = new Board();
        board.clearBoard();
        board.insertPiece(4,4, 'N');

        assertTrue(knight.validMove(4, 6, 4, 5, board));

        board.clearBoard();
        board.insertPiece(4,4, 'N');
        assertTrue(knight.validMove(4, 2, 4, 5, board));

        board.clearBoard();
        board.insertPiece(4,4, 'N');
        assertThrows(IllegalArgumentException.class,
                ()-> knight.validMove(4, 6, 4, 4, board));





    }

}