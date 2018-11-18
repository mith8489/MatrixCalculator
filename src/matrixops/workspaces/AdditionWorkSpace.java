package matrixops.workspaces;

import javafx.scene.Group;
import base.CalcGUI;


/**
 * Graphical interface for performing matrix addition.
 * @author Max Thurell
 * @version 1.0
 */
public class AdditionWorkSpace extends MatrixWorkSpace {

    /**
     * Creates a new AdditionWorkSpace bound to a given {@link base.CalcGUI CalcGUI}.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public AdditionWorkSpace(CalcGUI calcGUI)
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
        operatorSymbol.setText("+");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }

    /**
     * Performs matrix addition.
     */
    public void doOperation() {

        matrixA = createMatrix(vMatrixA);
        matrixB = createMatrix(vMatrixB);

        matrixC = matrixA.add(matrixB);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }
}
