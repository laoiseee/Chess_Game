public class Knight extends Piece {
    public Knight(boolean colour){
        //initialise
        isWhite = colour;
        if(isWhite){
            symbol = 'N';
        } else{
            symbol = 'n';
        }
    }

    //check move valid
    public boolean validMove(int r1, int r2, int c1, int c2, Board board){
        int rlen = Math.abs(r1 - r2);
        int clen = Math.abs(c1 - c2);

        //check shape
        if(!((rlen ==1 && clen ==2)||(rlen ==2 && clen ==1))){
            throw new IllegalArgumentException("Knight must move in an L shape.");

        }
        //check own capture
        char dest = board.getPiece(r2, c2);
        if(isWhite){
            if(Character.isUpperCase(dest)){
                throw new IllegalArgumentException("You cannot capture your own White piece.");
            }
        } else{
            if(Character.isLowerCase(dest)){
                throw new IllegalArgumentException("You cannot capture your own Black piece.");
            }
        }
        return true;
    }
}
