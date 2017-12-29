package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        launch(args);
    }
}
