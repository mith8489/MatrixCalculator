package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class MatrixMultWorkSpace extends WorkSpace {


    public MatrixMultWorkSpace(CalcGUI calcGUI)
    {
        super(calcGUI);
        dimensionControls.getChildren().addAll(matrixAControls, matrixBControls, controlButton);
        addSwapFunctionality();
        controlBox.getChildren().add(new Group(dimensionControls));
        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("×");

        matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
    }

    @Override
    public void doOperation() {

        matrixA = createMatrix(matrixA, vMatrixA);
        matrixB = createMatrix(matrixB, vMatrixB);

        matrixC = matrixA.matrixMult(matrixB);

        vMatrixC = createVisualMatrix(matrixC, vMatrixC);
    }

    @Override
    protected void checkCompatibility()
    {
        if (vMatrixA.getN() == vMatrixB.getM())
        {
            try {
                controlBox.getChildren().remove(matrixErrorText);
            } catch (NullPointerException npe) {}

            calcGUI.toggleSolveButton(true);
            vMatrixC = new VisualMatrix(vMatrixA.getM(), vMatrixB.getN(), false);
        }
        else
        {
            try {
                controlBox.getChildren().add(matrixErrorText);
                calcGUI.toggleSolveButton(false);
            } catch (IllegalArgumentException iae) {}
        }
    }

    private void addSwapFunctionality()
    {
        Button swapButton = new Button("Swap");
        swapButton.getStyleClass().add("control-button");
        swapButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                matrixA = createMatrix(matrixA, vMatrixA);
                matrixB = createMatrix(matrixB, vMatrixB);
                matrixA.swap(matrixB);

                vMatrixA = new VisualMatrix(matrixA.getM(), matrixA.getN(), true);
                vMatrixB = new VisualMatrix(matrixB.getM(), matrixB.getN(), true);
                vMatrixA = createVisualMatrix(matrixA, vMatrixA);
                vMatrixB = createVisualMatrix(matrixB, vMatrixB);
                vMatrixC = new VisualMatrix(vMatrixA.getM(), vMatrixB.getN(), false);

                checkCompatibility();

                matrixBox.getChildren().clear();
                matrixBox.getChildren().addAll(new Group(vMatrixA), operatorSymbol, new Group(vMatrixB), equalitySign, new Group(vMatrixC));
            }
        });

        dimensionControls.getChildren().add(swapButton);
    }
}
