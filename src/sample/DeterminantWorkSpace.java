package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class DeterminantWorkSpace extends WorkSpace {

    public DeterminantWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixC = new VisualMatrix(1, 1, false);

        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("det");
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {
        matrixA = createMatrix(matrixA, vMatrixA);

        double determinant = matrixA.getDeterminant();

        TextField textField = (TextField) vMatrixC.getChildren().get(0);
        textField.setText(decimalFormat.format(determinant));
    }

    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);

        checkCompatibility(newARows, newACols);


        matrixBox.getChildren().clear();
        matrixBox.getChildren().addAll(operatorSymbol, new Group(vMatrixA), equalitySign, new Group(vMatrixC));
    }

    protected void checkCompatibility(int newARows, int newACols)
    {
        matrixErrorText.setText("Matrix not square!");
        if (newARows == newACols)
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) {}

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(1, 1, false);
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) {}
        }
    }
}