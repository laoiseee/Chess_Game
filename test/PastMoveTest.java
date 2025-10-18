import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PastMoveTest {
    @Test
    void constructor(){
        PastMove move = new PastMove(1, 6, 4, 4, 'R', '.' );
        assertEquals(1, move.r1);
        assertEquals(6, move.r2);
        assertEquals(4, move.c1);
        assertEquals(4, move.c2);
        assertEquals('R', move.piece);
        assertEquals('.', move.dest);
    }

    @Test
    void squareConversion(){
        assertEquals("a1", PastMove.square(0, 0));
        assertEquals("h8", PastMove.square(7, 7));
        assertEquals("c3", PastMove.square(2, 2));
    }

    @Test
    void toStringCapture(){
        PastMove move = new PastMove(1, 6, 4, 4, 'R', 'p' );
        assertEquals("R(e2,e7)XP", move.toString());
    }

    @Test
    void toStringNoCapture(){
        PastMove move = new PastMove(1, 6, 4, 4, 'R', '.' );
        assertEquals("R(e2,e7)", move.toString());
    }



}