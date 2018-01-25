package matrixops.workspaces;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * InversionWorkSpace.java: Graphical interface for performing matrix inversion.
 * @author Max Thurell
 * @version 1.0
 */
public class InversionWorkSpace extends MatrixWorkSpace {

    private Text wrongRankText;

    /**
     * Creates a new InversionWorkSpace bound to a given CalcGUI.
     * Contains a double-size combined result matrix and identity matrix.
     *
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public InversionWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixC = new VisualMatrix(3, 6, false);
        augmentMatrix();
        showMatrices();
    }

    /**
     * Edits borders of TextFields to indicate an augmented matrix.
     */
    private void augmentMatrix()
    {
            for (int i = 0; i < vMatrixC.getM(); i++)
            {
                TextField textField = (TextField) getNodeByIndex(i, vMatrixC.getN() / 2, vMatrixC);
                textField.setStyle("-fx-border-width: 1px 1px 1px 3px; -fx-border-color: #AAA #AAA #AAA black");
            }
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("-1");
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    /**
     * Performs a matrix inversion, if the matrix is invertible.
     * Otherwise, displays error message.
     */
    @Override
    public void doOperation() {
        matrixA = createMatrix(vMatrixA);
        clearMatrix(vMatrixC);
        try {matrixBox.getChildren().remove(wrongRankText);}
        catch (NullPointerException npe) { npe.printStackTrace(); }

        if (matrixA.getRank() == matrixA.getM())
        {
            matrixC = matrixA.invert();
            vMatrixC = createVisualMatrix(matrixC, vMatrixC);
        }
        else
        {
            wrongRankText = new Text("Matrix rank < " + matrixA.getM() + ".\nMatrix not invertible.");
            wrongRankText.getStyleClass().add("matrix-error-text");
            matrixBox.getChildren().add(wrongRankText);
        }


    }

    /**
     * Updates the dimensions of the operand matrix, and checks for invertibility.
     */
    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);

        try {matrixBox.getChildren().remove(wrongRankText);}
        catch (NullPointerException npe) { npe.printStackTrace(); }

        checkCompatibility(newARows, newACols);

        augmentMatrix();

        matrixBox.getChildren().clear();
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    /**
     * Checks if the operand matrix is invertible (square and rank = row size).
     * If invertible, updates dimensions of the result matrix and makes the Solve button clickable.
     * Otherwise makes the Solve button un-clickable and displays an error message.
     * @param newARows Matrix rows. Must be equal to newACols to be invertible.
     * @param newACols Matrix columns. Must be equal to newARows to be invertible.
     */
    private void checkCompatibility(int newARows, int newACols)
    {
        matrixErrorText.setText("Matrix not invertible!");
        if (newARows == newACols)
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) { npe.printStackTrace(); }

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(newARows, newACols * 2, false);
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) { iae.printStackTrace(); }
        }
    }
}
