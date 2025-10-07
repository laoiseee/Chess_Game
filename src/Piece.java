public class Piece {
    boolean isWhite; //true if white, false if black
    char symbol; //symbol for piece

    public Piece(boolean colour, char pieceLetter){
        isWhite = colour;
        symbol =  pieceLetter;
    }
    public Piece(){

    }

    public boolean isWhite(){
        return isWhite;
    }

    public char getSymbol(){
        return symbol;
    }

    public boolean validMove(){
        return true;
    }
}
