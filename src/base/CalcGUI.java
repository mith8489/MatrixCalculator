package base;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import matrixops.workspaces.AdditionWorkSpace;
import matrixops.workspaces.MatrixWorkSpace;
import vectortransform.TransformationWorkSpace;


/**
 * Graphical interface class for performing matrix operations and vector transformations.
 * Holds workspaces for operations and transformations that can be switched between.
 *
 * @author Max Thurell
 * @version 1.0
 */
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

    /**
     * Creates a new CalcGUI containing the graphical elements for performing matrix operations and transformations,
     * including the corresponding workspaces.
     */
    public CalcGUI() {
        getStyleClass().add("calc-gui");
        makeTopBar();
        makeBottomBar();
        makeWorkSpaces();
    }

    /**
     * Creates a MatrixWorkSpace and TransformationWorkSpace. The MatrixWorkSpace is an AdditionWorkSpace
     * by default, and is also the active workspace by default.
     */
    private void makeWorkSpaces() {
        matrixWorkSpace = new AdditionWorkSpace(this);
        transformationWorkSpace = new TransformationWorkSpace(2);

        setWorkSpaceType(true);
        matrixWorkSpace.setAlignment(Pos.CENTER);
    }

    /**
     * Makes the top bar, containing the toggle between matrix operations and vector transformations,
     * as well as the menu to switch between types of matrix operations.
     */
    private void makeTopBar() {

        VBox workSpaceTypeSelector = new VBox();

        matrixWorkSpaceButton = new Button("Matrix Operations");
        matrixWorkSpaceButton.setOnAction(event -> setWorkSpaceType(true));
        matrixWorkSpaceButton.getStyleClass().add("workspace-selector-button");

        transformationWorkSpaceButton = new Button("Transformations");
        transformationWorkSpaceButton.setOnAction(event -> setWorkSpaceType(false));
        transformationWorkSpaceButton.getStyleClass().add("workspace-selector-button");
        transformationWorkSpaceButton.setStyle("-fx-border-color: 0 0 2px 0");

        workSpaceTypeSelector.getChildren().addAll(matrixWorkSpaceButton, transformationWorkSpaceButton);

        operationSelectorBox = new ComboBox<WorkSpaceType>();
        operationSelectorBox.getItems().setAll(WorkSpaceType.values());
        operationSelectorBox.getSelectionModel().selectFirst();

        selectOperationButton = new Button("Select");
        selectOperationButton.setOnAction(event -> changeWorkSpace((WorkSpaceType) operationSelectorBox.getSelectionModel().getSelectedItem()));

        operationSelector = new HBox();
        operationSelector.getStyleClass().add("operation-selector");
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

    /**
     * Switches the type of workspace between MatrixWorkSpace and TransformationWorkSpace.
     * Removes redundant buttons if necessary.
     * @param isMatrixWorkSpace Indicates whether to switch to MatrixWorkSpace or TransformationWorkSpace.
     */
    private void setWorkSpaceType(boolean isMatrixWorkSpace)
    {
        if (isMatrixWorkSpace)
        {
            try {
                topBar.setCenter(operationSelector);
                bottomBar.getChildren().addAll(clearButton, solveButton);
            }
            catch (IllegalArgumentException iae) {}

            matrixWorkSpaceButton.setStyle("-fx-background-color: #EF7200");
            transformationWorkSpaceButton.setStyle("-fx-background-color: -fx-button-colour");
            setCenter(matrixWorkSpace);
        }
        else
        {
            try {
                topBar.setCenter(null);
                bottomBar.getChildren().clear();
                }
            catch (NullPointerException npe) { npe.printStackTrace(); }

            transformationWorkSpaceButton.setStyle("-fx-background-color: #EF7200");
            matrixWorkSpaceButton.setStyle("-fx-background-color: -fx-button-colour");
            setCenter(transformationWorkSpace);
        }
    }

    /**
     * Toggles the clickability of the Solve button.
     * @param isInteractable Indicates whether to make the Solve button clickable or un-clickable.
     */
    public void toggleSolveButton(boolean isInteractable)
    {
        solveButton.setDisable(!isInteractable);
    }

    /**
     * Makes the bottom bar, containing buttons to solve the current operation and to clear the operand matrices.
     */
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


    /**
     * Changes the current operation workspace to the given WorkSpaceType.
     * @param type The type of workspace to which to switch.
     */
    private void changeWorkSpace(WorkSpaceType type)
    {
        matrixWorkSpace = type.setWorkSpace(this);
        setCenter(matrixWorkSpace);
        matrixWorkSpace.setAlignment(Pos.CENTER);
        toggleSolveButton(true);
    }
}