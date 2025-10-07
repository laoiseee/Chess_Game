//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Board b1 = new Board();
        b1.print();
        gameStart game = new gameStart();
        game.turn(b1);



    }
}