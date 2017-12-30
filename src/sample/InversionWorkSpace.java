package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;

public class InversionWorkSpace extends WorkSpace{

    public InversionWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, controlButton);
        controlBox.getChildren().add(new Group(dimensionControls));

        vMatrixC = new VisualMatrix(3, 6, false);
        augmentMatrix();
        showMatrices();
    }

    private void augmentMatrix()
    {
            for (int i = 0; i < vMatrixC.getM(); i++)
            {
                TextField textField = (TextField) getNodeByIndex(i, vMatrixC.getN() / 2, vMatrixC);
                textField.setStyle("-fx-border-width: 1px 1px 1px 3px; -fx-border-color: #AAA #AAA #AAA black");
            }
    }

    private void showMatrices()
    {
        operatorSymbol.setText("-1");
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {
        matrixA = createMatrix(matrixA, vMatrixA);

        matrixC = matrixA.invert();

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);


        checkCompatibility(newARows, newACols);

        augmentMatrix();

        matrixBox.getChildren().clear();
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    protected void checkCompatibility(int newARows, int newACols)
    {
        matrixErrorText.setText("Matrix not invertible!");
        if (newARows == newACols)
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) {}

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(newARows, newACols * 2, false);
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
