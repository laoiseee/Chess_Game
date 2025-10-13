import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckDetectTest {
    private Board board;
    private CheckDetect check;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.clearBoard();
        check = new CheckDetect(board);
    }

    @Test
    void whiteInCheckRook(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(0, 4, 'r');
        assertTrue(check.detect(true));
    }

    @Test
    void whiteInCheckBishop(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(1, 1, 'b');
        assertTrue(check.detect(true));
    }

    @Test
    void whiteInCheckKnight(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(2, 3, 'n');
        assertTrue(check.detect(true));
    }

    @Test
    void whiteInCheckQueen(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(2, 4, 'q');
        assertTrue(check.detect(true));
    }

    @Test
    void whiteInCheckKing(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(3, 3, 'k');
        assertTrue(check.detect(true));
    }

    @Test
    void whiteInCheckPawn(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(3, 3, 'p');
        assertTrue(check.detect(true));
    }

    @Test
    void whiteKingBlocked(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(0, 4, 'r');
        board.insertPiece(3, 4, 'R');
        assertFalse(check.detect(true));

    }

    @Test
    void whiteKingNotInCheck(){
        board.insertPiece(4, 4, 'K');
        board.insertPiece(0, 2, 'r');
        board.insertPiece(3, 3, 'P');
        assertFalse(check.detect(true));
    }

    @Test
    void blackInCheckRook(){
        board.insertPiece(4, 4, 'k');
        board.insertPiece(0, 4, 'R');
        assertTrue(check.detect(false));
    }
    /*Errors*/

    @Test
    void noKingFound(){
        board.insertPiece(0, 4, 'k');
        assertThrows(IllegalArgumentException.class,
                ()->check.detect(true));
    }




}