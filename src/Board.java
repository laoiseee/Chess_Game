import java.util.*;

public class Board {
    private char[][] board;
    private PastMove lastWhiteMove = null;
    private PastMove lastBlackMove = null;
    private List<PastMove> history = new ArrayList<>();

    public Board(){
        board = new char[8][8];
        setup();
    }

    private void setup(){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                board[row][col] = '.';
            }
            history.clear();
            lastWhiteMove = null;
            lastBlackMove = null;
        }
        char[] black = {'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'};
        char[] white = {'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};

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
        System.out.println("   a b c d e f g h");
    }

    public boolean move(int r1, int r2, int c1, int c2){
        if(!inBounds(r1,c1)||!inBounds(r2, c2)){
            System.out.println("Move out of Bounds");
            return false;

        }
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
            char capturedPiece = board[r2][c2];
            board[r2][c2] = piece;
            board[r1][c1] =  '.';



            CheckDetect check = new CheckDetect(this);
            boolean isInCheck = check.detect(Character.isUpperCase(piece));

            board[r1][c1] = piece;
            board[r2][c2] = capturedPiece;

            if(isInCheck){
                System.out.println("This move will put you in check, try again.");
                valid = false;
            }


        }

        if(valid){
            char capturedPiece = board[r2][c2];
            board[r2][c2] = piece;
            board[r1][c1] =  '.';
            recordMove(new PastMove(r1, r2, c1, c2, piece, capturedPiece));
            print();

            CheckDetect check = new CheckDetect(this);
            try {
                if (check.detect(true)) {
                    System.out.println("White King is in Check!");
                }
            }catch(IllegalArgumentException ignored){

            }
            try {
                if (check.detect(false)) {
                    System.out.println("Black King is in Check!");
                }
            } catch(IllegalArgumentException ignored){}

        }
        return valid;
    }
    //clear board for testing
    public void clearBoard(){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                board[r][c] = '.';
            }
        }

    }

    private boolean inBounds(int r, int c){
        return r >= 0 && r < 8 && c >= 0 && c < 8;

    }

    //add piece for testing
    public void insertPiece(int r, int c, char piece){
        board[r][c] = piece;

    }

    public char getPiece(int r1, int c1){
        return board[r1][c1];
    }

    public PastMove getLastWhiteMove(){
        return lastWhiteMove;
    }

    public PastMove getLastBlackMove(){
        return lastBlackMove;
    }

    public void recordMove(PastMove past){
        boolean moveWasWhite = Character.isUpperCase(past.piece);
        if(moveWasWhite){
            lastWhiteMove = past;
        } else{
            lastBlackMove = past;
        }
        history.add(past);
    }

    public List<PastMove> getHistory(){
        return history;
    }
}