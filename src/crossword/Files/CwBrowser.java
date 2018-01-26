package crossword.Files;

import crossword.board.Board;
import crossword.board.Crossword;
import crossword.board.Strategy;
import crossword.dictionary.InteliCwDB;
import crossword.exceptions.TooBigBoardException;
import crossword.exceptions.WordNotFoundException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

public class CwBrowser implements Serializable {
    private LinkedList<Crossword> crosswords = new LinkedList<>();
    static final long serialVersionUID = -7588980448693010399L;

    public void saveCrossword(Crossword cw){
        Writer w = new Writer();
        w.write(cw);
    }

    public void readCrossword(String file) throws IOException, ClassNotFoundException {
        Reader r = new Reader(file);
        addCrossword(r.getCrossword());
    }
    public int readAllCrosswords(String file) throws IOException, ClassNotFoundException {
        Reader r = new Reader(file);
        return addAllCrosswords(r.getAllCws());
    }

    public void addCrossword(Crossword cw){
        crosswords.add(cw);
    }

    public int addAllCrosswords(LinkedList<Crossword> list){
        crosswords.addAll(list);
        return crosswords.size()-1;
    }

    public Crossword getCrossword(int index){
        return crosswords.get(index);
    }

    public int getNumberOfCrosswords(){
        return crosswords.size();
    }
    public Crossword getLastCrossword(){
        return crosswords.getLast();
    }
    public Crossword generateCrossword(int height, int width, Strategy s, InteliCwDB cwDB) throws FileNotFoundException, TooBigBoardException, WordNotFoundException {
        if(cwDB==null){
            cwDB = new InteliCwDB("res/cwdb.txt");
        }
        Crossword cw = new Crossword();
        cw.setCwDB(cwDB);
        Board b = new Board(width,height);
        cw.setBoard(b);
        cw.generate(s);
        return cw;
    }

}
