package crossword.Files;

import crossword.board.Crossword;

import java.io.*;
import java.util.Date;

public class Writer  implements IWriter, Serializable{
    static final long serialVersionUID = -7588980448693010399L;
    @Override
    public void write(Crossword cw) {
try{
    File dir = new File("savedCw");
    if(!dir.exists()) dir.mkdir();
    FileOutputStream fileOut = new FileOutputStream(String.valueOf("savedCw\\" + this.getUniqueID()) + ".txt");
    ObjectOutputStream out = new ObjectOutputStream(fileOut);
    out.writeObject(cw);
    out.close();
    fileOut.close();
}catch (IOException e) {
    e.printStackTrace();
}
    }

    @Override
    public long getUniqueID() {
        return new Date().getTime();
    }
}
