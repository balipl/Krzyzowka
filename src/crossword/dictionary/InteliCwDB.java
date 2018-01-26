package crossword.dictionary;

import crossword.exceptions.WordNotFoundException;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.Collator;
import java.util.*;

//rozszerzenie CwDB ktore potrafi zwracac losowe hasla i szukac po patternie
public class InteliCwDB extends CwDB implements Serializable {
    static final long serialVersionUID = -7588980448693010399L;
    //konstruktor wywolujacy konstruktor klasy bazowej, ktory tworzy DB na podstawie pliku filename
    public InteliCwDB(String filename) throws FileNotFoundException {
        super(filename);
    }
    public InteliCwDB() throws FileNotFoundException {super("F:\\Studia\\Semestr 3\\Programowanie_Obiektowe\\Krzyzowka\\res\\cwdb.txt");}

    //metoda zwracaj?ca list? wszystkich hase? pasuj?cych do wzorca
    public LinkedList<Entry> findAll(String pattern){
        LinkedList<Entry> list = new LinkedList<>();
        for(Entry e : dict ){
            if(e.getWord().matches(pattern)) list.add(e);
        }
        return list;
    }

    //metoda zwracajaca losowe dowolne slowo ze slownika
    public Entry getRandom(){
        Random generator = new Random();
        return dict.get(generator.nextInt(getSize()));
    }

    //metoda zwracajaca losowe slowo o okreslonej dlugosci ze slownika
    public Entry getRandom(int length) throws WordNotFoundException {
        String pattern = "^";
        for (int i = 0; i < length; i++) {
            pattern+=".";
        }
        pattern+="$";
        LinkedList<Entry> lista = findAll(pattern);

        if(lista.isEmpty()) throw new WordNotFoundException("Nie znaleziono slowa o okreslonej dlugosci");

        Random generator = new Random();
        return lista.get(generator.nextInt(lista.size()));
    }
    //metoda zwracajaca losowe slowo o okreslonym wzorze
    public Entry getRandom(String pattern) throws WordNotFoundException {
        LinkedList<Entry> lista = findAll(pattern);
        //if(lista.isEmpty()) throw new WordNotFoundException("Nie znaleziono s?owa o okre?lonym wzorcu");
        if(lista.isEmpty()) return null;
        Random generator = new Random();
        return lista.get(generator.nextInt(lista.size()));
    }

    //przedefiniowana metoda z CwDB, ktora sortuje baz? przy wykorzystaniu Comparatora i Collatora(sortowanie w j?zyku polskim)
    public void add(String word, String clue){
        dict.add(new Entry(word,clue));
        Collections.sort(dict, new Comparator<Entry>() {

            private Collator collator = Collator.getInstance(new Locale("pl","PL"));

            @Override
            public int compare(Entry e1, Entry e2) {
                return collator.compare(e1.getWord(),e2.getWord());
            }
        });
    }
}
