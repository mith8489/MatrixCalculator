package sample;

import javafx.scene.layout.BorderPane;

public class TransformationWorkSpace extends BorderPane {

    private Vector[] points;

    private VectorInputBox vectorInputBox;

    private CoordinateGrid coordinateGrid;
    private final int COORD_GRID_WIDTH = 700;
    private final int COORD_GRID_HEIGHT = 700;

    public CoordinateGrid getCoordinateGrid() {
        return coordinateGrid;
    }

    public Vector[] getPoints() {
        return points;
    }

    public TransformationWorkSpace(int initFields)
    {
        vectorInputBox = new VectorInputBox(initFields, this);
        vectorInputBox.getStyleClass().add("dimension-controls");
        setTop(vectorInputBox);
        makeCoordinateGrid();
    }

    public Vector[] getPointVectors() {
        points = new Vector[vectorInputBox.getNumOfPoints()];

        for (int i = 0; i < points.length; i++) {
            try {
                Double xVal = Double.parseDouble(vectorInputBox.getxFields().get(i).getText());
                Double yVal = Double.parseDouble(vectorInputBox.getyFields().get(i).getText());
                points[i] = new Vector(xVal, yVal);
            } catch (NumberFormatException nfe) { }
        }

        return points;
    }

    public void drawVectors()
    {
        getPointVectors();
        coordinateGrid.drawPointVectors(points);
        coordinateGrid.drawVectorPolygon(points);
    }

    private void makeCoordinateGrid()
    {
        coordinateGrid = new CoordinateGrid(COORD_GRID_WIDTH, COORD_GRID_HEIGHT);
        setCenter(coordinateGrid);
    }
}
