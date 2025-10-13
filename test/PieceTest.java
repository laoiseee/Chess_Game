import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceTest {
    @Test
    void isWhite(){
        Piece p = new Piece(true, 'P');
        assertTrue(p.isWhite());
    }

    @Test
    void isBlack(){
        Piece p = new Piece(false, 'B');
        assertFalse(p.isWhite());
    }

    @Test
    void getSymbol(){
        Piece p = new Piece(true, 'P');
        assertEquals('P', p.getSymbol());
    }

}