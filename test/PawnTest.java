import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PawnTest {
    @Test
    void PawnTest(){
        Board board = new Board();
        Pawn pawn = new Pawn(true);

        board.clearBoard();
        board.insertPiece(4, 4, 'P');
        assertTrue(pawn.validMove(4, 3, 4, 4, board));//valid move

        board.clearBoard();
        board.insertPiece(4, 4, 'P');
        board.insertPiece(3, 3, 'p');
        assertTrue(pawn.validMove(4, 3, 4, 3, board));//taking piece

        board.clearBoard();
        board.insertPiece(4, 4, 'P');
        board.insertPiece(3, 4, 'p');
        assertThrows(IllegalArgumentException.class,
                () -> pawn.validMove(4, 3, 4, 4, board));//attempting to take a piece straight ahead

        board.clearBoard();
        board.insertPiece(4, 4, 'P');
        board.insertPiece(3, 3, 'P');
        assertThrows(IllegalArgumentException.class,
                () -> pawn.validMove(4, 3, 4, 3, board));//cannot take own piece

        board.clearBoard();
        board.insertPiece(4, 4, 'P');
        assertThrows(IllegalArgumentException.class,
                ()-> pawn.validMove(4, 5, 4, 4, board));//invalid move

    }

}