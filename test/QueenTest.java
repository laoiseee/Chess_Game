import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class QueenTest {
    @Test
    void testQueenDiagonal() {
        Queen queen = new Queen(true);
        Board board = new Board();
        board.clearBoard();
        board.insertPiece(4, 4, 'Q');

        assertTrue(queen.validMove(4, 6, 4, 6, board));
        board.clearBoard();
        board.insertPiece(4, 4, 'Q');
        assertThrows(IllegalArgumentException.class,
                () -> queen.validMove(4, 6, 4, 5, board));



    }
    @Test
    void testQueenVH(){
        Queen queen = new Queen(true);
        Board board = new Board();
        board.clearBoard();
        board.insertPiece(4, 4, 'Q');
        assertTrue(queen.validMove(4, 4, 4, 6, board));

        board.clearBoard();
        board.insertPiece(4, 4, 'Q');
        assertTrue(queen.validMove(4, 6, 4, 4, board));

        board.clearBoard();
        board.insertPiece(4, 4, 'Q');
        assertThrows(IllegalArgumentException.class,
                () -> queen.validMove(4, 6, 4, 5, board));
    }


}