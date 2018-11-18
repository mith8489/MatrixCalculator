package matrixops.workspaces;

import com.test.mthur.matrix.Fraction;
import com.test.mthur.matrix.FractionMatrix;
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
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * Base graphical interface for working with matrix operations.
 * Uses {@link VisualMatrix VisualMatrix} objects to receive data for use in operations. Data stored and operated on in {@link FractionMatrix} objects.
 * @author Max Thurell
 * @version 1.0
 */
public class MatrixWorkSpace extends VBox {

    protected CalcGUI calcGUI;

    protected HBox matrixBox;
    protected HBox controlBox;

    protected FractionMatrix matrixA; // First matrix
              FractionMatrix matrixB; // Second matrix (if necessary)
              FractionMatrix matrixC; //Solution matrix

    protected VisualMatrix vMatrixA; // First visual matrix
              VisualMatrix vMatrixB; // Second visual matrix (if necessary)
    protected VisualMatrix vMatrixC; //Solution visual matrix

    protected Text operatorSymbol;
    protected Text equalitySign;

    protected HBox dimensionControls;
    protected VBox matrixAControls;
              VBox matrixBControls;

              TextField matrixARowField;
              TextField matrixAColField;
    private   TextField matrixBRowField;
    private   TextField matrixBColField;

    protected Button controlButton;

              Text matrixErrorText;

    /**
     * Creates a new MatrixWorkSpace bound to a given CalcGUI.
     * Creates and displays all the components of the workspace.
     *
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public MatrixWorkSpace(CalcGUI calcGUI)
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

    /**
     * Creates the controls for changing the dimensions of the Visual Matrices (and thus the resultant Fraction Matrices).
     *
     */
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

    /**
     * Creates the control box for the dimensions of one matrix.
     *
     * @param matrixLetter The letter of the matrix controlled by the box.
     * @return VBox containing controls for the dimensions of one matrix.
     */
    private VBox makeDimensionControlBox(String matrixLetter)
    {
        Label matrixLabel = new Label("Matrix " + matrixLetter);
        matrixLabel.getStyleClass().add("dimension-control-matrix-label");

        GridPane matrixFields = new GridPane();

        Label matrixRowLabel = new Label("Rows:");
        matrixFields.add(matrixRowLabel, 0, 0);

        Label matrixColLabel = new Label("Columns:");
        matrixFields.add(matrixColLabel, 1, 0);

        TextField rowField;
        TextField colField;

        rowField = new TextField("3");
        rowField.getStyleClass().add("dimension-control-field");
        matrixFields.add(rowField, 0, 1);

        colField = new TextField("3");
        colField.getStyleClass().add("dimension-control-field");
        matrixFields.add(colField, 1, 1);

        if (matrixLetter.equals("A"))
        {
            matrixARowField = rowField;
            matrixAColField = colField;
        }
        else
        {
            matrixBRowField = rowField;
            matrixBColField = colField;
        }

        return new VBox(matrixLabel, matrixFields);
    }

    /**
     * Sets TextFields to clear their text when clicked.
     *
     * @param fields TextFields for which to set clearing behaviour.
     */
    private void setClickedBehaviour(TextField[] fields)
    {
        for (TextField field : fields)
        {
            field.setOnMouseClicked(event -> field.setText(""));
        }
    }

    /**
     * Updates the dimensions of both operand matrices (if present). Also checks compatibility for the current operation, and updates
     * the result matrix if compatible.
     */
    public void updateMatrixDimensions()
    {
        setNewDimensions();
        checkCompatibility();


        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }

    /**
     * Sets new dimensions for both Visual Matrices based on data from the control boxes.
     */
    private void setNewDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());
        int newBRows = Integer.parseInt(matrixBRowField.getText());
        int newBCols = Integer.parseInt(matrixBColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixB = new VisualMatrix(newBRows, newBCols, true);
    }

    /**
     * Checks if the two operand matrices (or one, if only one is present) are/is compatible for the operation
     * in the current workspace. If compatible, the result matrix is updated to correspond to the dimensions of the
     * operand matrix/matrices.
     */
    protected void checkCompatibility()
    {
        if (vMatrixA.getM() == vMatrixB.getM() && vMatrixA.getN() == vMatrixB.getN())
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) {npe.printStackTrace();}

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(vMatrixA.getM(), vMatrixA.getN(), false);
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) {iae.printStackTrace();}
        }
    }

    /**
     * Creates a FractionMatrix using data taken from the TextFields in a VisualMatrix.
     * Matrices must have the same dimensions.
     *
     * @param vMatrix VisualMatrix from which to retrieve data.
     * @return FractionMatrix created from data in vMatrix.
     */
    protected FractionMatrix createMatrix(VisualMatrix vMatrix)
    {
        FractionMatrix matrix = new FractionMatrix(vMatrix.getM(), vMatrix.getN());
        for (int i = 0; i < matrix.getM(); i++)
        { for (int j = 0; j < matrix.getN(); j++)
        {
            TextField textField = (TextField) getNodeByIndex(i, j, vMatrix);
            String text = textField.getText();
            if (text.equals("")) text = "0";

            matrix.setElement(i, j, Fraction.fromString(text));
        }
        }
        return matrix;
    }

    /**
     * Fills a VisualMatrix with fraction values based on data from a FractionMatrix. Matrices must have the same dimensions.
     *
     * @param fractionMatrix FractionMatrix from which to retrieve data.
     * @param vMatrix VisualMatrix to fill with values.
     * @return VisualMatrix containing fraction values from matrix.
     */
    VisualMatrix createVisualMatrix(FractionMatrix fractionMatrix, VisualMatrix vMatrix)
    {
        for (int i = 0; i < fractionMatrix.getM(); i++)
        { for (int j = 0; j < fractionMatrix.getN(); j++)
        {
            Node textField = getNodeByIndex(i, j, vMatrix);
            String text = (fractionMatrix.getElement(i, j).toString());
            if (text.equals("-0")) text = "0";
            ((TextField) textField).setText(text);
        }
        }

        return vMatrix;
    }

    /**
     * Gets a JavaFX Node by its index in a GridPane. Used to retrieve values from specific fields in Visual Matrices.
     *
     * @param row Row index of Node.
     * @param column Column index of Node.
     * @param gridPane GridPane from which to retrieve Node.
     * @return Node found at (row, column) in gridPane.
     */
    Node getNodeByIndex (int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    /**
     * Clears the fields of all Visual Matrices.
     */
    public void clearMatrices()
    {
        VisualMatrix[] vMatrices = {vMatrixA, vMatrixB, vMatrixC};
        for (VisualMatrix vMatrix : vMatrices)
        {
            clearMatrix(vMatrix);
        }
    }

    /**
     * Clears all fields of a VisualMatrix.
     *
     * @param vMatrix VisualMatrix to clear.
     */
    void clearMatrix(VisualMatrix vMatrix)
    {
        for (Node textField : vMatrix.getChildren())
        {
            ((TextField) textField).setText("");
        }
    }

    /**
     * Operation method inherited by specific operation workspaces.
     * Performs the matrix operation belonging to each specific workspace.
     */
    public void doOperation() {}
}
