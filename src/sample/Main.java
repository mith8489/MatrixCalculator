package sample;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

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

        Vector v1 = new Vector(-5.0, -12.0);
        Vector v2 = new Vector(2.0, 6.0);
        Vector v3 = new Vector(-17.0, 22.0);
        Vector v4 = new Vector(23.0, -7.0);
        Vector[] vectors = {v1,v2,v3,v4};

        System.out.println(v1.get2DAngle(v2));
//        VectorPolygon vectorPolygon = new VectorPolygon(vectors, new Vector(0.0,0.0));

        launch(args);
        System.out.println();
    }
}