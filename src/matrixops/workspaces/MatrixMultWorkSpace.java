package matrixops.workspaces;

import javafx.scene.Group;
import javafx.scene.control.Button;
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * MatrixMultWorkSpace.java: Graphical interface for performing matrix multiplication.
 * @author Max Thurell
 * @version 1.0
 */
public class MatrixMultWorkSpace extends MatrixWorkSpace {


    /**
     * Creates a new MatrixMultWorkSpace bound to a given CalcGUI.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    public MatrixMultWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        vMatrixC.setLargeFields();
        dimensionControls.getChildren().addAll(matrixAControls, matrixBControls, controlButton);
        addSwapFunctionality();
        controlBox.getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("×");

        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }

    /**
     * Performs matrix multiplication (AxB = C).
     */
    public void doOperation() {

        matrixA = createMatrix(vMatrixA);
        matrixB = createMatrix(vMatrixB);

        matrixC = matrixA.matrixMult(matrixB);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
        vMatrixC.setLargeFields();
    }

    /**
     * Checks if the operand matrices are compatible for multiplication.
     * If they are, changes the dimensions of the result matrix to correspond and makes the Solve button clickable.
     * If not, makes the Solve button un-clickable and displays an error message.
     */
    @Override
    protected void checkCompatibility()
    {
        if (vMatrixA.getN() == vMatrixB.getM())
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) { npe.printStackTrace(); }

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(vMatrixA.getM(), vMatrixB.getN(), false);
            vMatrixC.setLargeFields();
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) { iae.printStackTrace(); }
        }
    }

    /**
     * Adds functionality to swap the places of the operand matrices (AxB = C -> BxA = C).
     * Useful as matrix multiplication is non-commutative.
     */
    private void addSwapFunctionality()
    {
        Button swapButton = new Button("Swap");
        swapButton.getStyleClass().add("control-button");
        swapButton.setOnAction(event -> {
            matrixA = createMatrix(vMatrixB);
            matrixB = createMatrix(vMatrixA);

            vMatrixA = new VisualMatrix(matrixA.getM(), matrixA.getN(), true);
            vMatrixB = new VisualMatrix(matrixB.getM(), matrixB.getN(), true);
            vMatrixA = createVisualMatrix(matrixA, vMatrixA);
            vMatrixB = createVisualMatrix(matrixB, vMatrixB);
            vMatrixC = new VisualMatrix(vMatrixA.getM(), vMatrixB.getN(), false);
            vMatrixC.setLargeFields();

            checkCompatibility();

            matrixBox.getChildren().clear();
            matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
        });

        dimensionControls.getChildren().add(swapButton);
    }
}
