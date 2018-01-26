package crossword.Files;

import crossword.board.Crossword;

import java.io.*;
import java.util.LinkedList;

public class Reader implements IReader {
    static final long serialVersionUID = -7588980448693010399L;
    private String path;
    public Reader(String path){
        this.path = path;
    }

    @Override
    public LinkedList<Crossword> getAllCws() throws IOException, ClassNotFoundException {
        LinkedList<Crossword> list = new LinkedList<>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        for(File file : files ){
            if(file.isFile())
            {
                FileInputStream fileIn = new FileInputStream(file);
                list.add(readSerializedFile(fileIn));
                fileIn.close();
            }
        }
        return list;
    }

private Crossword readSerializedFile(FileInputStream f) throws IOException, ClassNotFoundException {
    ObjectInputStream in = new ObjectInputStream(f);
    Crossword cw = (Crossword) in.readObject();
    in.close();
    return cw;
}

public Crossword getCrossword() throws IOException, ClassNotFoundException {
        Crossword cw = null;
        FileInputStream fileIn = new FileInputStream(path);
        cw = readSerializedFile(fileIn);
        fileIn.close();
        return cw;
}
}
