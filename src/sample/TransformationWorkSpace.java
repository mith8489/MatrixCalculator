package sample;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class TransformationWorkSpace extends BorderPane {

    private Vector[] points;

    private int numOfPoints = 0;

    private HBox coordFieldBox;

    private LinkedList<TextField> xFields = new LinkedList<>();
    private LinkedList<TextField> yFields = new LinkedList<>();

    public TransformationWorkSpace(int initFields)
    {
        coordFieldBox = new HBox();
        coordFieldBox.getStyleClass().add("dimension-controls");
        setTop(coordFieldBox);
        setInitFields(initFields);
    }

    private void setInitFields(int initFields)
    {
        for (int i = 1; i <= initFields; i++)
        {
            addCoordField();
        }
    }

    private void addCoordField()
    {
        numOfPoints++;

        Label xLabel = new Label("X:");
        xLabel.getStyleClass().add("vector-field-label");
        TextField xField = new TextField();
        HBox xCoordBox = new HBox();
        xCoordBox.getChildren().addAll(xLabel, xField);
        xField.getStyleClass().add("dimension-control-field");
        xFields.add(xField);

        Label yLabel = new Label("Y:");
        yLabel.getStyleClass().add("vector-field-label");
        TextField yField = new TextField();
        HBox yCoordBox = new HBox();
        yCoordBox.getChildren().addAll(yLabel, yField);
        yField.getStyleClass().add("dimension-control-field");
        yFields.add(yField);


        Label pointNumber = new Label(Integer.toString(numOfPoints));
        pointNumber.getStyleClass().add("vector-number-label");
        VBox coordBox = new VBox();
        coordBox.setStyle("-fx-alignment: center");
        coordBox.getChildren().addAll(pointNumber, xCoordBox, yCoordBox);
        coordFieldBox.getChildren().add(coordBox);
    }

    private void getPointVectors()
    {
        points = new Vector[xFields.size()];

        for (int i = 0; i < xFields.size(); i++)
        {
            Double xVal = Double.parseDouble(xFields.get(i).getText());
            Double yVal = Double.parseDouble(yFields.get(i).getText());
            points[i] = new Vector(xVal, yVal);
        }
    }
}
