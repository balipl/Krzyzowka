package crossword.dictionary;

import java.io.Serializable;

//klasa przedstawiajaca pojedyncze haslo krzyzowki
public class Entry implements Serializable {
    static final long serialVersionUID = -7588980448693010399L;
    //pola slowo i podpowiedz
    private String word;
    private String clue;

    //konstruktor
    public Entry(String word, String clue) {
        this.word = word;
        this.clue = clue;
    }

    public Entry(){
        this.word = "xx";
        this.clue = "xx";
    }

    //getery
    public String getWord() {
        return this.word;
    }

    public String getClue() {
        return this.clue;
    }
}
