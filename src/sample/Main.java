package sample;

import crossword.Files.CwBrowser;
import crossword.board.*;
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
import java.util.Optional;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {

        Controller controller = new Controller();
        final FileChooser fileChooser = new FileChooser();
        CwBrowser cwBrowser = new CwBrowser();


        Pane root = new Pane();
        root.setPrefSize(1200, 600);

        Pane cwPane = new Pane();
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

        Button generate1 = new Button("Generuj EASY");
        Button generate2 = new Button("Generuj MEDIUM");
        Button generate3 = new Button("Generuj HARD");

        hbox.getChildren().addAll(new Label("Wysokosc"), spinner_height, new Label("Szerokosc"), spinner_width, generate1, generate2, generate3);

        root.getChildren().add(hbox);


        Button print = new Button("Drukuj");
        Button save = new Button("Zapisz");
        Button read = new Button("Wczytaj");
        Button solve = new Button("Rozwiaz");

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(print, save, read, solve);

        buttonBar.setTranslateX(600);
        root.getChildren().add(buttonBar);


        generate1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Crossword cw = new Crossword("res/cwdb.txt", 4);
                    Strategy s = new SimpleStrategy();
                    Board b = new Board(spinner_width.getValue(), spinner_height.getValue());
                    cw.setBoard(b);
                    cw.generate(s);
                    generateCw(cw, cwPane, controller);
                } catch (FileNotFoundException e) {
                    Alert alert = controller.generateAlert("Blad pliku");
                    alert.showAndWait();
                } catch (WordNotFoundException e) {
                    Alert alert = controller.generateAlert("Blad slowa");
                    alert.showAndWait();
                } catch (TooBigBoardException e) {
                    Alert alert = controller.generateAlert("Blad krzyzowki");
                    alert.showAndWait();
                }

            }
        });

        generate2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                try {
                    Crossword cw = new Crossword("res/cwdb.txt", 4);
                    Strategy s = new MediumStrategy();
                    Board b = new Board(spinner_width.getValue(), spinner_height.getValue());
                    cw.setBoard(b);
                    cw.generate(s);
                    generateCw(cw, cwPane, controller);
                } catch (FileNotFoundException e) {
                    Alert alert = controller.generateAlert("Blad pliku");
                    alert.showAndWait();
                } catch (WordNotFoundException e) {
                    Alert alert = controller.generateAlert("Blad slowa");
                    alert.showAndWait();
                } catch (TooBigBoardException e) {
                    Alert alert = controller.generateAlert("Blad krzyzowki");
                    alert.showAndWait();
                }
            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cwBrowser.saveCrossword(MyClass.getCw());
            }
        });

        read.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) try {
                    cwBrowser.readCrossword(file.getAbsolutePath());
                    Crossword cw = cwBrowser.getLastCrossword();

                    generateCw(cw, cwPane, controller);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Node node = MyClass.getNode();
                node.setTranslateX(25);
                node.setTranslateY(225);
                if (!cwPane.getChildren().contains(node)) cwPane.getChildren().add(node);
            }
        });


        primaryStage.setTitle("Krzyzowka");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    private static class MyClass {
        public static Node n2;
        public static Crossword cw;

        public static void setNode(Node n) {
            n2 = n;
        }

        public static Node getNode() {
            return n2;
        }

        public static Crossword getCw() {
            return cw;
        }

        public static void setCw(Crossword cw) {
            MyClass.cw = cw;
        }
    }

    public void generateCw(Crossword crossword, Pane cwPane, Controller controller) {
        try {
            cwPane.getChildren().clear();
            Board b = crossword.getBoardCopy();

            Node n = controller.createCrossword(b, true);
            Node nn = controller.createCrossword(b, false);
            MyClass.setNode(nn);
            MyClass.setCw(crossword);
            Node n2 = controller.createClues(crossword.getEntries());
            n.setTranslateX(25);
            n.setTranslateY(225);
            n2.setTranslateX(625);
            n2.setTranslateY(225);
            cwPane.getChildren().add(n);
            cwPane.getChildren().add(n2);
        } catch (TooBigBoardException e) {
            Alert alert = controller.generateAlert("Blad krzyzowki");
            alert.showAndWait();
        }
    }
}




