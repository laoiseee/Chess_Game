public class Board {
    private char[][] board;

    public Board(){
        board = new char[8][8];
        setup();
    }

    private void setup(){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                board[row][col] = '.';
            }
        }
        char[] black = {'r', 'n', 'b', 'q', 'k', 'b', 'h', 'r'};
        char[] white = {'R', 'N', 'B', 'Q', 'K', 'B', 'H', 'R'};

        for (int i = 0; i<8; i++){
            board[0][i] = black[i];
            board[7][i] = white[i];
            board[1][i] = 'p';
            board[6][i] = 'P';
        }

    }

    public void print(){
        int num = 1;
        System.out.println("   _ _ _ _ _ _ _ _ ");
        for (int row = 0; row < 8; row++){
            System.out.print(num + " | ");
            num++;
            for (int col = 0; col < 8; col++){
                System.out.print(board[row][col]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println("   _ _ _ _ _ _ _ _ ");
        System.out.print("   a b c d e f g h");
    }

    public boolean move(int r1, int r2, int c1, int c2){
        char piece = board[r1][c1];
        boolean valid = false;
        if(piece == '.'){
            System.out.println("No piece found");
            return false;

        }
        if(piece == 'P'){
            Pawn pawn = new Pawn(true);
            try {
                valid = pawn.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }

        }

        if(piece == 'p'){
            Pawn pawn = new Pawn(false);
            try {
                valid = pawn.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }

        }
        if(piece == 'R'){
            Rook rook = new Rook(true);
            try {
                valid = rook.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }
        }
        if(piece == 'r'){
            Rook rook = new Rook(false);
            try {
                valid = rook.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }
        }

        if(piece =='B'){
            Bishop bishop = new Bishop(true);
            try {
                valid = bishop.validMove(r1, r2, c1, c2, this);
            }  catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }
        }

        if(piece == 'b'){
            Bishop bishop = new Bishop(false);
            try{
            valid = bishop.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }

        }

        if(piece == 'Q'){
            Queen queen = new Queen(true);
            try {
                valid = queen.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }
        }
        if(piece == 'q'){
            Queen queen = new Queen(false);
            try {
                valid = queen.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid move: " + e.getMessage());
                valid = false;
            }
        }

        if(piece =='N'){
            Knight knight = new Knight(true);
            try{
                valid = knight.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid Move: "+e.getMessage());
                valid = false;
            }
        }

        if(piece =='n'){
            Knight knight = new Knight(false);
            try{
                valid = knight.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid Move: "+e.getMessage());
                valid = false;
            }
        }

        if(piece == 'K'){
            King king = new King(true);
            try {
                valid = king.validMove(r1, r2, c1, c2, this);
            }catch(IllegalArgumentException e){
                System.out.println("Invalid Move: "+e.getMessage());
                valid = false;
            }
        }

        if(piece == 'k'){
            King king = new King(false);
            try {
                valid = king.validMove(r1, r2, c1, c2, this);
            } catch(IllegalArgumentException e){
                System.out.println("Invalid Move: "+e.getMessage());
                valid = false;
            }
        }

        if (valid){
            board[r2][c2] = piece;
            board[r1][c1] =  '.';
            print();
        }
        return valid;
    }

    public char getPiece(int r1, int c1){
        return board[r1][c1];
    }
}