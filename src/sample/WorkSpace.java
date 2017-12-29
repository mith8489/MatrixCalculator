package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WorkSpace extends VBox implements MatrixOperational {

    protected CalcGUI calcGUI;

    protected HBox matrixBox;
    protected HBox controlBox;

    protected Matrix matrixA; // First matrix
    protected Matrix matrixB; // Second matrix (if necessary)
    protected Matrix matrixC; //Solution matrix

    protected VisualMatrix vMatrixA; // First visual matrix
    protected VisualMatrix vMatrixB; // Second visual matrix (if necessary)
    protected VisualMatrix vMatrixC; //Solution visual matrix

    protected Text operatorSymbol;
    protected Text equalitySign;

    protected HBox dimensionControls;
    protected VBox matrixAControls;
    protected VBox matrixBControls;

    protected TextField matrixARowField;
    protected TextField matrixAColField;
    protected TextField matrixBRowField;
    protected TextField matrixBColField;

    protected Button controlButton;

    protected Text matrixErrorText;

    public WorkSpace(CalcGUI calcGUI)
    {
        this.calcGUI = calcGUI;
        matrixBox = new HBox();
        controlBox = new HBox();
        getChildren().addAll(controlBox, matrixBox);

        getStyleClass().add("work-space");
        vMatrixA = new VisualMatrix(3, 3);
        vMatrixB = new VisualMatrix(3, 3);
        vMatrixC = new VisualMatrix(3, 3);

        makeDimensionControls();

        operatorSymbol = new Text();
        operatorSymbol.getStyleClass().add("operator-symbol");
        equalitySign = new Text("=");
        equalitySign.getStyleClass().add("operator-symbol");
        matrixErrorText = new Text("INCOMPATIBLE \nMATRICES");
        matrixErrorText.getStyleClass().add("matrix-error-text");
    }

    private void makeDimensionControls()
    {
        matrixAControls = makeDimensionControlBox("A");
        matrixBControls = makeDimensionControlBox("B");

        controlButton = new Button("Save");
        controlButton.getStyleClass().add("control-button");
        controlButton.setOnAction(event -> updateMatrixDimensions());
        dimensionControls = new HBox();
        dimensionControls.getStyleClass().add("dimension-controls");
    }

    private VBox makeDimensionControlBox(String matrixLetter)
    {
        Label matrixLabel = new Label("Matrix " + matrixLetter);
        matrixLabel.getStyleClass().add("dimension-control-matrix-label");

        GridPane matrixFields = new GridPane();

        Label matrixRowLabel = new Label("Rows:");
        matrixFields.add(matrixRowLabel, 0, 0);

        Label matrixColLabel = new Label("Columns:");
        matrixFields.add(matrixColLabel, 1, 0);

        if (matrixLetter == "A")
        {
            matrixARowField = new TextField("3");
            matrixARowField.getStyleClass().add("dimension-control-field");
            matrixFields.add(matrixARowField, 0, 1);

            matrixAColField = new TextField("3");
            matrixAColField.getStyleClass().add("dimension-control-field");
            matrixFields.add(matrixAColField, 1, 1);
        }
        else
        {
            matrixBRowField = new TextField("3");
            matrixBRowField.getStyleClass().add("dimension-control-field");
            matrixFields.add(matrixBRowField, 0, 1);

            matrixBColField = new TextField("3");
            matrixBColField.getStyleClass().add("dimension-control-field");
            matrixFields.add(matrixBColField, 1, 1);
        }


        VBox matrixControls = new VBox(matrixLabel, matrixFields);
        return matrixControls;
    }

    public void updateMatrixDimensions()
    {

    }

    protected Matrix createMatrix(Matrix matrix, VisualMatrix vMatrix)
    {
        matrix = new Matrix(vMatrix.getM(), vMatrix.getN());
        for (int i = 0; i < matrix.getM(); i++)
        { for (int j = 0; j < matrix.getN(); j++)
        {
            TextField textField = (TextField) getNodeByIndex(i, j, vMatrix);
            matrix.setElement(i, j, Double.parseDouble(textField.getText()));
        }
        }
        return matrix;
    }

    protected VisualMatrix createVisualMatrix(Matrix matrix, VisualMatrix vMatrix)
    {
        for (int i = 0; i < matrix.getM(); i++)
        { for (int j = 0; j < matrix.getN(); j++)
        {
            Node textField = getNodeByIndex(i, j, vMatrix);
            ((TextField) textField).setText(Double.toString(matrix.getElement(i, j)));
        }
        }

        return vMatrix;
    }

    protected Node getNodeByIndex (int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void clearMatrices()
    {
        VisualMatrix[] vMatrices = {vMatrixA, vMatrixB, vMatrixC};
        for (VisualMatrix vMatrix : vMatrices)
        {
            for (Node textField : vMatrix.getChildren())
            {
                ((TextField) textField).setText("0");
            }
        }
    }

    public void doOperation()
    {

    }
}
