import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class RookTest {

    @Test
    void testRook() {
        Board board = new Board();
        Rook rook = new Rook(true);

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        assertTrue(rook.validMove(4, 4, 4, 7, board));//valid moves

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        assertTrue(rook.validMove(4, 7, 4, 4, board));

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        assertTrue(rook.validMove(4, 0, 4, 4, board));

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        assertTrue(rook.validMove(4, 4, 4, 0, board));

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        board.insertPiece(4, 0, 'r');
        assertTrue(rook.validMove(4, 4, 4, 0, board));//take a piece

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        assertThrows(IllegalArgumentException.class,
                () -> rook.validMove(4, 7, 4, 7, board)); //invalid move

        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        board.insertPiece(7, 7, 'R');
        assertThrows(IllegalArgumentException.class,
                () -> rook.validMove(4, 7, 4, 7, board));//taking own piece






    }

}