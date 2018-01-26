package crossword.dictionary;

import java.io.Serializable;

//klasa obiektu hasla krzyzowkowego, ktory dodatkowo ma kierunek i pozycje x,y
public class CwEntry extends Entry implements Serializable{
    static final long serialVersionUID = -7588980448693010399L;
    //pola
    private int x;
    private int y;
    private Direction d;

    //metody

    public CwEntry(String word, String clue, Direction d, int x, int y) {
        super(word, clue);
        this.x = x;
        this.y = y;
        this.d = d;

    }

    public CwEntry(){
        super();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getD() {
        return d;
    }
}
