package matrixops.workspaces;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import base.CalcGUI;
import matrixops.VisualMatrix;
import matrixops.maths.Fraction;

/**
 * DeterminantWorkSpace.java: Graphical interface for finding matrix determinants.
 * @author Max Thurell
 * @version 1.0
 */
public class DeterminantWorkSpace extends MatrixWorkSpace {

    /**
     * Creates a new DeterminantWorkSpace bound to a given CalcGUI.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public DeterminantWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixC = new VisualMatrix(1, 1, false);

        showMatrices();
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("det");
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    /**
     * Finds the determinant of the operand matrix.
     */
    @Override
    public void doOperation() {
        matrixA = createMatrix(vMatrixA);

        Fraction determinant = matrixA.getDeterminant();

        TextField textField = (TextField) vMatrixC.getChildren().get(0);
        textField.setText(determinant.toString());
    }

    /**
     * Updates the dimension of the operand matrix, and checks if the matrix is square.
     */
    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);

        checkCompatibility(newARows, newACols);


        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    /**
     * Checks if matrix is square. If square, updates result matrix dimensions to match operand matrix, and makes the
     * Solve button clickable.
     * If not square, makes Solve button un-clickable and displays error message.
     * @param newARows Matrix rows. Must be equal to newACols.
     * @param newACols Matrix columns. Must be equal to newARows.
     */
    private void checkCompatibility(int newARows, int newACols)
    {
        matrixErrorText.setText("DecimalMatrix not square!");
        if (newARows == newACols)
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) { npe.printStackTrace(); }

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(1, 1, false);
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) {iae.printStackTrace(); }
        }
    }
}
