public class Pawn extends Piece {

    public Pawn(boolean colour) {
        isWhite = colour;

        if (isWhite) {
            symbol = 'P';
        } else {
            symbol = 'p';
        }
    }

    public boolean validMove(int r1, int r2, int c1, int c2, Board board) {
        int move = r2 - r1;
        if (c1 == c2) {
            if (isWhite) {
                if (move == -1) {
                    if(board.getPiece(r2, c2)!='.'){
                        throw new IllegalArgumentException("White pawn move blocked");
                    }
                    return true;
                }
                if (move == -2) {
                    if (r1 == 6) {
                        if(board.getPiece(r1-1, c1) != '.'||board.getPiece(r2, c2) != '.'){
                            throw new IllegalArgumentException("White pawn move blocked");
                        }
                        return true;
                    }
                }
            } else {
                if (move == 1) {
                    if(board.getPiece(r2, c2) != '.'){
                        throw new IllegalArgumentException("Black pawn move blocked");
                    }
                    return true;
                }
                if(move == 2){
                    if (r1 == 1){
                        if(board.getPiece(r1+1, c1) != '.'||board.getPiece(r2, c2)!='.'){
                            throw new IllegalArgumentException("Black pawn move blocked");
                        }
                        return true;
                    }
                }
            }

        } else{
            if(Math.abs(c1-c2)==1 && ((isWhite&&move == -1) ||(!isWhite&&move == 1))){
                char dest = board.getPiece(r2, c2);
                if(dest == '.'){
                    throw new IllegalArgumentException("No piece to capture");
                }
                if(isWhite && Character.isUpperCase(dest)){
                    throw new IllegalArgumentException("Cannot capture your own white piece");
                }

                if(!isWhite && Character.isLowerCase(dest)){
                    throw new IllegalArgumentException("Cannot capture your own black piece");

                }
                return true;
            }
        }
        throw new IllegalArgumentException("Invalid pawn move");

    }


    }


