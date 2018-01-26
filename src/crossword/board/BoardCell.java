package crossword.board;

import java.io.Serializable;

//klasa b?d?ca pojedy?cz? kom?rk? w krzy??wce
public class BoardCell implements Serializable{
    static final long serialVersionUID = -7588980448693010399L;
    //zawiera informacje o zawartosci, oraz mozliwosci rozszerzenia krzyzowki w danym kierunku
    String content;
    boolean canHorizStart,canHorizIn,canHorizEnd;
    boolean canVertiStart,canVertiIn,canVertiEnd;

    //konstruktor bezparametryczny dla pustej kom?rki
    public BoardCell() {
        content=" ";
        canHorizEnd=canHorizIn=canHorizStart=canVertiEnd=canVertiIn=canVertiStart=true;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }


//metoda umo?liwiaj?c? ustalenie czy kom?rka mo?e by? pocz?tkiem/ko?cem/?rodkiem dla nowego has?a poziomo, lub pionowo.
    public void checkPossibility(int x,int y,Board b){
        //False - komorka pusta
        //True - komorka zawiera w sobie litere


        //TODO OCB????
        //tablica pomocnicza dookola danej komorki
        boolean[][] tablicaPomocnicza = new boolean[3][3];
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++)
                if (b.getCell(x-1+j, y-1+i).content.isEmpty()) tablicaPomocnicza[i][j]=false;
                else tablicaPomocnicza[i][j]=true;
        }

        //ustalamy tylko stany false, bo wszystkie sa przy tworzeniu obiektu ustawiane na true

        if (x==0) {
            //komorka w lewym dolnym rogu
            if (y==0) {
                canHorizEnd=canVertiEnd=canHorizIn=canVertiIn=false;
            }
            //komorka w lewym gornym rogu
            if (y==b.getHeight()) {
                canHorizEnd=canVertiStart=canVertiIn=false;
            }
            canHorizEnd=false;
        }
        if (this.content.isEmpty()) {
            if (tablicaPomocnicza[0][0]) {
                if (tablicaPomocnicza[1][0]) canVertiEnd=false;
            }
            if (tablicaPomocnicza[0][1]) {
                canVertiEnd=false;
                canVertiStart=false;
                canHorizStart=false;
                canHorizEnd=false;
            }
            if (tablicaPomocnicza[0][2]) {
                canHorizStart=false;
            }
            if (tablicaPomocnicza[1][0]) {
                canVertiEnd=false;
                canVertiStart=false;
                canHorizStart=false;
                canHorizEnd=false;
            }
            if (tablicaPomocnicza[1][2]) {
                canVertiEnd=false;
                canVertiStart=false;
                canHorizStart=false;
                canHorizEnd=false;
            }
            if (tablicaPomocnicza[2][0]) {
                canVertiStart=false;
            }
            if (tablicaPomocnicza[2][1]) {
                canVertiEnd=false;
                canVertiStart=false;
                canHorizStart=false;
                canHorizEnd=false;
            }
            if (tablicaPomocnicza[2][2]) {
                canVertiStart=false;
            }
        }
    }
}
