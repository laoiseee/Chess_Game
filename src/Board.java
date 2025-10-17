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
        history.clear();
        lastWhiteMove = null;
        lastBlackMove = null;
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                board[row][col] = '.';
            }

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

    public boolean inCheck(boolean white){
        try{
            return new CheckDetect(this).detect(white);
    }catch (IllegalArgumentException e){
            return false;
        }

    }

    public List<PastMove> getLegalMoves(boolean white){
        List <PastMove> legal = new ArrayList<>();

        for(int r1 = 0; r1 < 8; r1++){
            for(int c1 = 0; c1 < 8; c1++){
                char piece = board[r1][c1];
                if(piece == '.'){
                    continue;
                }
                if(Character.isUpperCase(piece)!= white){
                    continue;
                }

                for(int r2 = 0; r2 < 8; r2++){
                    for(int c2 = 0; c2 < 8; c2++){
                        if(r1 == r2 && c1 == c2){
                            continue;
                        }
                        boolean moveIsValid;
                        char pieceType = Character.toLowerCase(piece);

                        try{
                            if(pieceType == 'p'){
                                moveIsValid = new Pawn(white).validMove(r1, r2, c1, c2, this);
                            } else if(pieceType == 'r'){
                                moveIsValid = new Rook(white).validMove(r1, r2, c1, c2, this);
                            } else if(pieceType == 'n'){
                                moveIsValid = new Knight(white).validMove(r1, r2, c1, c2, this);
                            } else if(pieceType == 'b'){
                                moveIsValid = new Bishop(white).validMove(r1, r2, c1, c2, this);
                            } else if(pieceType == 'q'){
                                moveIsValid = new Queen(white).validMove(r1, r2, c1, c2, this);
                            } else  if(pieceType == 'k'){
                                moveIsValid = new King(white).validMove(r1, r2, c1, c2, this);
                            } else{
                                moveIsValid = false;
                            }
                        }  catch(IllegalArgumentException e){
                            moveIsValid = false;
                        }
                        if(!moveIsValid){
                            continue;
                        }

                        char dest = board[r2][c2];
                        board[r2][c2] = piece;
                        board[r1][c1] = '.';

                        boolean LeavesinCheck;

                        try{
                            LeavesinCheck = new CheckDetect(this).detect(white);
                        } catch (IllegalArgumentException e){
                            LeavesinCheck = true;
                        }

                        board[r1][c1] = piece;
                        board[r2][c2] = dest;

                        if(!LeavesinCheck){
                            legal.add(new PastMove(r1, r2, c1, c2, piece, dest));
                        }


                    }
                }

            }
        }
        return legal;

    }

    public boolean hasLegalMoves(boolean white){
        return !getLegalMoves(white).isEmpty();

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