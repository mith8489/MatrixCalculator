package base;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for Matrix Calculator.
 *
 * The Matrix Calculator allows the user to perform all of the common matrix operations.
 * This includes, among other thing, arithmetic operations, scalar/matrix multiplication,
 * transposition, inversion and Gauss-Jordan elimination. It also provides functionality for plotting
 * two-dimensional vectors on a Cartesian plane and transforming them with transformation matrices.
 * @author Max Thurell
 * @version 1.0
 */
public class Main extends Application {

    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 900;

    @Override
    public void start(Stage primaryStage) {
        CalcGUI calcGUI = new CalcGUI();
        primaryStage.setTitle("Matrix Calculator");
        Scene scene = new Scene(calcGUI, WINDOW_WIDTH, WINDOW_HEIGHT);
        addStyleSheets(scene);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }

    private void addStyleSheets(Scene scene) {
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\base-styles.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\top-bottom-bar-styles.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\matrix-workspace-styles.css").toExternalForm());
        scene.getStylesheets().add(Main.class.getResource("..\\stylesheets\\transformation-styles.css").toExternalForm());
    }
}