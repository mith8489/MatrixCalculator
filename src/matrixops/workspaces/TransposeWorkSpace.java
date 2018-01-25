package matrixops.workspaces;

import javafx.scene.Group;
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * TransposeWorkSpace.java: Graphical interface for performing matrix transposition.
 * @author Max Thurell
 * @version 1.0
 */
public class TransposeWorkSpace extends MatrixWorkSpace {

    /**
     * Creates a new TransposeWorkSpace bound to a given CalcGUI.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    TransposeWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        showMatrices();
    }

    /**
     * Displays all matrices and the operator symbol.
     */
    private void showMatrices()
    {
        operatorSymbol.setText("T");
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    /**
     * Transposes the operand matrix.
     */
    @Override
    public void doOperation() {
        matrixA = createMatrix(vMatrixA);

        matrixC = matrixA.transpose();

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

    /**
     * Updates the dimensions of the operand matrix, and the result matrix to correspond.
     */
    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixC = new VisualMatrix(newACols, newARows, false);

        matrixBox.getChildren().clear();
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }
}
