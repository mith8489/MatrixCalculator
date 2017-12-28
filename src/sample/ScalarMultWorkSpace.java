package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class ScalarMultWorkSpace extends WorkSpace {


    public ScalarMultWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        vMatrixB = new VisualMatrix(1, 1);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("×");
        getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {
        matrixA = createMatrix(matrixA, vMatrixA);

        TextField textField = (TextField) vMatrixB.getChildren().get(0);
        int scalar = Integer.parseInt(textField.getText());

        matrixC = matrixA.scalarMult(scalar);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols);
        vMatrixC = new VisualMatrix(newARows, newACols);

        getChildren().clear();
        getChildren().addAll(new Group(dimensionControls), new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }
}
