package sample;

import crossword.board.Board;
import crossword.dictionary.CwEntry;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;

public class Controller {
    public final int TILE_SIZE = 35;

    public Node createCrossword(Board b, boolean empty) {
        Pane root = new Pane();
        root.setPrefSize(600,300);

        try {


            for (int i = 0; i < b.getWidth(); i++) {
                for (int j = 0; j < b.getHeight(); j++) {
                    if(!(b.getCell(i,j).getContent().equals(" "))) {
                        String cell;
                        if(empty) cell = " ";
                        else cell = b.getCell(i,j).getContent();
                        Tile tile = new Tile(cell);
                        tile.setTranslateX(i*TILE_SIZE);
                        tile.setTranslateY(j*TILE_SIZE);
                        root.getChildren().add(tile);
                    }

                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    private class Tile extends StackPane {
        public Tile(String value) {
            Rectangle border = new Rectangle(TILE_SIZE, TILE_SIZE);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            Text text = new Text(value);
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

    public Node createClues(LinkedList<CwEntry> entries) {
        Pane root = new Pane();
        root.setPrefSize(600,300);


        for (int i = 1; i < entries.size(); i++) {
            Text text = new Text(entries.get(i).getClue());
            text.setTranslateY(i*20);
            root.getChildren().add(text);
        }

        return root;
    }

    public static Alert generateAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(msg);
        alert.setHeaderText("Sprobuj jeszcze raz");
        alert.setContentText("Sprobuj jeszcze raz!");

        return alert;
    }
}
