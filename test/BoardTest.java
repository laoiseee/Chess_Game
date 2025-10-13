import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {
    private Board board;
    @BeforeEach
    void setup(){
        board = new Board();
    }

    @Test
    void startingPlaces(){
        assertEquals('r', board.getPiece(0,0));
        assertEquals('R', board.getPiece(7,0));
        assertEquals('p', board.getPiece(1,3));
        assertEquals('P', board.getPiece(6,4));
        assertEquals('.', board.getPiece(5,5));
    }

    @Test
    void testClearBoard(){
        board.clearBoard();
        for (int r = 0; r <8; r++){
            for (int c = 0; c <8; c++){
                assertEquals('.', board.getPiece(r,c));
            }
        }
    }

    @Test
    void insertGet(){
        board.clearBoard();
        board.insertPiece(4, 4, 'K');
        assertEquals('K', board.getPiece(4,4));
    }

    @Test
    void outOfBounds(){
        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        assertFalse(board.move(4, 8, 4, 4));
        assertFalse(board.move(4, -1, 4, 4));

    }

    @Test
    void noPiece(){
        board.clearBoard();
        assertFalse(board.move(4, 8, 4, 4));
    }

    @Test
    void validMove(){
        board.clearBoard();
        board.insertPiece(4, 4, 'R');
        board.insertPiece(0, 0, 'K'); //so king checker wont throw
        assertTrue(board.move(4, 7, 4, 4));
        assertEquals('R', board.getPiece(7,4));
    }

    @Test
    void capture(){
        board.clearBoard();
        board.insertPiece(0, 0, 'K');
        board.insertPiece(4, 4, 'Q');
        board.insertPiece(4, 6, 'q');
        assertTrue(board.move(4, 4, 4, 6));
        assertEquals('Q', board.getPiece(4,6));
        assertEquals('.', board.getPiece(4,4));
    }

    @Test
    void ownCapture(){
        board.clearBoard();
        board.insertPiece(0, 0, 'K');
        board.insertPiece(4, 4, 'Q');
        board.insertPiece(4, 6, 'R');
        assertFalse(board.move(4, 4, 4, 6));
    }

    @Test
    void cannotEnterSelfCheck(){
        board.clearBoard();
        board.insertPiece(0, 0, 'K');
        board.insertPiece(4, 4, 'q');
        board.insertPiece(3, 3, 'R');
        assertFalse(board.move(3, 3, 3, 4));
    }

}