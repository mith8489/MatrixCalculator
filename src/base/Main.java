package base;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main.java: Main class for Matrix Calculator.
 * Sets the size of the application windows and contains the graphical interface for performing matrix calculations.
 * @author Max Thurell
 * @version 1.0
 */
public class Main extends Application {

    private CalcGUI calcGUI;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 900;

    @Override
    public void start(Stage primaryStage) throws Exception{
        calcGUI = new CalcGUI();
        primaryStage.setTitle("Matrix Calculator");
        Scene scene = new Scene(calcGUI, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\base-styles.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\top-bottom-bar-styles.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\matrix-workspace-styles.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\transformation-styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}