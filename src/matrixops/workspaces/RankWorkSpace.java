package matrixops.workspaces;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import base.CalcGUI;
import matrixops.VisualMatrix;

/**
 * RankWorkSpace.java: Graphical interface for finding the rank of a matrix.
 * @author Max Thurell
 * @version 1.0
 */
public class RankWorkSpace extends MatrixWorkSpace {

    /**
     * Creates a new RankWorkSpace bound to a given CalcGUI.
     * @param calcGUI Graphical User Interface in which the workspace is placed.
     */
    RankWorkSpace(CalcGUI calcGUI)
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
        operatorSymbol.setText("rank");
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    /**
     * Finds the rank of the operand matrix.
     */
    @Override
    public void doOperation() {
        matrixA = createMatrix(vMatrixA);

        int rank = matrixA.getRank();

        TextField textField = (TextField) vMatrixC.getChildren().get(0);
        textField.setText(Integer.toString(rank));
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

        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }
}
