package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class ScalarMultWorkSpace extends WorkSpace {


    public ScalarMultWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        vMatrixB = new VisualMatrix(1, 1, true);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("×");
        matrixBox.getChildren().addAll(new Group(vMatrixB), operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
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

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixC = new VisualMatrix(newARows, newACols, false);

        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(new Group(vMatrixB), operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }
}
