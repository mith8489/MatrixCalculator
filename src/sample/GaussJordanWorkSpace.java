package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class GaussJordanWorkSpace extends MatrixWorkSpace {

    public GaussJordanWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixA = new VisualMatrix(3, 4, true);
        vMatrixC = new VisualMatrix(3, 4, false);
        matrixAColField.setText("4");

        vMatrixC.setLargeFields();
        augmentMatrices();
        showMatrices();
    }

    private void augmentMatrices()
    {
        VisualMatrix[] visualMatrices = {vMatrixA, vMatrixC};
        for (VisualMatrix visualMatrix : visualMatrices)
        {
            for (int i = 0; i < visualMatrix.getM(); i++)
            {
                TextField textField = (TextField) getNodeByIndex(i, visualMatrix.getN() - 1, visualMatrix);
                textField.setStyle("-fx-border-width: 1px 1px 1px 3px; -fx-border-color: #AAA #AAA #AAA black; -fx-min-width: 100px");
            }
        }
    }

    private void showMatrices()
    {
        operatorSymbol.setText("Gauss-Jordan");
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {
        matrixA = createMatrix(matrixA, vMatrixA);

        matrixC = matrixA.gaussJordanEliminate(true);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
        vMatrixC.setLargeFields();
        augmentMatrices();
    }

    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);
        vMatrixC = new VisualMatrix(newARows, newACols, false);
        vMatrixC.setLargeFields();
        augmentMatrices();

        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }
}
