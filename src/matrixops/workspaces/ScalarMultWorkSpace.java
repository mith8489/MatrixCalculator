package matrixops.workspaces;

import com.test.mthur.matrix.Fraction;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * ScalarMultWorkSpace.java: Graphical interface for performing scalar multiplication on a matrix.
 * @author Max Thurell
 * @version 1.0
 */
public class ScalarMultWorkSpace extends MatrixWorkSpace {


    /**
     * Creates a new ScalarMultWorkSpace bound to a given CalcGUI.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public ScalarMultWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        vMatrixB = new VisualMatrix(1, 1, true);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("×");
        matrixBox.getChildren().addAll(new Group(vMatrixB), operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    /**
     * Performs scalar multiplication on the operand matrix.
     */
    @Override
    public void doOperation() {
        matrixA = createMatrix(vMatrixA);

        TextField textField = (TextField) vMatrixB.getChildren().get(0);
        Fraction scalar = Fraction.fromString(textField.getText());

        matrixC = matrixA.scalarMult(scalar);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

    /**
     * Updates the dimensions of the operand matrix.
     */
    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixC = new VisualMatrix(newARows, newACols, false);

        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(new Group(vMatrixB), operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }
}
