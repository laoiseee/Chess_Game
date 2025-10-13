import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PawnTest {
    private Pawn pawn;
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        pawn = new Pawn(true);
        board.clearBoard();
        board.insertPiece(4, 4, 'P');
    }
    @Test
    void PawnValid(){
        assertTrue(pawn.validMove(4, 3, 4, 4, board));//valid move
    }

    @Test
    void capturePiece(){
        board.insertPiece(3, 3, 'p');
        assertTrue(pawn.validMove(4, 3, 4, 3, board));//taking piece

    }

    @Test
    void invalidCapture(){
        board.insertPiece(3, 4, 'p');
        assertThrows(IllegalArgumentException.class,
                () -> pawn.validMove(4, 3, 4, 4, board));//attempting to take a piece straight ahead
    }

    @Test
    void takeOwnPiece(){
        board.insertPiece(3, 3, 'P');
        assertThrows(IllegalArgumentException.class,
                () -> pawn.validMove(4, 3, 4, 3, board));//cannot take own piec
    }

    @Test
    void PawnInvalid(){
        assertThrows(IllegalArgumentException.class,
                ()-> pawn.validMove(4, 5, 4, 4, board));//invalid move
    }

}