import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class KingTest {

    @Test
    void testKing() {
        Board board = new Board();
        King king = new King(true);
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 4, 4, 5, board)); //up/down
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 4, 4, 3, board));//up/down
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 5, 4, 4, board)); //L/R
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 3, 4, 5, board)); //L/R
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 5, 4, 5, board)); //diagonals
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 3, 4, 3, board));
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 5, 4, 3, board));
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertTrue(king.validMove(4, 5, 4, 3, board));
        board.clearBoard();
        board.insertPiece(4,4, 'K');

        assertThrows(IllegalArgumentException.class,
                () -> king.validMove(4, 2, 4, 5, board));
    }

}