public class King extends Piece{
    public King(boolean colour){
        isWhite = colour;
        if(isWhite){
            symbol = 'K';
        } else{
            symbol = 'k';
        }
    }

    public boolean validMove(int r1, int r2, int c1, int c2, Board board){
        int rlen = Math.abs(r1-r2);
        int clen = Math.abs(c1-c2);

        if(rlen == 0 && clen == 0){
            throw new IllegalArgumentException("King must move at least one square");
        }
        if(rlen > 1 || clen > 1){
            throw new IllegalArgumentException("King may only move one square");
        }
        char dest = board.getPiece(r2, c2);

        if(isWhite && Character.isUpperCase(dest)){
            throw new IllegalArgumentException("You cannot capture your own White piece.");
        }
        if(!isWhite && Character.isLowerCase(dest)){
            throw new IllegalArgumentException("You cannot capture your own Black piece.");
        }
        return true;
    }
}
