import java.util.*;

public class gameStart {
    boolean whiteTurn = true;
    public boolean gameEnd = false;
    public String name1;
    public String name2;
    private Scanner scanIn;

    public gameStart(){
        this(new Scanner(System.in));

    }

    public gameStart(Scanner scanIn) {
        this.scanIn = scanIn;

        //Get names and welcome
        System.out.println("\nWelcome");
        System.out.println("Please enter the name of player one (white): ");
        name1 = scanIn.nextLine();
        System.out.println("Please enter the name of player two (black): ");
        name2 = scanIn.nextLine();
        System.out.println("Welcome "+name1+" and "+name2+".");
    }

    //get histor
    public void printHistory(Board board){
        List<PastMove> history = board.getHistory();
        if(history == null||history.isEmpty()){
            System.out.println("No moves yet.");
            return;
        }
        for (int i = 0; i<history.size(); i++){
            PastMove move = history.get(i);
            String player = Character.isUpperCase(move.piece)? "White":"Black";
            System.out.println((i+1)+": "+player+": "+move);
        }

    }
    //converting input to array destination
    private int filetoColumn(char file){
        return file - 'a';

    }

    private int rowChange(char row){
        return row - '1';
    }

    private int[] newSquare(String spot){
        if(spot == null){
            throw new IllegalArgumentException("No square entered");
        }
        spot = spot.trim().toLowerCase();
        if(spot.length() !=2) {
            throw new IllegalArgumentException("Invalid square entered. Please enter move in the format use a1...h8 8(e.g) 'a7 a5'");
        }
            char col = spot.charAt(0);
            char row = spot.charAt(1);

            if(col < 'a' || col > 'h'||row <  '1' || row > '8'){
                throw new IllegalArgumentException("Square out of range");
            }
            int c = filetoColumn(col);
            int r = rowChange(row);

            return new int[]{r,c};
    }
    //each turn
    public void turn(Board board) {
        if(!board.getHistory().isEmpty()){
            PastMove lastMove = board.getHistory().getLast();
            whiteTurn =  Character.isLowerCase(lastMove.piece);
        }

        boolean valid;


        while(!gameEnd) {
            //white turn
            if(whiteTurn == true && !gameEnd) {
                //checkmate and stalemate
                if (!board.hasLegalMoves(true)) {
                    if(board.inCheck(true)){
                        System.out.println("Checkmate. Black wins!");
                    } else{
                        System.out.println("Stalemate. Game Over!");
                    }
                    gameEnd = true;
                    return;

                }
                //print opponents last move
                PastMove prevBlack = board.getLastBlackMove();
                if (prevBlack != null) {
                    System.out.println("Blacks Last Move: "+prevBlack);

                }
                //turn prompt
                System.out.println(name1 + ", it's your turn");
                System.out.println("Type 'save' to save the game, 'load' to reload a game, 'history' to view move history, 'moves' to see all available moves or 'quit' to quit the game");
                System.out.println("Please enter a move: ");

                String start = scanIn.next();
                //save case
                if (start.toLowerCase().equals("save")) {
                    GameSaveandLoad.save(board, "saved_game.txt");
                    continue;
                }

                //load case
                if(start.toLowerCase().equals("load")) {
                    GameSaveandLoad.load(board, "saved_game.txt");
                    continue;
                }

                //quit case
                if(start.toLowerCase().equals("quit")){
                    System.out.println("Game ended by "+name1);
                    gameEnd = true;
                    return;
                }

                //get history case
                if(start.toLowerCase().equals("history")){
                    printHistory(board);
                    continue;
                }

                //get legal moges case
                if(start.toLowerCase().equals("moves")){
                    List<PastMove> moves = board.getLegalMoves(true);
                    if(moves==null||moves.isEmpty()){
                        System.out.println("No legal moves found.");
                    } else{
                        System.out.println("Legal Moves: ("+moves.size()+")");
                        for(int i = 0; i < moves.size(); i++){
                            System.out.println(i+1+": "+moves.get(i));
                        }
                    }
                    continue;

                }
                //make move
                int[] startSpot;
                try{
                    startSpot = newSquare(start);
                } catch(IllegalArgumentException e){
                    System.out.println("Input error: "+e.getMessage());
                    continue;
                }

                String end = scanIn.next();
                if(end.toLowerCase().equals("quit")){
                    System.out.println("Game ended by "+name1);
                    gameEnd = true;
                    return;
                }



                int[] endSpot;
                try{
                    endSpot = newSquare(end);
                }catch(IllegalArgumentException e){
                    System.out.println("Input error: "+e.getMessage());
                    continue;
                }
                int r1 = startSpot[0];
                int r2 = endSpot[0];
                int c1 = startSpot[1];
                int c2 = endSpot[1];


                char current = board.getPiece(r1, c1);

                //validate turn
                if (current == 'p' || current == 'n' || current == 'r' || current == 'b' || current == 'q' || current == 'k') {
                    System.out.println("It is not your turn");
                    continue;

                }

                // check move is valid
                try{
                    valid = board.move(r1, r2, c1, c2);
                }catch(IllegalStateException ex){

                    //pawn promotion
                    if("Promotion required.".equals(ex.getMessage())) {
                        while(true){
                        System.out.println("Pawn Promotion! Enter Piece (R, Q, N or N)");
                        String s = scanIn.next();
                        if (s == null || s.isEmpty()) {
                            continue;
                        }
                        char choice = Character.toUpperCase(s.charAt(0));
                        try {
                            board.setPromotionChoice(choice);
                            break;

                        } catch (IllegalArgumentException exChoice) {
                            System.out.println(exChoice.getMessage());

                        }
                    }
                        valid = board.move(r1, r2, c1, c2);
                    } else{
                       valid = false;
                    }


                }catch(IllegalArgumentException ex){
                    valid = false;
                }
                if (valid) {
                    System.out.println("Valid move entered");
                    whiteTurn = false;
                } else {
                    System.out.println("Invalid move entered, please try again");
                }


            }

            //black turn
            if(whiteTurn == false && !gameEnd) {
                //checkmate and stalemate
                if (!board.hasLegalMoves(false)) {
                    if (board.inCheck(false)) {
                        System.out.println("Checkmate. White wins!");
                    } else{
                        System.out.println("Stalemate. Game Over!");
                    }
                    gameEnd = true;
                    return;

                    }
                //print opponents last move
                PastMove prevWhite = board.getLastWhiteMove();
                if (prevWhite != null) {
                    System.out.println("Whites Last Move: "+prevWhite);

                }

                //turn prompt
                System.out.println(name2 + ", it's your turn");
                System.out.println("Type 'save' to save the game, 'load' to reload a game, 'history' to view move history, 'moves' to see all available moves or 'quit' to quit the game");
                System.out.println("Please enter a move: ");

                String start = scanIn.next();
                //save case
                if (start.toLowerCase().equals("save")) {
                    if(board.isPromotionPending()) {
                        System.out.println("Finish promotion before saving");
                    }

                        GameSaveandLoad.save(board, "saved_game.txt");
                        continue;

                }

                //load case
                if(start.toLowerCase().equals("load")) {
                    GameSaveandLoad.load(board, "saved_game.txt");
                    continue;
                }

                //quit case
                if(start.toLowerCase().equals("quit")){
                    System.out.println("Game ended by "+name2);
                    gameEnd = true;
                    return;
                }
                //get history case
                if(start.toLowerCase().equals("history")){
                    printHistory(board);
                    continue;
                }

                //get legal moves case
                if(start.toLowerCase().equals("moves")){
                    List<PastMove> moves = board.getLegalMoves(false);
                    if(moves==null||moves.isEmpty()){
                        System.out.println("No legal moves found.");
                    } else{
                        System.out.println("Legal Moves: ("+moves.size()+")");
                        for(int i = 0; i < moves.size(); i++){
                            System.out.println(i+1+": "+moves.get(i));
                        }
                    }
                    continue;

                }

                //make move
                int[] startSpot;
                try{
                    startSpot = newSquare(start);
                } catch(IllegalArgumentException e){
                    System.out.println("Input error: "+e.getMessage());
                    continue;
                }


                String end = scanIn.next();
                if(end.toLowerCase().equals("quit")){
                    System.out.println("Game ended by "+name2);
                    gameEnd = true;
                    return;
                }

                //make move
                int[] endSpot;
                try{
                    endSpot = newSquare(end);
                }catch(IllegalArgumentException e){
                    System.out.println("Input error: "+e.getMessage());
                    continue;
                }
                int r1 = startSpot[0];
                int r2 = endSpot[0];
                int c1 = startSpot[1];
                int c2 = endSpot[1];


                char current = board.getPiece(r1, c1);

                //check turn valid
                if (current == 'P' || current == 'N' || current == 'R' || current == 'B' || current == 'Q' || current == 'K') {
                    System.out.println("It is not your turn");
                    continue;

                }

                //pawn promotion
                try{
                    valid = board.move(r1, r2, c1, c2);
                }catch(IllegalStateException ex){

                    if("Promotion required.".equals(ex.getMessage())) {
                        while(true){
                            System.out.println("Pawn Promotion! Enter Piece (R, Q, N or N)");
                            String s = scanIn.next();
                            if (s == null || s.isEmpty()) {
                                continue;
                            }
                            char choice = Character.toUpperCase(s.charAt(0));
                            try {
                                board.setPromotionChoice(choice);
                                break;

                            } catch (IllegalArgumentException exChoice) {
                                System.out.println(exChoice.getMessage());

                            }
                        }
                        valid = board.move(r1, r2, c1, c2);
                    } else{
                        valid = false;
                    }


                }catch(IllegalArgumentException ex){
                    valid = false;
                }

                if (valid) {
                    System.out.println("\nValid move entered");
                    whiteTurn = true;
                } else {
                    System.out.println("\nInvalid move entered, please try again");
                }
            }
        }

    }
}
