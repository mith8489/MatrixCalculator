package sample;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.text.DecimalFormat;

public class WorkSpace extends VBox implements MatrixOperational {

    protected DecimalFormat decimalFormat = new DecimalFormat("#.##");

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
        vMatrixA = new VisualMatrix(3, 3, true);
        vMatrixB = new VisualMatrix(3, 3, true);
        vMatrixC = new VisualMatrix(3, 3, false);

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

        TextField[] controlFields = {matrixARowField, matrixAColField, matrixBRowField, matrixBColField};
        setClickedBehaviour(controlFields);

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

    private void setClickedBehaviour(TextField[] fields)
    {
        for (TextField field : fields)
        {
            field.setOnMouseClicked(event -> field.setText(""));
        }
    }

    public void updateMatrixDimensions()
    {
        setNewDimensions();
        checkCompatibility();


        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }

    protected void setNewDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());
        int newBRows = Integer.parseInt(matrixBRowField.getText());
        int newBCols = Integer.parseInt(matrixBColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixB = new VisualMatrix(newBRows, newBCols, true);
    }

    protected void checkCompatibility()
    {
        if (vMatrixA.getM() == vMatrixB.getM() && vMatrixA.getN() == vMatrixB.getN())
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) {}

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(vMatrixA.getM(), vMatrixA.getN(), false);
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) {}
        }
    }

    protected Matrix createMatrix(Matrix matrix, VisualMatrix vMatrix)
    {
        matrix = new Matrix(vMatrix.getM(), vMatrix.getN());
        for (int i = 0; i < matrix.getM(); i++)
        { for (int j = 0; j < matrix.getN(); j++)
        {
            TextField textField = (TextField) getNodeByIndex(i, j, vMatrix);
            String text = textField.getText();
            if (text.equals("")) text = "0";
            matrix.setElement(i, j, Double.parseDouble(text));
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
            String text = decimalFormat.format(matrix.getElement(i, j));
            if (text.equals("-0")) text = "0";
            ((TextField) textField).setText(text);
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
            clearMatrix(vMatrix);
        }
    }

    protected void clearMatrix(VisualMatrix vMatrix)
    {
        for (Node textField : vMatrix.getChildren())
        {
            ((TextField) textField).setText("");
        }
    }

    public void doOperation()
    {

    }
}
