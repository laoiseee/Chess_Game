import java.util.Scanner;

public class gameStart {
    boolean whiteTurn = true;
    public boolean gameEnd = false;
    public String name1;
    public String name2;

    public gameStart() {
        Scanner scanIn = new Scanner(System.in);

        System.out.println("\nWelcome");
        System.out.println("Please enter the name of player one (white): ");
        name1 = scanIn.nextLine();
        System.out.println("Please enter the name of player two (black): ");
        name2 = scanIn.nextLine();

    }

    public void turn(Board board) {
        Scanner scanIn = new Scanner(System.in);
        boolean valid;

        while(!gameEnd) {
            while (whiteTurn == true) {
                System.out.println(name1 + ", it's your turn");
                System.out.println("Please enter a move: ");

                String start = scanIn.next();
                String end = scanIn.next();

                int c1 = 0;
                int c2 = 0;

                if (start.startsWith("b")) {
                    c1 = 1;
                } else if (start.startsWith("c")) {
                    c1 = 2;
                } else if (start.startsWith("d")) {
                    c1 = 3;
                } else if (start.startsWith("e")) {
                    c1 = 4;
                } else if (start.startsWith("f")) {
                    c1 = 5;
                } else if (start.startsWith("g")) {
                    c1 = 6;
                } else if (start.startsWith("h")) {
                    c1 = 7;
                }

                if (end.startsWith("b")) {
                    c2 = 1;
                } else if (end.startsWith("c")) {
                    c2 = 2;
                } else if (end.startsWith("d")) {
                    c2 = 3;
                } else if (end.startsWith("e")) {
                    c2 = 4;
                } else if (end.startsWith("f")) {
                    c2 = 5;
                } else if (end.startsWith("g")) {
                    c2 = 6;
                } else if (end.startsWith("h")) {
                    c2 = 7;
                }

                int r1 = 0;
                int r2 = 0;

                if (start.endsWith("1")) {
                    r1 = 0;
                } else if (start.endsWith("2")) {
                    r1 = 1;
                } else if (start.endsWith("3")) {
                    r1 = 2;
                } else if (start.endsWith("4")) {
                    r1 = 3;
                } else if (start.endsWith("5")) {
                    r1 = 4;
                } else if (start.endsWith("6")) {
                    r1 = 5;
                } else if (start.endsWith("7")) {
                    r1 = 6;
                } else if (start.endsWith("8")) {
                    r1 = 7;
                }

                if (end.endsWith("1")) {
                    r2 = 0;
                } else if (end.endsWith("2")) {
                    r2 = 1;
                } else if (end.endsWith("3")) {
                    r2 = 2;
                } else if (end.endsWith("4")) {
                    r2 = 3;
                } else if (end.endsWith("5")) {
                    r2 = 4;
                } else if (end.endsWith("6")) {
                    r2 = 5;
                } else if (end.endsWith("7")) {
                    r2 = 6;
                } else if (end.endsWith("8")) {
                    r2 = 7;
                }

                char current = board.getPiece(r1, c1);

                if (current == 'p' || current == 'n' || current == 'r' || current == 'b' || current == 'q' || current == 'k') {
                    System.out.println("It is not your turn");
                    continue;

                }

                valid = board.move(r1, r2, c1, c2);
                if (valid) {
                    System.out.println("Valid move entered");
                    whiteTurn = false;
                } else {
                    System.out.println("Invalid move entered, please try again");
                }


            }

            while (whiteTurn == false) {

                System.out.println(name2 + ", it's your turn");
                System.out.println("Please enter a move: ");

                String start = scanIn.next();
                String end = scanIn.next();

                int c1 = 0;
                int c2 = 0;

                if (start.startsWith("b")) {
                    c1 = 1;
                } else if (start.startsWith("c")) {
                    c1 = 2;
                } else if (start.startsWith("d")) {
                    c1 = 3;
                } else if (start.startsWith("e")) {
                    c1 = 4;
                } else if (start.startsWith("f")) {
                    c1 = 5;
                } else if (start.startsWith("g")) {
                    c1 = 6;
                } else if (start.startsWith("h")) {
                    c1 = 7;
                }

                if (end.startsWith("b")) {
                    c2 = 1;
                } else if (end.startsWith("c")) {
                    c2 = 2;
                } else if (end.startsWith("d")) {
                    c2 = 3;
                } else if (end.startsWith("e")) {
                    c2 = 4;
                } else if (end.startsWith("f")) {
                    c2 = 5;
                } else if (end.startsWith("g")) {
                    c2 = 6;
                } else if (end.startsWith("h")) {
                    c2 = 7;
                }

                int r1 = 0;
                int r2 = 0;

                if (start.endsWith("1")) {
                    r1 = 0;
                } else if (start.endsWith("2")) {
                    r1 = 1;
                } else if (start.endsWith("3")) {
                    r1 = 2;
                } else if (start.endsWith("4")) {
                    r1 = 3;
                } else if (start.endsWith("5")) {
                    r1 = 4;
                } else if (start.endsWith("6")) {
                    r1 = 5;
                } else if (start.endsWith("7")) {
                    r1 = 6;
                } else if (start.endsWith("8")) {
                    r1 = 7;
                }

                if (end.endsWith("1")) {
                    r2 = 0;
                } else if (end.endsWith("2")) {
                    r2 = 1;
                } else if (end.endsWith("3")) {
                    r2 = 2;
                } else if (end.endsWith("4")) {
                    r2 = 3;
                } else if (end.endsWith("5")) {
                    r2 = 4;
                } else if (end.endsWith("6")) {
                    r2 = 5;
                } else if (end.endsWith("7")) {
                    r2 = 6;
                } else if (end.endsWith("8")) {
                    r2 = 7;
                }


                char current = board.getPiece(r1, c1);

                if (current == 'P' || current == 'N' || current == 'R' || current == 'B' || current == 'Q' || current == 'K') {
                    System.out.println("It is not your turn");
                    continue;

                }
                valid = board.move(r1, r2, c1, c2);

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
