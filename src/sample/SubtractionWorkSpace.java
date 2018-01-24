package sample;

import javafx.scene.Group;


public class SubtractionWorkSpace extends MatrixWorkSpace {

    public SubtractionWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, matrixBControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("-");

        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }


    @Override
    public void doOperation() {

        matrixA = createMatrix(matrixA, vMatrixA);
        matrixB = createMatrix(matrixB, vMatrixB);

        matrixC = matrixA.subtract(matrixB);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }
}
