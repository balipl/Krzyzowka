package crossword.board;

import crossword.dictionary.CwEntry;
import crossword.dictionary.Direction;
import crossword.dictionary.Entry;
import crossword.exceptions.TooBigBoardException;
import crossword.exceptions.WordNotFoundException;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;

public class SimpleStrategy extends Strategy implements Serializable {
    static final long serialVersionUID = -7588980448693010399L;

    //funkcja, wyszukuj?ca has?o do krzyzowki
    @Override
    public CwEntry findEntry(Crossword cw) throws TooBigBoardException, WordNotFoundException {
        Iterator<CwEntry> it = cw.getROEntryIter(); //lista hasel ktore JUZ sa w krzyzowce
        if(!it.hasNext()){ //jesli nie ma zadnego hasla w krzyzowce, to trzeba znalezc to pierwsze, ktore jest pionowe
            Entry n = cw.getCwDB().getRandom(cw.getBoardCopy().getHeight());  //losujemy slowo o dlugosci rownej wysokosci planszy
            CwEntry haslo = new CwEntry(n.getWord(),n.getClue(), Direction.VERT,0,0);
            return haslo;
        }
        else{//gdy w krzyzowce jest juz jakies haslo
            CwEntry first = it.next();   //pierwsze slowo (pionowe)
            int i = 0;
            while(it.hasNext()){ //sprawdzamy ktore haslo wyszukujemy
                i++;
                it.next();
            }

            if(i==first.getWord().length()) return null;  //jesli liczba hasel jest rowna dlugosci slowa, to konczymy

            Random generator = new Random();
            int los = generator.nextInt(cw.getBoardCopy().getWidth() - 3) + 2;  //losujemy dlugosc hasla z zakresu (2,szerokosc)
            String pat = cw.getBoardCopy().createPattern(first.getX(), first.getY()+i, los, first.getY()+i,this); //tworzymy pattern dla slowa okreslonej dlugosci
            Entry e = cw.getCwDB().getRandom(pat); //wyszukujemy slowo pasujace do danego patternu

            int licznik = 0;
            while (e==null) { //dopoki nie znajdziemy odpowiedniego hasla

                    los = generator.nextInt(cw.getBoardCopy().getWidth() - 4)+4;
                    pat = cw.getBoardCopy().createPattern(first.getX(), first.getY()+i, los, first.getY()+i,this);
                    e = cw.getCwDB().getRandom(pat);
                    if (e!=null && cw.contains(e.getWord())) e=null;

                licznik += 1;
                if(licznik > 1000) throw new WordNotFoundException("Nie znaleziono wyrazu pasuj?cego do hasla");
                }


            CwEntry haslo = new CwEntry(e.getWord(),e.getClue(),Direction.HORIZ,0,i);
            return haslo;
        }
    }

    @Override
    public void updateBoard(Board b, CwEntry e) {
        int xEntry = e.getX();
        int yEntry = e.getY();
        Direction d = e.getD();

        if(d.equals(Direction.VERT)){ //dla hasla pionowego
            for (int i = yEntry; i < e.getWord().length(); i++) {
                BoardCell c = new BoardCell();
                c.setContent(e.getWord().substring(i-yEntry,i-yEntry+1));
                b.setCell(xEntry,i,c);
            }
            }
            else{ //dla hasla poziomego
            for(int i=xEntry;i<e.getWord().length();i++) {
                BoardCell c = new BoardCell();
                c.setContent(e.getWord().substring(i-xEntry,i-xEntry+1));
                b.setCell(i,yEntry,c);
            }
        }
    }
}
