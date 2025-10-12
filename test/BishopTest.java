import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BishopTest {
    @Test
    void BishopTest() {
        Board board = new Board();
        Bishop bishop = new Bishop(true);

        board.clearBoard();
        board.insertPiece(4,4, 'B');
        assertTrue(bishop.validMove(4, 7, 4, 7, board)); //diagonal

        board.clearBoard();
        board.insertPiece(4,4, 'B');
        assertTrue(bishop.validMove(4, 1, 4, 1, board));

        board.clearBoard();
        board.insertPiece(4,4, 'B');
        assertTrue(bishop.validMove(4, 1, 4, 7, board));

        board.clearBoard();
        board.insertPiece(4,4, 'B');
        assertTrue(bishop.validMove(4, 7, 4, 1, board));

        board.clearBoard();
        board.insertPiece(4,4, 'B');
        assertThrows(IllegalArgumentException.class,
                () -> bishop.validMove(4, 4, 4, 8, board));//forward

        board.clearBoard();
        board.insertPiece(4,4, 'B');
        board.insertPiece(7,7, 'P');
        assertThrows(IllegalArgumentException.class,
                () -> bishop.validMove(4, 7, 4, 7, board));//taking own piece

    }

}