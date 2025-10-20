public class Bishop extends Piece{
    public Bishop(boolean colour){
        //initialise pieces
        isWhite = colour;
        if(isWhite){
            symbol =  'B';
        } else{
            symbol = 'b';
        }
    }
//check valid move
    public boolean validMove(int r1, int r2, int c1, int c2, Board board) {
        int rlen = Math.abs(r1 - r2);
        int clen = Math.abs(c1 - c2);
        int rstep;
        int cstep;

//wrong move
        if(rlen != clen || rlen  == 0 || clen == 0){
            throw new IllegalArgumentException("Bishop must move diagonally");
        }
        if (r2>r1){
            rstep = 1;
        } else{
            rstep = -1;
        }

        if (c2>c1){
            cstep = 1;
        }else{
            cstep = -1;
        }
        //path blcoked
        int r = r1 + rstep;
        int c = c1 + cstep;
        while(r != r2 && c != c2){
            if(board.getPiece(r,c) != '.'){
                throw new IllegalArgumentException("Bishop's path is blocked");
            }
            r+=rstep;
            c+=cstep;
        }

        char dest = board.getPiece(r2,c2);
//own capture
        if(isWhite && Character.isUpperCase(dest)){
            throw new IllegalArgumentException("Cannot capture your own white piece");
        }

        if(!isWhite && Character.isLowerCase(dest)){
            throw new IllegalArgumentException("Cannot capture your own black piece");
        }
        return true;

    }
}
