package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

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

       /* double[][] matrixData = {{2, 7, 0}, {3, 2, 0}, {12, 4, 7}, {0, 0, 0}};
        Matrix matrix = new Matrix(matrixData);
        System.out.println(matrix.getRank());

        double[][] matrixData2 = {{5, 5, 5}, {6, 6, 7}, {8, 8, 8}};
        Matrix matrix2 = new Matrix(matrixData2);
        matrix.swap(matrix2);*/


        launch(args);
    }
}
