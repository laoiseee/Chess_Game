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

            if((piece == 'k'||piece == 'K') && r1 ==r2 && Math.abs(c1-c2) == 2){
                int back = r1;
                if(c2>c1){
                    board[back][5] = board[back][7];
                    board[back][7] = '.';
                } else{
                    board[back][3] = board[back][0];
                    board[back][0] = '.';
                }
            }



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

    public void gameReset(){
        setup();
    }

    //castling
    public boolean hasMoved(int r, int c){
        List<PastMove> history = getHistory();
        if(history == null){
            return false;
        }
        for(PastMove move : history){
            if(move.r1 == r && move.c1 == c){
                return true;
            }
        }
        return false;
    }

    public boolean isSquareAttacked(int targetRow, int targetCol, boolean attackerIsWhite){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                char piece = getPiece(r, c);
                if(piece == '.'){
                    continue;
                }

                boolean pieceIsWhite = Character.isUpperCase(piece);
                if(pieceIsWhite != attackerIsWhite){
                    continue;
                }

                char upper =  Character.toUpperCase(piece);
                try{
                    if(upper == 'P'){
                        Pawn pawn = new Pawn(pieceIsWhite);
                        if(pawn.validMove(r,targetRow, c, targetCol, this)){
                            return true;
                        }
                    } else if(upper == 'R'){
                        Rook rook = new Rook(pieceIsWhite);
                        if(rook.validMove(r,targetRow, c, targetCol, this)){
                            return true;
                        }
                    } else if(upper == 'B'){
                        Bishop bishop = new Bishop(pieceIsWhite);
                        if(bishop.validMove(r,targetRow, c, targetCol, this)){
                            return true;
                        }
                    }else if(upper == 'N'){
                        Knight knight = new Knight(pieceIsWhite);
                        if(knight.validMove(r,targetRow, c, targetCol, this)){
                            return true;
                        }
                    } else if(upper == 'Q'){
                        Queen queen = new Queen(pieceIsWhite);
                        if(queen.validMove(r,targetRow, c, targetCol, this)){
                            return true;
                        }
                    }else if(upper == 'K'){
                        int rowDist = Math.abs(targetRow - r);
                        int colDist = Math.abs(targetCol - c);
                        if((rowDist>0||colDist>0)&&rowDist<=1 && colDist<=1){
                            return true;

                        }
                    }
                } catch(IllegalArgumentException ignored){

                }

            }
        }
        return false;
    }

    public boolean canCastle(boolean isWhite, boolean kingSide){
        int backRow;

        if(isWhite){
            backRow = 7;
        } else{
            backRow = 0;
        }

        int rookStartCol;
        if(kingSide){
            rookStartCol = 7;
        } else{
            rookStartCol = 0;
        }

        int kingStartCol = 4;
        int kingEndCol;
        int throughCol; //king cannot pass through a square under attack

        if(kingSide){
            kingEndCol =6;
            throughCol = 5;
        }else{
            kingEndCol = 2;
            throughCol = 3;
        }

        //Expected case of pieces
        char expectKing;
        char expectRook;

        if(isWhite){
            expectKing  = 'K';
            expectRook = 'R';
        } else{
            expectKing  = 'k';
            expectRook = 'r';
        }

        //CHECKS
        //1. pieces on start squares
        if(getPiece(backRow, kingStartCol)!= expectKing){
            return false;
        }
        if(getPiece(backRow, rookStartCol)!= expectRook){
            return false;
        }

        //2. pieces haven't moved
        if(hasMoved(backRow, kingStartCol)){
            return false;
        }
        if(hasMoved(backRow, rookStartCol)){
            return false;
        }

        //3. no pieces between king and rook
        int mover;
        if(rookStartCol>kingStartCol){
            mover = 1;
        }else{
            mover = -1;
        }
        int c = kingStartCol + mover;
        while(c!=rookStartCol){
            if(getPiece(backRow,c)!= '.'){
                return false;
            }
            c+=mover;
        }

        //4. king is not in check
        if(inCheck(isWhite)){
            return false;
        }

        //5. king cannot land on attacked square
        boolean opponentIsWhite;
        if(isWhite){
            opponentIsWhite = false;
        }else{
            opponentIsWhite = true;
        }

        if(isSquareAttacked(backRow, throughCol, opponentIsWhite)){
            return false;
        }
        if(isSquareAttacked(backRow, kingEndCol, opponentIsWhite)){
            return false;
        }
        return true;


    }

}