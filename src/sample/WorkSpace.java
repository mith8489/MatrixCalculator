package sample;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class WorkSpace extends HBox {

    protected Matrix matrixA; // First matrix
    protected Matrix matrixB; // Second matrix (if necessary)
    protected Matrix matrixC; //Solution matrix

    protected VisualMatrix vMatrixA; // First visual matrix
    protected VisualMatrix vMatrixB; // Second visual matrix (if necessary)
    protected VisualMatrix vMatrixC; //Solution visual matrix
    protected VisualMatrix[] vMatrices = {vMatrixA, vMatrixB, vMatrixC};

    protected Text operatorSymbol;
    protected Text equalitySign;

    public WorkSpace()
    {
        vMatrixA = new VisualMatrix(3, 3);
        vMatrixB = new VisualMatrix(3, 3);
        vMatrixC = new VisualMatrix(3, 3);

        operatorSymbol = new Text();
        operatorSymbol.getStyleClass().add("operator-symbol");
        equalitySign = new Text("=");
        equalitySign.getStyleClass().add("operator-symbol");
    }

    protected void createMatrix(Matrix matrix, VisualMatrix vMatrix)
    {
        matrix = new Matrix(vMatrix.getM(), vMatrix.getN());
        for (int i = 0; i < matrix.getM(); i++)
        { for (int j = 0; j < matrix.getN(); j++)
        {
            TextField textField = (TextField) getNodeByIndex(i, j, vMatrix);
            matrix.setElement(i, j, Double.parseDouble(textField.getText()));
        }
        }
    }

    protected Node getNodeByIndex (int row, int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public void clearMatrices()
    {
        for (VisualMatrix vMatrix : vMatrices)
        {
            for (Node textField : vMatrix.getChildren())
            {
                ((TextField) textField).setText("");
            }
        }
    }
}
