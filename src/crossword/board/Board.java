package crossword.board;

import crossword.exceptions.TooBigBoardException;

import java.io.Serializable;
import java.util.LinkedList;

//klasa tworzaca plansze do gry
public class Board implements Serializable{
    static final long serialVersionUID = -7588980448693010399L;
    private BoardCell[][] board;
    private int width;
    private int height;

    //defaultowo plansza 10x10
    public Board(){
        this.board = new BoardCell[10][10];
        this.width = 10;
        this.height = 10;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                board[i][j] = new BoardCell();
            }
        }
    }

    //plansza o wybranym rozmiarze
    public Board(int width, int height) throws TooBigBoardException {
        if(height > 12 ) throw new TooBigBoardException("Zbyt wysoka plansza");
        if(width > 30) throw new TooBigBoardException("Zbyt szeroka plansza");
        this.width = width;
        this.height = height;
        this.board = new BoardCell[this.width][this.height];

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                board[i][j] = new BoardCell();
            }
        }
    }

    public int getWidth(){
        return this.width;
    }
    public int getHeight(){
        return this.height;
    }
    public BoardCell getCell(int x, int y){
        return board[x][y];
    }

    public void setCell(int x,int y, BoardCell c){
        board[x][y] = c;
    }


    //zwraca liste wszystkich pol, na ktorych mozna rozpoczac dane slowo krzyzowki
    public LinkedList<BoardCell> getStartCells(){
        LinkedList<BoardCell> exitlist = new LinkedList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(board[i][j].canHorizStart || board[i][j].canVertiStart) exitlist.add(board[i][j]);
            }
        }
        return exitlist;
    }

    //metoda ktora tworzy regex, gdzie na pierwszym miejscu jest dana litera ze slowa, a reszta to jest odpowiednia ilosc liter dostepnych na planszy
    String createPattern(int fromx, int fromy, int tox, int toy){
        String pattern="^";
        int ilosc = tox - fromx;
        String first = board[0][fromy].getContent();
        first = first.toLowerCase();
        pattern +=first + "[A-Za-z]{2,";
        pattern+=String.valueOf(ilosc) + "}";
        pattern+="$";
        return pattern;
    }
}
