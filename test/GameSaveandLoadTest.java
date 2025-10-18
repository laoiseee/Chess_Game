import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.nio.*;

class GameSaveandLoadTest {
    private Board board;
    private String file = "test_save.txt";

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @AfterEach //delete file after test
    void clean() throws IOException {
        Files.deleteIfExists(Path.of(file));
    }

    @Test
    void saveGame() throws IOException {
        assertTrue(board.move(6, 4, 4, 4));
        GameSaveandLoad.save(board, file);
        assertTrue(Files.exists(Path.of(file)));
        String gameContent = Files.readString(Path.of(file));
        assertFalse(gameContent.isBlank());

    }

    @Test
    void loadGame() throws IOException {
        assertTrue(board.move(6, 4, 4, 4));
        GameSaveandLoad.save(board, file);

        Board newBoard  = new Board();
        GameSaveandLoad.load(newBoard, file);

        assertEquals('P', newBoard.getPiece(4, 4));
        assertEquals('.', newBoard.getPiece(6, 4));

    }

    @Test
    void missingFile(){
        GameSaveandLoad.load(board, "nofile.txt");
        assertEquals('r', board.getPiece(0, 0));
        assertEquals('R', board.getPiece(7, 0));
    }



}