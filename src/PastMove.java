public class PastMove {
    public int r1;
    public int c1;
    public int r2;
    public int c2;
    public char piece;
    public char dest;

    public PastMove(int r1, int r2, int c1, int c2, char piece, char dest) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.piece = piece;
        this.dest = dest;
    }

    public static String square(int r, int c){
        char letter = (char)('a'+ c);
        int rank = 1+r;
        return "" + letter +rank;
    }

    @Override
    public String toString(){
        String base = (piece + "(" + square(r1,c1) + "," + square(r2,c2) +")");
        return (dest =='.')?base:base + "X" + Character.toUpperCase(dest) ;


    }
}
