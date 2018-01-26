package crossword.Files;

import crossword.board.Crossword;

public interface IWriter {
    void write(Crossword cw);
    long getUniqueID();
}
