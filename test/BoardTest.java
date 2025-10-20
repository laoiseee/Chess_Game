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

    @Test
    void legalMovesAtStart(){
        assertFalse(board.getLegalMoves(true).isEmpty());
        assertFalse(board.getLegalMoves(false).isEmpty());

    }

    @Test
    void recordMove(){
        board.clearBoard();
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 4, 'K');
        board.insertPiece(4, 4, 'q');

        assertTrue(board.move(4, 4, 4, 7));
        assertEquals(board.getHistory().size(), 1);
        assertEquals(board.getLastWhiteMove(), null);
        assertFalse(board.getLastBlackMove() == null);

    }

    @Test
    void noOwnMove(){
        board.clearBoard();
        board.insertPiece(0, 0, 'K');
        board.insertPiece(4, 4, 'Q');
        assertFalse(board.move(4, 4, 4, 4));
    }

    @Test
    void blackInCheckMate(){
        board.clearBoard();
        board.insertPiece(7, 0, 'k');
        board.insertPiece(6, 1, 'Q');
        board.insertPiece(5, 2, 'K');
        assertTrue(board.inCheck(false));
        assertFalse(board.hasLegalMoves(false));
    }

    @Test
    void stalemate(){
        board.clearBoard();
        board.insertPiece(7, 0, 'k');
        board.insertPiece(6, 2, 'Q');
        board.insertPiece(5, 1, 'K');
        board.print();
        assertFalse(board.inCheck(false));
        assertFalse(board.hasLegalMoves(false));

    }

    @Test
    void castleWhiteKingSideMovesKingandRook(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 7, 'R');

        assertTrue(board.move(7,7,4, 6));

        assertEquals(board.getPiece(7, 6),'K' );
        assertEquals(board.getPiece(7, 5),'R' );
        assertEquals(board.getPiece(7, 7),'.' );

    }

    @Test
    void castleWhiteQueenSideMovesKingandRook(){
        board.clearBoard();
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 0, 'R');

        assertTrue(board.move(7,7,4, 2));

        assertEquals(board.getPiece(7, 2),'K' );
        assertEquals(board.getPiece(7, 3),'R' );
        assertEquals(board.getPiece(7, 0),'.' );
    }

    @Test
    void castleFailsPathBlocked(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 7, 'R');
        board.insertPiece(7, 5, 'N');

        assertFalse(board.move(7,7,4, 6));

    }

    @Test
    void castleFailsThroughSquareUnderAttack(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 7, 'R');
        board.insertPiece(0, 5, 'r');

        assertFalse(board.move(7,7,4, 6));

    }

    @Test
    void failsDestinationSquareUnderAttack(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 7, 'R');
        board.insertPiece(5, 4, 'b');

        assertFalse(board.move(7,7,4, 6));


    }

    @Test
    void castleFailsRookMovedEarlier(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 7, 'R');

        assertTrue(board.move(7,7, 7, 6));
        assertTrue(board.move(7,7, 6, 7));
        assertFalse(board.move(7,7,4, 6));

    }

    @Test
    void castleFailedKingMovedEarlier(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');
        board.insertPiece(7, 7, 'R');

        assertTrue(board.move(7,7, 4, 5));
        assertTrue(board.move(7,7, 5, 4));
        assertFalse(board.move(7,7,4, 6));

    }

    @Test
    void enPassantRemovesCorrectPawn(){
        board.clearBoard();
        board.insertPiece(7, 4, 'K');
        board.insertPiece(0, 4, 'k');

        board.insertPiece(3, 4, 'P');
        board.insertPiece(1, 5, 'p');

        board.move(1, 3, 5, 5);
        assertEquals(2, board.getEnPassantTargetRow());
        assertEquals(5, board.getEnPassantTargetCol());

        assertTrue(board.move(3, 2, 4, 5));

        assertEquals(board.getPiece(3, 5), '.');
        assertEquals(board.getPiece(2, 5), 'P');
    }

    @Test
    void pawnPromotesToQueen(){
        board.clearBoard();
        board.insertPiece(1, 0, 'P');
        board.insertPiece(0, 4, 'K');
        board.print();

        board.setPromotionChoice('Q');
        assertTrue(board.move(1,0,0,0));
        assertEquals(board.getPiece(0,0), 'Q');
    }

    @Test
    void pawnPromotesToKnight(){
        board.clearBoard();
        board.insertPiece(1, 0, 'P');
        board.insertPiece(0, 4, 'K');

        board.setPromotionChoice('N');
        assertTrue(board.move(1,0,0,0));
        assertEquals(board.getPiece(0,0), 'N');
    }

    @Test
    void rejectsInvalidPromotionLetter(){
        board.clearBoard();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
        ()->board.setPromotionChoice('X')
        );
        assertEquals("Invalid choice: Promotion must be 'Q', 'R', 'B' or 'N'", ex.getMessage() );
    }
}