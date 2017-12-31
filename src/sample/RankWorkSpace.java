package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class RankWorkSpace extends WorkSpace {

    public RankWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixC = new VisualMatrix(1, 1, false);

        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("rank");
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {
        matrixA = createMatrix(matrixA, vMatrixA);

        int rank = matrixA.getRank();

        TextField textField = (TextField) vMatrixC.getChildren().get(0);
        textField.setText(decimalFormat.format(rank));
    }

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
