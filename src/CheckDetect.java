public class CheckDetect {
    private Board board;
    public CheckDetect(Board board) {
        this.board = board;
    }

    public boolean detect(boolean isWhite){
        char king;
        if(isWhite){
            king = 'K';
        } else{
            king = 'k';
        }

        int kingr = -1;
        int kingc = -1;

        for(int r = 0; r< 8;r++) {
            for (int c = 0; c < 8; c++) {
                if (board.getPiece(r, c) == king) {
                    kingr = r;
                    kingc = c;
                }
            }
            if (kingr != -1 && kingc != -1) {
                break;
            }
        }
            if(kingr == -1 || kingc == -1){
                throw new IllegalArgumentException("King not found");
            }

            for(int r = 0; r< 8; r++) {
                for(int c = 0; c < 8; c++) {
                    char piece = board.getPiece(r, c);

                    if(piece == '.'){
                        continue;
                    }

                    boolean pieceWhite =  Character.isUpperCase(piece); //checks if the piece r, c is the enemy

                    if(pieceWhite == isWhite){
                        continue;
                    }

                    try{
                        char lowerCase = Character.toLowerCase(piece);
                        if(lowerCase == 'p'){
                            if(new Pawn(pieceWhite).validMove(r, kingr, c, kingc, board)){
                                return true;
                            }
                        }
                        if(lowerCase == 'r'){
                            if(new Rook(pieceWhite).validMove(r, kingr, c, kingc, board)){
                                return true;
                            }
                        }

                        if(lowerCase == 'b'){
                            if(new Bishop(pieceWhite).validMove(r, kingr, c, kingc, board)){
                                return true;
                            }
                        }
                        if(lowerCase == 'n'){
                            if(new Knight(pieceWhite).validMove(r, kingr, c, kingc, board)){
                                return true;
                            }
                        }
                        if(lowerCase == 'k'){
                            if(new King(pieceWhite).validMove(r, kingr, c, kingc, board)){
                                return true;
                            }
                        }
                        if(lowerCase == 'q'){
                            if(new Queen(pieceWhite).validMove(r, kingr, c, kingc, board)){
                                return true;
                            }
                        }

                    } catch (IllegalArgumentException e){

                    }
                }
            }
            return false;

    }
}
