import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.*;

public class GameSaveandLoad {
    //save method
    public static void save(Board board, String filename){
        try{
            //create snapshot of current board
            StringBuilder boardState = new StringBuilder();
            for(int r = 0; r<8; r++){
                for(int c = 0; c<8; c++){
                    boardState.append(board.getPiece(r, c));
                }
                boardState.append("\n");
            }
            boardState.append("---\n");
            //add history to file
            StringBuilder sb = new StringBuilder(boardState);
            for(PastMove move: board.getHistory()){
                sb.append(move.r1).append(' ')
                        .append(move.r2).append(' ')
                        .append(move.c1).append(' ')
                        .append(move.c2).append('\n');
            }
            //save file
            Files.writeString(Path.of(filename), sb.toString());
            System.out.println("Game saved to " + filename);

        }catch(Exception e){
            System.out.println("Save failed" + e.getMessage());

        }
    }


// load methof
    public static void load(Board board, String filename){
        try{
            //reset board
            board.gameReset();

            //load in content
            String gameContent =  Files.readString(Path.of(filename));
            String[] lines = gameContent.split("\n");

            int lineIndex = 0;
            board.clearBoard();

            //find and get last board state
          for(int r = 0; r < 8 && lineIndex<lines.length;r++, lineIndex++){
              String line = lines[lineIndex].trim();
              if(line.equals("---")){
                  break;
              }
              for(int c = 0; c <  Math.min(line.length(), 8); c++){
                  board.insertPiece(r, c, line.charAt(c));

              }

          }
          if(lineIndex < lines.length && lines[lineIndex].equals("---")){
              lineIndex++;
          }

          for(int i = lineIndex; i < lines.length; i++){
              String line = lines[i];
              if(line.isBlank()){
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
