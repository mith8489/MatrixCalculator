package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class CalcGUI extends BorderPane {

    private HBox topBar;
    private ComboBox operationSelector;
    private Button selectOperationButton;

    private HBox bottomBar;
    private Button solveButton;

    private MatrixWorkSpace matrixWorkSpace;
    private TransformationWorkSpace transformationWorkSpace;

    public CalcGUI() {
        getStyleClass().add("calc-gui");
        makeWorkSpaces();
        makeTopBar();
        makeBottomBar();
    }

    private void makeWorkSpaces() {
        matrixWorkSpace = new AdditionWorkSpace(this);
        transformationWorkSpace = new TransformationWorkSpace(4);

        setCenter(transformationWorkSpace);
        matrixWorkSpace.setAlignment(Pos.CENTER);
    }

    private void makeTopBar() {

        VBox workSpaceTypeSelector = new VBox();

        Button matrixWorkSpaceButton = new Button("Matrix Operations");
        Button transformationWorkSpaceButton = new Button("Transformations");
        workSpaceTypeSelector.getChildren().addAll(matrixWorkSpaceButton, transformationWorkSpaceButton);


        ObservableList<String> operationTypes = FXCollections.observableArrayList(
                "Addition",
                "Subtraction",
                "Scalar Multiplication",
                "Matrix Multiplication",
                "Gauss-Jordan Elimination",
                "Transposition",
                "Inversion",
                "Determinant",
                "Matrix Rank"
        );

        operationSelector = new ComboBox(operationTypes);
        operationSelector.getSelectionModel().selectFirst();

        selectOperationButton = new Button("Select");
        selectOperationButton.setOnAction(event -> changeWorkSpace((String) operationSelector.getSelectionModel().getSelectedItem()));

        topBar = new HBox();
        topBar.getStyleClass().add("top-bottom-bar");

        topBar.getChildren().addAll(operationSelector, selectOperationButton);
        setTop(topBar);
    }

    private void setWorkSpaceType(boolean isMatrixWorkSpace)
    {
        if (isMatrixWorkSpace)
        {
            try { topBar.getChildren().addAll(operationSelector, selectOperationButton); }
            catch (IllegalArgumentException iae) {}

            setCenter(matrixWorkSpace);
        }
        else
        {
            try { topBar.getChildren().removeAll(operationSelector, selectOperationButton); }
            catch (NullPointerException npe) {}

            setCenter(transformationWorkSpace);
        }
    }

    public void toggleSolveButton(boolean isInteractable)
    {
        solveButton.setDisable(!isInteractable);
    }

    private void makeBottomBar() {
        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().add("bottom-bar-button");
        clearButton.setOnAction(event -> matrixWorkSpace.clearMatrices());

        solveButton = new Button("Solve!");
        solveButton.getStyleClass().add("solve-button");
        solveButton.setOnAction(event -> matrixWorkSpace.doOperation());

        bottomBar = new HBox();
        bottomBar.getStyleClass().add("top-bottom-bar");

        bottomBar.getChildren().addAll(clearButton, solveButton);
        setBottom(bottomBar);
    }

    private void changeWorkSpace(String operationType) {
        setCenter(null);
        switch (operationType) {
            case "Addition":
                matrixWorkSpace = new AdditionWorkSpace(this);
                break;

            case "Subtraction":
                matrixWorkSpace = new SubtractionWorkSpace(this);
                break;

            case "Scalar Multiplication":
                matrixWorkSpace = new ScalarMultWorkSpace(this);
                break;

            case "Matrix Multiplication":
                matrixWorkSpace = new MatrixMultWorkSpace(this);
                break;

            case "Gauss-Jordan Elimination":
                matrixWorkSpace = new GaussJordanWorkSpace(this);
                break;

            case "Transposition":
                matrixWorkSpace = new TransposeWorkSpace(this);
                break;

            case "Inversion":
                matrixWorkSpace = new InversionWorkSpace(this);
                break;

            case "Determinant":
                matrixWorkSpace = new DeterminantWorkSpace(this);
                break;

            case "Matrix Rank":
                matrixWorkSpace = new RankWorkSpace(this);
                break;

            default:
                matrixWorkSpace = null;
        }
        setCenter(matrixWorkSpace);
        matrixWorkSpace.setAlignment(Pos.CENTER);
        toggleSolveButton(true);
    }

}