public class King extends Piece{
    public King(boolean colour){
        //initialise pieces
        isWhite = colour;
        if(isWhite){
            symbol = 'K';
        } else{
            symbol = 'k';
        }
    }

    public boolean validMove(int r1, int r2, int c1, int c2, Board board){
        //castling
        if(r1 == r2 && Math.abs(c1-c2)==2){
            boolean isKingSide;
            if(c2>c1){
                isKingSide = true;
            }else{
                isKingSide = false;
            }

            //check start square
            boolean onStartSquare;
            if(isWhite){
                onStartSquare = (r1 == 7 && c1 ==4);
            } else{
                onStartSquare = (r1 == 0 && c1 ==4);
            }
            if(!onStartSquare){
                throw new IllegalArgumentException("Castling not possible: King not on starting square");
            }

            //check legality
            boolean castlePossible = board.canCastle(isWhite, isKingSide);
            if(!castlePossible){
                throw new IllegalArgumentException("Castling not possible: Conditions not met.");
            }
            return true;

        }
        //regular king movements
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
