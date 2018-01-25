package matrixops.workspaces;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * GaussJordanWorkSpace.java: Graphical interface for performing Gauss-Jordan elimination.
 * @author Max Thurell
 * @version 1.0
 */
public class GaussJordanWorkSpace extends MatrixWorkSpace {

    /**
     * Creates a new InversionWorkSpace bound to a given CalcGUI.
     * Augments the operand matrix and result matrix.
     *
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    GaussJordanWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixA = new VisualMatrix(3, 4, true);
        vMatrixC = new VisualMatrix(3, 4, false);
        matrixAColField.setText("3");

        vMatrixC.setLargeFields();
        augmentMatrices();
        showMatrices();
    }

    /**
     * Edits borders in TextFields to indicate augmented matrices.
     */
    private void augmentMatrices()
    {
        for (int i = 0; i < vMatrixA.getM(); i++)
        {
            TextField textField = (TextField) getNodeByIndex(i, vMatrixA.getN() - 1, vMatrixA);
            textField.setStyle("-fx-border-width: 1px 1px 1px 3px; -fx-border-color: #AAA #AAA #AAA black");
        }
        for (int i = 0; i < vMatrixC.getM(); i++)
        {
            TextField textField = (TextField) getNodeByIndex(i, vMatrixC.getN() - 1, vMatrixC);
            textField.setStyle("-fx-border-width: 1px 1px 1px 3px; -fx-border-color: #AAA #AAA #AAA black; -fx-min-width: 100px");
        }
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("Gauss-Jordan");
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    /**
     * Performs a Gauss-Jordan elimination.
     */
    @Override
    public void doOperation() {
        matrixA = createMatrix(vMatrixA);

        matrixC = matrixA.gaussJordanEliminate(true);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
        vMatrixC.setLargeFields();
        augmentMatrices();
    }

    /**
     * Updates the dimensions of the operand matrix, and the result matrix to match.
     */
    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText()) + 1;

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixC = new VisualMatrix(newARows, newACols, false);
        vMatrixC.setLargeFields();
        augmentMatrices();

        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }
}
