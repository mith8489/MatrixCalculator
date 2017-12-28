package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Group;


public class SubtractionWorkSpace extends WorkSpace {

    public SubtractionWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, matrixBControls, controlButton);
        getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("-");

        getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }


    @Override
    public void doOperation() {

        matrixA = createMatrix(matrixA, vMatrixA);
        matrixB = createMatrix(matrixB, vMatrixB);

        matrixC = matrixA.minus(matrixB);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());
        int newBRows = Integer.parseInt(matrixBRowField.getText());
        int newBCols = Integer.parseInt(matrixBColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols);
        vMatrixB = new VisualMatrix(newBRows, newBCols);

        if (vMatrixA.getM() == vMatrixB.getM() && vMatrixA.getN() == vMatrixB.getN())
        {
            vMatrixC = new VisualMatrix(newARows, newACols);
        }
        else
        {

        }

        getChildren().clear();
        getChildren().addAll(new Group(dimensionControls), new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }
}
