package sample;

import crossword.Files.CwBrowser;
import crossword.board.Board;
import crossword.board.Crossword;
import crossword.board.SimpleStrategy;
import crossword.board.Strategy;
import crossword.dictionary.CwEntry;
import crossword.dictionary.Entry;
import crossword.exceptions.TooBigBoardException;
import crossword.exceptions.WordNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class Main extends Application {


    private Node createCrossword(Board b,boolean empty) {
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
                        tile.setTranslateX(i*35);
                        tile.setTranslateY(j*35);
                        root.getChildren().add(tile);
                    }

                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }


    private Node createClues(LinkedList<CwEntry> entries) {
        Pane root = new Pane();
        root.setPrefSize(600,300);


        for (int i = 1; i < entries.size(); i++) {
            System.out.println(i + " " + entries.get(i).getClue());
            Text text = new Text(entries.get(i).getClue());
            text.setTranslateY(i*20);
            root.getChildren().add(text);
        }

        return root;
    }






    @Override
    public void start(Stage primaryStage) throws Exception {

//        Crossword cw = new Crossword("res/cwdb.txt", 4);
//        Strategy s = new SimpleStrategy();
//        Board b = new Board();
//        cw.setBoard(b);
//        cw.generate(s);
        final FileChooser fileChooser = new FileChooser();
        CwBrowser cwBrowser = new CwBrowser();

        Pane root = new Pane();
        Pane cwPane = new Pane();
        root.setPrefSize(1200,600);
        root.getChildren().add(cwPane);


        HBox hbox = new HBox(8); // spacing = 8

        final Spinner<Integer> spinner_height = new Spinner<Integer>();
        final Spinner<Integer> spinner_width = new Spinner<Integer>();
        SpinnerValueFactory<Integer> valueFactory_height =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 10, 7);

        SpinnerValueFactory<Integer> valueFactory_width =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 10, 7);
        spinner_height.setPrefWidth(50);
        spinner_width.setPrefWidth(50);
        spinner_height.setValueFactory(valueFactory_height);
        spinner_width.setValueFactory(valueFactory_width);

        Button generate = new Button("Generuj");

        generate.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {
                try {
                    cwPane.getChildren().clear();
                    Crossword cw = new Crossword("res/cwdb.txt", 4);
                    Strategy s = new SimpleStrategy();
                    Board b = new Board(spinner_width.getValue(),spinner_height.getValue());
                    cw.setBoard(b);
                    cw.generate(s);

                Node n = createCrossword(b,true);
                Node nn = createCrossword(b, false);
                MyClass.setNode(nn);
                MyClass.setCw(cw);
                Node n2 = createClues(cw.getEntries());
                n.setTranslateX(25);
                n.setTranslateY(225);
                n2.setTranslateX(625);
                n2.setTranslateY(225);
                cwPane.getChildren().add(n);
                cwPane.getChildren().add(n2);
                } catch (TooBigBoardException e) {
                    e.printStackTrace();
                } catch (WordNotFoundException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });


        hbox.getChildren().addAll(new Label("Wysokosc"), spinner_height,new Label("Szerokosc"), spinner_width, generate);

        root.getChildren().add(hbox);


        Button print = new Button("Drukuj");
        Button save = new Button("Zapisz");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cwBrowser.saveCrossword(MyClass.getCw());
            }
        });
        Button read = new Button("Wczytaj");
        read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file !=null) try {
                    cwBrowser.readCrossword(file.getAbsolutePath());
                    Crossword cw = cwBrowser.getLastCrossword();

                    cwPane.getChildren().clear();

                    Board b = cw.getBoardCopy();

                    Node n = createCrossword(b,true);
                    Node nn = createCrossword(b, false);
                    MyClass.setNode(nn);
                    MyClass.setCw(cw);
                    System.out.println(cw.getEntries().size());
                    Node n2 = createClues(cw.getEntries());
                    n.setTranslateX(25);
                    n.setTranslateY(225);
                    n2.setTranslateX(625);
                    n2.setTranslateY(225);
                    cwPane.getChildren().add(n);
                    cwPane.getChildren().add(n2);
                } catch (TooBigBoardException e) {
                    e.printStackTrace();
                }  catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();


            }

            }
        });
        Button solve = new Button("Rozwiaz");

        solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node node = MyClass.getNode();
                node.setTranslateX(25);
                node.setTranslateY(225);
                if(!cwPane.getChildren().contains(node)) cwPane.getChildren().add(node);
            }
        });
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(print,save,read,solve);

        buttonBar.setTranslateX(300);
        root.getChildren().add(buttonBar);


        primaryStage.setTitle("Krzyzowka");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }


    private class Tile extends StackPane {
        public Tile(String value) {
            Rectangle border = new Rectangle(35, 35);
            border.setFill(null);
            border.setStroke(Color.BLACK);

            Text text = new Text(value);
            text.setFont(Font.font(20));

            setAlignment(Pos.CENTER);
            getChildren().addAll(border, text);
        }
    }

    private static class MyClass{
        public static Node n2;
        public static Crossword cw;
        public static void setNode(Node n){n2 = n;}
        public static Node getNode(){return n2;}

        public static Crossword getCw() {
            return cw;
        }

        public static void setCw(Crossword cw) {
            MyClass.cw = cw;
        }
    }
}


