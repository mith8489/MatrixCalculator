package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class TransposeWorkSpace extends WorkSpace {

    String yTranslateSymbolDistance;

    public TransposeWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("T");
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {
        matrixA = createMatrix(matrixA, vMatrixA);

        matrixC = matrixA.transpose();

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

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
