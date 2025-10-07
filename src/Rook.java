public class Rook extends Piece{
    public Rook (boolean colour){
        isWhite = colour;

        if(isWhite){
            symbol =  'R';
        } else{
            symbol = 'r';
        }

    }

    public boolean validMove(int r1, int r2, int c1, int c2, Board board){
        int step;
        if(c2 != c1 && r2 != r1){
            throw new IllegalArgumentException("Rook must move in straight horizontal or vertical lines");
        } else if(c1 == c2 && r1!= r2) {
            if(r1> r2){
                step = -1;
            } else{
                step = 1;
            }
            for(int r = r1 + step; r != r2; r = r + step ){
                if(board.getPiece(r, c1) != '.'){
                    throw new IllegalArgumentException("Rook's path is blocked");

                }
            }

        } else if(r1 == r2 && c1 != c2){
            if(c1 > c2){
                step = -1;
            } else{
                step = 1;
            }
            for(int c = c1 + step; c != c2; c = c + step ){
                if(board.getPiece(r1, c) != '.'){
                    throw new IllegalArgumentException("Rook's path is blocked");
                }
            }
        }
        char dest = board.getPiece(r2, c2);
        if(isWhite && Character.isUpperCase(dest)){
            throw new IllegalArgumentException("Cannot capture your own white piece");

        }

        if(!isWhite && Character.isLowerCase(dest)){
            throw new IllegalArgumentException("Cannot capture your own black piece");
        }

        return true;
    }
}
