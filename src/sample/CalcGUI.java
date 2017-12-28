package sample;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CalcGUI extends BorderPane {

    private Matrix matrixA; // First matrix
    private Matrix matrixB; // Second matrix (if necessary)
    private Matrix matrixC; //Solution matrix

    private VisualMatrix vMatrixA; // First visual matrix
    private VisualMatrix vMatrixB; // Second visual matrix (if necessary)
    private VisualMatrix vMatrixC; //Solution visual matrix

    private HBox topBar;
    private HBox bottomBar;

    private WorkSpace workSpace;

    public CalcGUI()
    {
        makeWorkSpaces();
        makeTopBar();
        makeBottomBar();
    }

    private void makeWorkSpaces()
    {
        workSpace = new AdditionWorkSpace();

        setCenter(workSpace);
        workSpace.setAlignment(Pos.CENTER);
    }

    private void makeTopBar()
    {
        topBar = new HBox();
        topBar.getStyleClass().add("top-bottom-bar");
        setTop(topBar);
    }
    private void makeBottomBar()
    {
        bottomBar = new HBox();
        bottomBar.getStyleClass().add("top-bottom-bar");
        setBottom(bottomBar);
    }
}

/*
private Matrix matrixA; // First matrix
    private Matrix matrixB; // Second matrix (if necessary)
    private Matrix matrixC; //Solution matrix

    private VisualMatrix vMatrixA; // First visual matrix
    private VisualMatrix vMatrixB; // Second visual matrix (if necessary)
    private VisualMatrix vMatrixC; //Solution visual matrix

    private HBox topBar;
    private HBox workSpace;
    private HBox bottomBar;

    public CalcGUI()
    {
        makeWorkSpace();
        makeTopBar();
        makeBottomBar();
    }

    private void makeWorkSpace()
    {
        workSpace = new HBox();
        workSpace.getStyleClass().add("work-space");
        vMatrixA = new VisualMatrix(3, 3);
        vMatrixB = new VisualMatrix(3, 3);
        vMatrixC = new VisualMatrix(3, 3);

        workSpace.getChildren().addAll(vMatrixA, vMatrixB, vMatrixC);
        setCenter(workSpace);
        workSpace.setAlignment(Pos.CENTER);
    }

    private void makeTopBar()
    {
        topBar = new HBox();
        topBar.getStyleClass().add("top-bottom-bar");
        setTop(topBar);
    }
    private void makeBottomBar()
    {
        bottomBar = new HBox();
        bottomBar.getStyleClass().add("top-bottom-bar");
        setBottom(bottomBar);
    }

    private Node getNodeByIndex (int row, int column, GridPane gridPane) {
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
 */
