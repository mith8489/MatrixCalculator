package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class VectorInputBox extends HBox {

    private TransformationWorkSpace workSpace;

    private HBox vectorValueBox;
    private String vectorNames = ".abcdefghijklmnopqrstuvwxyz";

    private LinkedList<TextField> xFields = new LinkedList<>();
    private LinkedList<TextField> yFields = new LinkedList<>();

    private int numOfPoints = 0;

    public LinkedList<TextField> getxFields() {
        return xFields;
    }

    public LinkedList<TextField> getyFields() {
        return yFields;
    }

    public int getNumOfPoints() {
        return numOfPoints;
    }


    public VectorInputBox(int numOfFields, TransformationWorkSpace workSpace)
    {
        this.workSpace = workSpace;
        makeControlBox();
        setInitFields(numOfFields);
    }

    private void setInitFields(int initFields)
    {
        for (int i = 1; i <= initFields; i++)
        {
            addCoordField();
        }
    }

    private void makeControlBox()
    {
        Button drawButton = new Button("Show Vectors");
        drawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                workSpace.getPointVectors();
                workSpace.drawVectors();

            }
        });
        drawButton.getStyleClass().add("vector-control-button");

        Button addFieldButton = new Button(" + ");
        addFieldButton.setOnAction(event -> addCoordField());
        addFieldButton.getStyleClass().add("vector-control-button");

        Button removeFieldButton = new Button(" - ");
        removeFieldButton.setOnAction(event -> removeCoordField());
        removeFieldButton.getStyleClass().add("vector-control-button");

        Label xLabel = new Label("X:");
        xLabel.getStyleClass().add("vector-field-label");
        Label yLabel = new Label("Y:");
        yLabel.getStyleClass().add("vector-field-label");
        VBox labelBox = new VBox();
        labelBox.getStyleClass().add("vector-label-box");
        labelBox.getChildren().addAll(xLabel, yLabel);

        vectorValueBox = new HBox();
        vectorValueBox.getChildren().addAll(drawButton, addFieldButton, removeFieldButton, labelBox);
        vectorValueBox.getStyleClass().add("vector-control-box");
        getChildren().add(vectorValueBox);
    }

    private void addCoordField()
    {
        numOfPoints++;

        TextField xField = new TextField();
        xField.getStyleClass().add("vector-text-field");
        HBox xCoordBox = new HBox();
        xCoordBox.getChildren().addAll( xField);
        xField.getStyleClass().add("dimension-control-field");
        xFields.add(xField);

        TextField yField = new TextField();
        yField.getStyleClass().add("vector-text-field");
        HBox yCoordBox = new HBox();
        yCoordBox.getChildren().addAll( yField);
        yField.getStyleClass().add("dimension-control-field");
        yFields.add(yField);

        Label pointNumber = new Label(Character.toString(vectorNames.charAt(numOfPoints)));
        pointNumber.getStyleClass().add("vector-number-label");
        VBox coordBox = new VBox();
        coordBox.getStyleClass().add("vector-coord-box");
        coordBox.getChildren().addAll(pointNumber, xCoordBox, yCoordBox);
        getChildren().add(coordBox);
    }

    private void removeCoordField()
    {
        if (numOfPoints > 0)
        {
            numOfPoints--;
            getChildren().remove(getChildren().size() - 1);
        }
    }
}
