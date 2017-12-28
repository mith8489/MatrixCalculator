package sample;

import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AdditionWorkSpace extends WorkSpace implements MatrixOperational {

    public AdditionWorkSpace()
    {
        super();
        showMatrices();
    }

    private void showMatrices()
    {
        operatorSymbol.setText("+");

        getChildren().addAll(vMatrixA, operatorSymbol, vMatrixB, equalitySign, vMatrixC);
    }


    @Override
    public Matrix doOperation() {

        createMatrix(matrixA, vMatrixA);
        createMatrix(matrixB, vMatrixB);

        matrixC = matrixA.plus(matrixB);

        return null;
    }
}
