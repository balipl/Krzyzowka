package crossword.Files;

import crossword.board.Crossword;

import java.util.LinkedList;

public interface Reader {
    public LinkedList<Crossword> getAllCws();
}
