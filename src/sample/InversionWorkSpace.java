package sample;

import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InversionWorkSpace extends MatrixWorkSpace {

    private Text wrongRankText;

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
        clearMatrix(vMatrixC);
        try {matrixBox.getChildren().remove(wrongRankText);}
        catch (NullPointerException npe) {};

        if (matrixA.getRank() == matrixA.getM())
        {
            matrixC = matrixA.invert();
            vMatrixC = createVisualMatrix(matrixC, vMatrixC);
        }
        else
        {
            wrongRankText = new Text("DecimalMatrix rank < " + matrixA.getM() + ".\nDecimalMatrix not invertible.");
            wrongRankText.getStyleClass().add("matrix-error-text");
            matrixBox.getChildren().add(wrongRankText);
        }


    }

    @Override
    public void updateMatrixDimensions()
    {
        int newARows = Integer.parseInt(matrixARowField.getText());
        int newACols = Integer.parseInt(matrixAColField.getText());

        vMatrixA = new VisualMatrix(newARows, newACols, true);

        try {matrixBox.getChildren().remove(wrongRankText);}
        catch (NullPointerException npe) {}

        checkCompatibility(newARows, newACols);

        augmentMatrix();

        matrixBox.getChildren().clear();
        operatorSymbol.setStyle("-fx-translate-y:"+ Integer.toString((vMatrixA.getM() * -17) - 9) + "px; -fx-translate-x: -15px");
        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, equalitySign, new Group(vMatrixC));
    }

    protected void checkCompatibility(int newARows, int newACols)
    {
        matrixErrorText.setText("DecimalMatrix not invertible!");
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
