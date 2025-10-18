import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanIn = new Scanner(System.in);
        Board b1 = new Board();

        System.out.println("Type 'load' to resume an old game, or press Enter to start a new one");
        String choice = scanIn.nextLine().trim();
        if (choice.toLowerCase().equals("load")) {
            GameSaveandLoad.load(b1, "saved_game.txt");

        }
        b1.print();
        gameStart game = new gameStart();
        game.turn(b1);



    }
}