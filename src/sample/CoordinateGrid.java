package sample;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

public class CoordinateGrid extends Pane{

    private final double WIDTH;
    private final double HEIGHT;

    private double xOrigin;
    private double yOrigin;
    private Line xAxis;
    private Line yAxis;

    public CoordinateGrid(double width, double height)
    {
        WIDTH = width;
        HEIGHT = height;

        yOrigin = HEIGHT / 2;
        xOrigin = WIDTH / 2;

        getStyleClass().add("coordinate-grid");
        setDimensions();
        setAxes();
    }

    private void setDimensions()
    {
        setPrefSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);

        setScaleY(1);
    }

    private void setAxes()
    {
        for (int i = 50; i < WIDTH; i+= 50)
        {
            Line line = new Line(i, 0, i, HEIGHT);
            line.getStyleClass().add("line");
            getChildren().add(line);
        }
        for (int i = 50; i < HEIGHT; i+= 50)
        {
            Line line = new Line(0, i, WIDTH, i);
            line.getStyleClass().add("line");
            getChildren().add(line);
        }

        xAxis = new Line(0, yOrigin, WIDTH, yOrigin);
        xAxis.getStyleClass().add("axis");

        yAxis = new Line(xOrigin, 0, xOrigin, HEIGHT);
        yAxis.getStyleClass().add("axis");

        getChildren().addAll(xAxis, yAxis);
    }

    private void drawPointVector(double xCoord, double yCoord)
    {
        Double xPos = xOrigin + xCoord * 10;
        Double yPos = yOrigin - yCoord * 10;
        Circle vector = new Circle(xPos, yPos, 2);

        String coordString = "(" + Math.round(xCoord ) + "," + Math.round(yCoord ) + ")";
        Text coordText = new Text(xPos, yPos, coordString);
        getChildren().addAll(vector, coordText);
    }

    public VectorPolygon makeVectorPolygon(Vector[] vectors)
    {
        Vector origin = new Vector(xOrigin, yOrigin);
        VectorPolygon vectorPolygon = new VectorPolygon(vectors, origin);

        return vectorPolygon;
    }

    public void drawVectorPolygon(Vector[] vectors)
    {
        VectorPolygon vectorPolygon = makeVectorPolygon(vectors);
        Polygon polygon = new Polygon();
        polygon.getStyleClass().add("polygon");

        for (Vector vector : vectorPolygon.getAdjustedVectors())
        {
            try {
                polygon.getPoints().add(vector.getX());
                polygon.getPoints().add(vector.getY());
            } catch (NullPointerException npe) {}
        }

        getChildren().add(polygon);
    }

    public void drawPointVectors(Vector[] vectors)
    {
        getChildren().clear();
        setAxes();
        for (Vector vector : vectors)
        {
            try {
                drawPointVector(vector.getX(), vector.getY());
            } catch (NullPointerException npe) {}

        }
    }
}
