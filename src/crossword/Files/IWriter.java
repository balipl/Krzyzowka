package crossword.Files;

import crossword.board.Crossword;

public interface Writer {
    void write(Crossword cw);
    long getUniqueID();
}
