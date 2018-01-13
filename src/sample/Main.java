package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private CalcGUI calcGUI;
    private final int WINDOW_WIDTH = 1200;
    private final int WINDOW_HEIGHT = 950;

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

        double[][] matrixData = {{1, 2}, {3, 4}};
        Matrix matrix = new Matrix(3, 3);
        matrix.addData(matrixData);

        Vector vector1 = new Vector(1.0, 2.0, 5.0);
        Vector vector2 = new Vector(3.0, 4.0, 6.0);
        Vector vector3 = new Vector(-1.0, -2.0, -3.0);
        matrix = Matrix.buildFromVectors(vector1, vector2, vector3);
        matrix.show();
        Vector vector = matrix.extractVector(1);
        vector.show();

        //launch(args);
    }
}
