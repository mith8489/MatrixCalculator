package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class CalcGUI extends BorderPane {

    private BorderPane topBar;

    private Button matrixWorkSpaceButton;
    private Button transformationWorkSpaceButton;

    private HBox operationSelector;
    private ComboBox operationSelectorBox;
    private Button selectOperationButton;

    private HBox bottomBar;
    private Button clearButton;
    private Button solveButton;

    private MatrixWorkSpace matrixWorkSpace;
    private TransformationWorkSpace transformationWorkSpace;

    public CalcGUI() {
        getStyleClass().add("calc-gui");
        makeTopBar();
        makeBottomBar();
        makeWorkSpaces();
    }

    private void makeWorkSpaces() {
        matrixWorkSpace = new AdditionWorkSpace(this);
        transformationWorkSpace = new TransformationWorkSpace(2);

        setWorkSpaceType(true);
        matrixWorkSpace.setAlignment(Pos.CENTER);
    }

    private void makeTopBar() {

        VBox workSpaceTypeSelector = new VBox();

        matrixWorkSpaceButton = new Button("Matrix Operations");
        matrixWorkSpaceButton.setOnAction(event -> setWorkSpaceType(true));
        matrixWorkSpaceButton.getStyleClass().add("workspace-selector-button");

        transformationWorkSpaceButton = new Button("Transformations");
        transformationWorkSpaceButton.setOnAction(event -> setWorkSpaceType(false));
        transformationWorkSpaceButton.getStyleClass().add("workspace-selector-button");

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

        operationSelectorBox = new ComboBox(operationTypes);
        operationSelectorBox.getSelectionModel().selectFirst();

        selectOperationButton = new Button("Select");
        selectOperationButton.setOnAction(event -> changeWorkSpace((String) operationSelectorBox.getSelectionModel().getSelectedItem()));

        operationSelector = new HBox();
        operationSelector.getChildren().addAll(operationSelectorBox, selectOperationButton);

        Region counterRegion = new Region();
        counterRegion.setMinWidth(250);

        topBar = new BorderPane();
        topBar.getStyleClass().add("top-bottom-bar");

        topBar.setLeft(workSpaceTypeSelector);
        operationSelector.setAlignment(Pos.CENTER);
        topBar.setCenter(operationSelector);
        topBar.setRight(counterRegion);
        setTop(topBar);
    }

    private void setWorkSpaceType(boolean isMatrixWorkSpace)
    {
        if (isMatrixWorkSpace)
        {
            try {
                topBar.setCenter(operationSelector);
                bottomBar.getChildren().addAll(clearButton, solveButton);
                matrixWorkSpaceButton.setStyle("-fx-background-color: #EF7200");
                transformationWorkSpaceButton.setStyle("-fx-background-color: -fx-button-colour");
            }
            catch (IllegalArgumentException iae) {}

            setCenter(matrixWorkSpace);
        }
        else
        {
            try {
                topBar.setCenter(null);
                bottomBar.getChildren().clear();
                transformationWorkSpaceButton.setStyle("-fx-background-color: #EF7200");
                matrixWorkSpaceButton.setStyle("-fx-background-color: -fx-button-colour");
                }
            catch (NullPointerException npe) {}

            setCenter(transformationWorkSpace);
        }
    }

    public void toggleSolveButton(boolean isInteractable)
    {
        solveButton.setDisable(!isInteractable);
    }

    private void makeBottomBar() {
        clearButton = new Button("Clear");
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