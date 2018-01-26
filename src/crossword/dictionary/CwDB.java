package crossword.dictionary;

import crossword.exceptions.WordNotFoundException;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

//klasa ktora jest baza danych wszystkich hasel krzyzowkowych
public class CwDB implements Serializable {
    static final long serialVersionUID = -7588980448693010399L;
    //lista wszystkich hasel
    protected LinkedList<Entry> dict = new LinkedList<>();

    //konstruktor przyjmujacy sciezke pliku jako argument
    public CwDB(String filename) throws FileNotFoundException {
        this.createDB(filename);
    }

    public CwDB() throws FileNotFoundException {
        this.createDB("F:\\Studia\\Semestr 3\\Programowanie_Obiektowe\\Krzyzowka\\res\\cwdb.txt");
    }

    public void add(String word, String clue) {
        dict.add(new Entry(word, clue));
    }

    //metoda ktora zwraca has?o i jego podpowiedz dla okreslonego s?owa
    public Entry get(String word) throws WordNotFoundException {
        for (Entry e : dict) {
            if (e.getWord().equals(word)) return e;
        }
        throw new WordNotFoundException("Nie znaleziono takiego slowa w CwDB");
    }

    //metoda ktora usuwa z bazy haslo wraz z podpowiedzia dla okreslonego slowa
    public void remove(String word) throws WordNotFoundException {
        for (Entry e : dict) {
            if (e.getWord().equals(word)) {
                dict.remove(e);
            }
        }
        throw new WordNotFoundException("Nie znaleziono takiego slowa w CwDB");
    }

    //metoda zapisujaca baze danych do pliku okreslonego przez filename
    public void saveDB(String filename) throws IOException {

            File plik = new File(filename);
            if (!plik.exists()) plik.createNewFile();
            FileWriter zapis = new FileWriter(plik.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(zapis);
            for (Entry e : dict) {
                bw.write(e.getWord() + "\n");
                bw.write(e.getClue() + "\n");
            }

        }


    public int getSize() {
        return dict.size();
    }

    //metoda tworzaca baze danych z konkretnego pliku (wywolywana w konstruktorze)
    protected void createDB(String filename) throws FileNotFoundException {

            Scanner plik = new Scanner(new FileInputStream(filename));
            String tmpWord;
            String tmpClue;
            while (plik.hasNext()) {
                tmpWord = plik.nextLine();
                tmpClue = plik.nextLine();
                add(tmpWord, tmpClue);
            }
            plik.close();

        }
    }

