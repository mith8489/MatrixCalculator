package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

import maths.Fraction;

public class Main extends Application {

    private CalcGUI calcGUI;
    private final int WINDOW_WIDTH = 1400;
    private final int WINDOW_HEIGHT = 1200;

    @Override
    public void start(Stage primaryStage) throws Exception{
        calcGUI = new CalcGUI();
        primaryStage.setTitle("Matrix Calculator");
        Scene scene = new Scene(calcGUI, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(Main.class.getResource("matrix-calc-style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        Fraction fractionA = new Fraction(4, 5);
        Fraction fractionB = new Fraction(2, 6);
        Fraction fractionC = new Fraction(-9, 2);

        System.out.println(fractionA.divide(fractionB).toString());
        launch(args);
    }
}