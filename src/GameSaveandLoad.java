import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.*;

public class GameSaveandLoad {
    public static void save(Board board, String filename){
        try{
            StringBuilder sb = new StringBuilder();
            for(PastMove move: board.getHistory()){
                sb.append(move.r1).append(' ')
                        .append(move.r2).append(' ')
                        .append(move.c1).append(' ')
                        .append(move.c2).append('\n');
            }
            Files.writeString(Path.of(filename), sb.toString());
            System.out.println("Game saved to " + filename);

        }catch(Exception e){
            System.out.println("Save failed" + e.getMessage());

        }
    }

    public static void load(Board board, String filename){
        try{
            board.gameReset();

            String gameContent =  Files.readString(Path.of(filename));
            String[] lines = gameContent.split("\n");

            for (String line: lines){
                if (line.isBlank()){
                    continue;
                }
                String[] pieces = line.split(" ");
                int r1 = Integer.parseInt(pieces[0]);
                int r2 = Integer.parseInt(pieces[1]);
                int c1 = Integer.parseInt(pieces[2]);
                int c2 = Integer.parseInt(pieces[3]);

                board.move(r1, r2, c1, c2);
            }
            System.out.println("Board loaded from " + filename);

        }catch(Exception e){
            System.out.println("Load failed" + e.getMessage());
        }

    }
}
