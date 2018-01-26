package crossword.Files;

import crossword.board.Crossword;

import java.io.IOException;
import java.util.LinkedList;

public interface IReader {
    public LinkedList<Crossword> getAllCws() throws IOException, ClassNotFoundException;
}
