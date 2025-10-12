public class Queen extends Piece {
    public Queen(boolean colour){
        isWhite = colour;

        if(isWhite){
            symbol = 'Q';
        } else{
            symbol = 'q';
        }

    }

    public boolean validMove(int r1, int r2, int c1, int c2, Board board){
        Rook rook = new Rook(isWhite);
        Bishop bishop = new Bishop(isWhite);

        boolean valid = false;
        try{
            valid = rook.validMove(r1, r2, c1, c2, board);
        } catch(IllegalArgumentException e){

        }

        if(!valid){
            try{
                valid = bishop.validMove(r1, r2, c1, c2, board);
            } catch(IllegalArgumentException e){

            }
        }

        if(!valid){
            throw new IllegalArgumentException("Queen must move straight or diagonally.");
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
