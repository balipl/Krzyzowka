package crossword.board;

import crossword.dictionary.CwEntry;
import crossword.dictionary.Entry;
import crossword.dictionary.InteliCwDB;
import crossword.exceptions.TooBigBoardException;
import crossword.exceptions.WordNotFoundException;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

//mechanizm obslugujacy krzyzowke
public class Crossword implements Serializable{
    static final long serialVersionUID = -7588980448693010399L;
    //pola
    //lista hasel w krzyzowce
    private LinkedList<CwEntry> entries = new LinkedList<>();
    //plansza
    private Board b;
    //baza danych z wszystkimi dostepnymi haslami
    private InteliCwDB cwdb;
    //id krzyzowki
    private final long ID;

    //konstruktory
    //krzyzowka bez id
    public Crossword(){
        ID = -1;
        b = new Board();
    }
    //krzyzowka budowana z konkretnym id
    public Crossword(long id){
        ID = id;
        b = new Board();
    }
    //krzyzowka z id oraz z plikiem nowej bazy danych
    public Crossword(String file, long id) throws FileNotFoundException {
        ID = id;
        b = new Board();
        cwdb = new InteliCwDB(file);
    }

    //metoda zwracaj?ca iterator Read Only, kt?ry nie pozwala np na usuwanie element?w z listy HASE? KT?RE JU? S? W KRZY??WCE
    public Iterator<CwEntry> getROEntryIter(){
        return Collections.unmodifiableList(entries).iterator();
    }

    //funkcja zwracajaca kopie planszy
    public Board getBoardCopy() throws TooBigBoardException {
        Board board = new Board(b.getWidth(),b.getHeight());
        for (int i=0;i<board.getWidth();i++) {
            for (int j=0;j<board.getHeight();j++) {
                board.setCell(i,j, b.getCell(i, j));
            }
        }
        return board;
    }

    public LinkedList<CwEntry> getEntries() {
        return entries;
    }

    public InteliCwDB getCwDB(){
        return cwdb;
    }
    public void setCwDB(InteliCwDB cwdb){
        this.cwdb = cwdb;
    }

    //metoda sprawdzajaca, czy w danej krzyzowce uzywamy juz danego hasla
    public boolean contains(String word){
        for(CwEntry e : entries){
            if(e.getWord().equals(word)) return true;
        }
        return false;
    }

    //funkcja dodajaca haslo do listy wszystkich hasel w krzyzowce
    public final void addCwEntry(CwEntry cwe, Strategy s){
        entries.add(cwe);
        s.updateBoard(b,cwe);
    }

    //funkcja odpowiadajaca za generowanie krzyzowki korzystajac z wybranej strategii
    public final void generate(Strategy s) throws TooBigBoardException, WordNotFoundException {
        CwEntry e = null;
        while((e = s.findEntry(this)) != null){  //kazde wywolanie tej petli powoduje znalezienie kolenego hasla
                                                        //dla SimpleStrategy, gdy liczba hasel bedzie rowna dlugosci pierwszego slowa
            addCwEntry(e,s);
        }
    }

    public void setBoard(Board board) {
        this.b = board;
    }
}
