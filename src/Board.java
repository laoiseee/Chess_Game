import java.util.*;

/**
 * Represents the choice board and includes all of the board level rules:
 * piece placement, legal moves, performing moves, this
 * includes castling, en passant and pawn promotion.
 * Also includes getting game history and possible moves
 *<p>Board coordinates: back row for 0 for black and 7 for white.
 * File 'a' is column 0 and file 'h' is column 7
 * </p>
 */


public class Board {
    /**8x8 board with uppercase white pieces and lowercase black pieces*/
    private char[][] board;
    /**Last move made by white*/
    private PastMove lastWhiteMove = null;
    /**Last move made by black*/
    private PastMove lastBlackMove = null;
    /**history of moves*/
    private List<PastMove> history = new ArrayList<>();
    /**Holds pawn promotion choice when pawn reaches other side of board*/
    private Character promotionPending = null;

    /**Creates a board in the standard starting formarion*/
    public Board(){
        board = new char[8][8];
        setup();
    }
    /**Clears board history and puts board in starting setup position*/
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
    /**Prints simple view of the board in its current state*/
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

    /**Checks if a side is in check
     * @param white true for white, false for black
     * @return true if king for this side is in check, false if not
     * */

    public boolean inCheck(boolean white){
        try{
            return new CheckDetect(this).detect(white);
    }catch (IllegalArgumentException e){
            return false;
        }

    }
    /**
     * Generates all legal moves for a player,
     * excludes moves that leave the player in check,
     * includes en passant and castling.
     * @param white true to generate white moves and false for black moves.
     * @return gives all legal moves using {@link PastMove}
     * */
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


                        //castling
                        int castleRookFromCol = -1;
                        int castleRookToCol = -1;
                        if((Character.toLowerCase(piece)=='k')&&r1 == r2 &&Math.abs(c1-c2)==2) {
                            int back = r1;
                            if (c2 > c1) {
                                board[back][5] = board[back][7];
                                board[back][7] = '.';
                                castleRookFromCol = 7;
                                castleRookToCol = 5;
                            } else {
                                board[back][3] = board[back][0];
                                board[back][0] = '.';
                                castleRookFromCol = 0;
                                castleRookToCol = 3;
                            }
                        }



                        //en passant
                        int epTempRow = -1;
                        char epTempPiece = '.';
                        if((piece=='p'||piece=='P') && Math.abs(c1-c2) == 1 && dest == '.'&&r2 == getEnPassantTargetRow() && c2 == getEnPassantTargetCol()&&((Character.isUpperCase(piece)&&(r2 - r1)==-1)||(Character.isLowerCase(piece)&&(r2-r1)==1))){
                            if(Character.isUpperCase(piece)){
                                epTempRow = r2 +1;
                            }else{
                                epTempRow = r2 -1;
                            }
                            epTempPiece = board[epTempRow][c2];
                            board[epTempRow][c2] = '.';


                        }

                        boolean LeavesinCheck;

                        try{
                            LeavesinCheck = new CheckDetect(this).detect(white);
                        } catch (IllegalArgumentException e){
                            LeavesinCheck = true;
                        }

                        //castling
                        if(castleRookFromCol != -1){
                            int back = r1;
                            board[back][castleRookFromCol] = board[back][castleRookToCol];
                            board[back][castleRookToCol] = '.';
                        }
                        //en passant
                        if(epTempRow != -1){
                            board[epTempRow][c2] = epTempPiece;
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


    /**Used to see if a piece has legal moves
     * @param white true for white, false for black
     * @return true if{@link #getLegalMoves(boolean)} is not empty
     * */
    public boolean hasLegalMoves(boolean white){
        return !getLegalMoves(white).isEmpty();

    }

    //moving piece logic
    /**
     * Moving piece logic
     * Attempts to move a piece on the board.
     * Checks legality of move (blocking, capturing, en passant, castling and pawn promotion)
     * @param r1 is source row
     * @param r2 is destination row
     * @param c1 is source column
     * @param c2 is destination column
     * <p>@throws IllegalArgumentException if board is in an invalid state (e.g. no king present)
     * @throws IllegalStateException if pawn reaches opposite side and no choice is made for promotion
     * in {@link #setPromotionChoice(char)}
     * @return true for completed move, false otherwise
     * */
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

        //check validity of moves and simulate move to make sure king is not left in check
        if (valid){
            char capturedPiece = board[r2][c2];
            int epTempRow = -1;
            char epTempPiece = '.';
            int castleRookFromCol = -1;
            int castleRookToCol = -1;

            board[r2][c2] = piece;
            board[r1][c1] =  '.';

            //en passant capture simulated
            if((piece=='p'||piece=='P') && Math.abs(c1-c2) == 1 && capturedPiece == '.'&&r2 == getEnPassantTargetRow() && c2 == getEnPassantTargetCol()&&((Character.isUpperCase(piece)&&(r2 - r1)==-1)||(Character.isLowerCase(piece)&&(r2-r1)==1))){
                if(Character.isUpperCase(piece)){
                    epTempRow = r2 +1;
                }else{
                    epTempRow = r2 -1;
                }
                epTempPiece = board[epTempRow][c2];
                board[epTempRow][c2] = '.';


            }

            //castling rook simulated
            if((piece == 'k'||piece == 'K') && r1 ==r2 && Math.abs(c1-c2) == 2){
                int back = r1;
                if(c2>c1){
                    board[back][5] = board[back][7];
                    board[back][7] = '.';
                    castleRookFromCol = 7;
                    castleRookToCol = 5;
                } else{
                    board[back][3] = board[back][0];
                    board[back][0] = '.';
                    castleRookFromCol = 0;
                    castleRookToCol = 3;
                }
            }


            //check detection
            CheckDetect check = new CheckDetect(this);
            boolean isInCheck = check.detect(Character.isUpperCase(piece));

            //undo en passant simulation
            if(epTempRow != -1){
                board[epTempRow][c2] = epTempPiece;
            }

            //undo castling rook simulation
            if(castleRookFromCol != -1){
                int back = r1;
                board[back][castleRookFromCol] = board[back][castleRookToCol];
                board[back][castleRookToCol] = '.';
            }

            //undo main simulation
            board[r1][c1] = piece;
            board[r2][c2] = capturedPiece;

            if(isInCheck){
                System.out.println("This move will put you in check, try again.");
                valid = false;
            }


        }

        //Execute if valid
        if(valid){
            char capturedPiece = board[r2][c2];
            board[r2][c2] = piece;
            board[r1][c1] =  '.';

            //castling rook move
            if((piece == 'k'||piece == 'K')&& r1==r2 && Math.abs(c1-c2) == 2){
                int back = r1;
                if(c2>c1){
                    board[back][5] = board[back][7];
                    board[back][7] = '.';
                } else{
                    board[back][3] = board[back][0];
                    board[back][0] = '.';
                }
            }

            //en passant move
            int epTempRow = -1;
            char epTempPiece = '.';

            if((piece=='p'||piece=='P') && Math.abs(c1-c2) == 1 && capturedPiece == '.'&&r2 == getEnPassantTargetRow() && c2 == getEnPassantTargetCol()&&((Character.isUpperCase(piece)&&(r2 - r1)==-1)||(Character.isLowerCase(piece)&&(r2-r1)==1))){
                if(Character.isUpperCase(piece)){
                    epTempRow = r2 +1;
                }else{
                    epTempRow = r2 -1;
                }
                epTempPiece = board[epTempRow][c2];
                board[epTempRow][c2] = '.';
                capturedPiece = epTempPiece;

            }

            //pawn promotion
            if((piece == 'P'&&r2 == 0)|| (piece == 'p'&&r2 == 7)){
                if(promotionPending == null){
                    board[r1][c1] = piece;
                    board[r2][c2] = capturedPiece;
                    throw new IllegalStateException("Promotion required.");
                }
                char promoteTo;
                if(Character.isUpperCase(piece)){
                    promoteTo = promotionPending;
                } else{
                    promoteTo = Character.toLowerCase(promotionPending);
                }
                board[r2][c2] = promoteTo;
                promotionPending = null;

            }



            //update en passant target square
            clearEnPassantTargetSquare();
            if((piece == 'p'||piece == 'P')&&Math.abs(r1-r2) == 2&& c1==c2){
                setEnPassantTargetSquare((r1+r2)/2, c1);
            }

            //record and print
            recordMove(new PastMove(r1, r2, c1, c2, piece, capturedPiece));
            print();



            //check notifiers
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
    /**
     * sets every piece to '.'
     * used in testing
     * */
    public void clearBoard(){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                board[r][c] = '.';
            }
        }

    }

/**
 * Checks coordinates are in bounds
 * @param r is the row
 * @param c is the colum
 * @return  true if 0<=r and c<=7
 * */
    private boolean inBounds(int r, int c){
        return r >= 0 && r < 8 && c >= 0 && c < 8;

    }

    /**adds a piece on the board
     * very useful in testing
     * @param r is the row
     * @param c is the column
     * @param piece is the piece to be inserted
     * */
    public void insertPiece(int r, int c, char piece){
        board[r][c] = piece;

    }

    /**
     * Getter for piece on certain square
     * @param r1  is te row
     * @param c1 is the column
     * @return the piece at r1, c1*/

    public char getPiece(int r1, int c1){
        return board[r1][c1];
    }

    /**
     * gets last white move
     * @return return returns last move made by white (null if none)*/
    public PastMove getLastWhiteMove(){
        return lastWhiteMove;
    }

    /**
     * gets last black move
     * @return returns last move made by black (null if none)*/
    public PastMove getLastBlackMove(){
        return lastBlackMove;
    }

    /**record past moves and add them to the history
     * @param past is the move to record
     * */
    public void recordMove(PastMove past){
        boolean moveWasWhite = Character.isUpperCase(past.piece);
        if(moveWasWhite){
            lastWhiteMove = past;
        } else{
            lastBlackMove = past;
        }
        history.add(past);
    }

    /**Getter to get the full history of moves made in this game
     * @return gives history*/
    public List<PastMove> getHistory(){
        return history;
    }


    /**Resets the board to starting position and clears states*/
    public void gameReset(){
        setup();
    }

    //castling
    /**Check if piece has moved from starting point
     * @param r is the strat row
     * @param c  is the start column
     * @return true of piece has moved, false otherwise*/
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

    /**Tests if piece is under attack by component if it moves (used for castling)
     * @param targetRow is the squares row
     * @param targetCol  is the column of the square
     * @param attackerIsWhite  true if attackers are white, false if black
     * @return return true if any attcking piece has a valid move to the square*/
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

    /**Checks if castling is possible given side and direction of move
     * @param isWhite true for white, false for black
     * @param kingSide true for king side castling, false for queen side castling
     * @return true if all conditions met for castling, false otherwise*/
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


    //pawn promotion
    /**
     * sets up pending pawn promotion choice (must be Q, R, B, or N)
     * @param choice is teh choice of piece to promote to
     * @throws IllegalArgumentException if{@code choice} is not valid
     * */
    public void setPromotionChoice(char choice){
        choice = Character.toUpperCase(choice);
        if(choice != 'Q'&& choice != 'R'&& choice != 'B'&&choice != 'N'){
          throw new IllegalArgumentException("Invalid choice: Promotion must be 'Q', 'R', 'B' or 'N'");
        }
        this.promotionPending = choice;

    }

    /**
     * shows if promotion choice is penidng
     * @return true if a promotion choice is pending*/
    public boolean isPromotionPending(){
        return promotionPending != null;
    }

    /**row of current en passant target square*/
    private int enPassantTargetRow = -1;

    /**column of current en passant target square*/
    private int enPassantTargetCol = -1;

    /**En passant target row getter
     * @return gievs target row*/
    public int getEnPassantTargetRow() {
        return enPassantTargetRow;
    }

    /**En passant target column getter
     * @return gievs target column*/

    public int getEnPassantTargetCol() {
        return enPassantTargetCol;
    }

    /**sets en passant target square
     * @param r is the row
     * @param c is the column*/
    public void setEnPassantTargetSquare(int r, int c) {
        enPassantTargetRow = r;
        enPassantTargetCol = c;
    }


    /**Clears en passant target square*/
    public void clearEnPassantTargetSquare() {
        enPassantTargetRow = -1;
        enPassantTargetCol = -1;
    }





}