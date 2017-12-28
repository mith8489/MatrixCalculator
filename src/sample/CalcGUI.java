package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;


public class CalcGUI extends BorderPane {

    private Matrix matrixA; // First matrix
    private Matrix matrixB; // Second matrix (if necessary)
    private Matrix matrixC; //Solution matrix

    private VisualMatrix vMatrixA; // First visual matrix
    private VisualMatrix vMatrixB; // Second visual matrix (if necessary)
    private VisualMatrix vMatrixC; //Solution visual matrix

    private HBox topBar;
    private ComboBox operationSelector;
    private Button selectOperationButton;

    private HBox bottomBar;
    private Button solveButton;

    private WorkSpace workSpace;

    public CalcGUI()
    {
        getStyleClass().add("calc-gui");
        makeWorkSpaces();
        makeTopBar();
        makeBottomBar();
    }

    private void makeWorkSpaces()
    {
        workSpace = new AdditionWorkSpace(this);

        setCenter(workSpace);
        workSpace.setAlignment(Pos.CENTER);
    }

    private void makeTopBar()
    {
        ObservableList<String> operationTypes = FXCollections.observableArrayList(
                "Addition",
                "Subtraction",
                "Scalar Multiplication"
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
    private void makeBottomBar()
    {
        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().add("solve-button");
        clearButton.setOnAction(event -> workSpace.clearMatrices());

        solveButton = new Button("Solve!");
        solveButton.getStyleClass().add("solve-button");
        solveButton.setOnAction(event -> workSpace.doOperation());

        bottomBar = new HBox();
        bottomBar.getStyleClass().add("top-bottom-bar");

        bottomBar.getChildren().addAll(clearButton, solveButton);
        setBottom(bottomBar);
    }

    private void changeWorkSpace(String operationType)
    {
        setCenter(null);
        switch (operationType)
        {
            case "Addition": workSpace = new AdditionWorkSpace(this);
            break;

            case "Subtraction": workSpace = new SubtractionWorkSpace(this);
            break;

            case "Scalar Multiplication": workSpace = new ScalarMultWorkSpace(this);
            break;

            default: workSpace = null;
        }
        setCenter(workSpace);
        workSpace.setAlignment(Pos.CENTER);
    }

/*    private void defineComboBoxBehaviour()
    {
        operationSelector.setCellFactory(new Callback<ListView<WorkSpace>,ListCell<WorkSpace>>() {

                @Override
                public ListCell<WorkSpace> call(ListView<WorkSpace> p) {

                    final ListCell<WorkSpace> cell = new ListCell<WorkSpace>() {

                        @Override
                        protected void updateItem(WorkSpace workSpace, boolean bln) {
                            super.updateItem(workSpace, bln);

                            if (workSpace != null) {
                                setText(workSpace.getWorkSpaceName().get());
                            } else {
                                setText(null);
                            }
                        }

                    };

                    return cell;
                }
            });


        operationSelector.setConverter(new StringConverter<WorkSpace>() {
            @Override
            public String toString(WorkSpace workSpace) {
                if (workSpace == null) {
                    return null;
                } else {
                    return workSpace.getWorkSpaceName().get();
                }
            }

            @Override
            public WorkSpace fromString(String personString) {
                return null; // No conversion fromString needed.
            }
        });*/
    }