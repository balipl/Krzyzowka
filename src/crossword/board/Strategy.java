package crossword.board;

import crossword.dictionary.CwEntry;
import crossword.exceptions.TooBigBoardException;
import crossword.exceptions.WordNotFoundException;

public abstract class Strategy {
    public abstract CwEntry findEntry(Crossword cw) throws TooBigBoardException, WordNotFoundException;
    public abstract void updateBoard(Board b, CwEntry e);
}
