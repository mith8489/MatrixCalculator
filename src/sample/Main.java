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

        Fraction fractionA = new Fraction(15, 12);
        Fraction fractionB = new Fraction(25, 5);
        Fraction fractionC = Fraction.add(fractionB, fractionA);
        Fraction fractionD = Fraction.subtract(fractionB, fractionA);
        System.out.println(fractionC.toString());
        System.out.println(fractionD.toString());

     //   launch(args);
    }
}