package matrixops.workspaces;

import javafx.scene.Group;
import base.CalcGUI;

/**
 * Graphical interface for performing matrix subtraction.
 * @author Max Thurell
 * @version 1.0
 */
public class SubtractionWorkSpace extends MatrixWorkSpace {

    /**
     * Creates a new SubtractionWorkSpace bound to a given CalcGUI.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public SubtractionWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, matrixBControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("-");

        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }


    /**
     * Performs matrix subtraction.
     */
    @Override
    public void doOperation() {

        matrixA = createMatrix(vMatrixA);
        matrixB = createMatrix(vMatrixB);

        matrixC = matrixA.subtract(matrixB);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }
}
