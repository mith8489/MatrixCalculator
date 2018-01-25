package vectortransform;


import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * Graphics class representing a Cartesian coordinate grid. Can display position vectors and polygons.
 * @author Max Thurell
 * @version 1.0
 */
public class CoordinateGrid extends Pane{

    private final double WIDTH;
    private final double HEIGHT;

    private double xOrigin;
    private double yOrigin;

    /**
     * Creates a new CoordinateGrid with the given dimensions.
     *
     * @param width Width of the new CoordinateGrid.
     * @param height Height of the new CoordinateGrid.
     */
    CoordinateGrid(double width, double height)
    {
        WIDTH = width;
        HEIGHT = height;

        yOrigin = HEIGHT / 2;
        xOrigin = WIDTH / 2;

        getStyleClass().add("coordinate-grid");
        setDimensions();
        setAxes();
    }

    /**
     * Sets the size of the CoordinateGrid based on its width and height fields.
     */
    private void setDimensions()
    {
        setPrefSize(WIDTH, HEIGHT);
        setMaxSize(WIDTH, HEIGHT);

        setScaleY(1);
    }

    /**
     * Creates the x-and-y axes of the CoordinateGrid, as well as grid lines.
     */
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

        Line xAxis = new Line(0, yOrigin, WIDTH, yOrigin);
        xAxis.getStyleClass().add("axis");

        Line yAxis = new Line(xOrigin, 0, xOrigin, HEIGHT);
        yAxis.getStyleClass().add("axis");

        getChildren().addAll(xAxis, yAxis);
    }

    /**
     * Draws a position vector at the given coordinates.
     *
     * @param xCoord x coordinate of the vector.
     * @param yCoord y coordinate of the vector.
     */
    private void drawPositionVector(double xCoord, double yCoord)
    {
        Double xPos = xOrigin + xCoord * 10;
        Double yPos = yOrigin - yCoord * 10;
        Circle vector = new Circle(xPos, yPos, 2);

        String coordString = "(" + Math.round(xCoord ) + "," + Math.round(yCoord ) + ")";
        Text coordText = new Text(xPos, yPos, coordString);
        getChildren().addAll(vector, coordText);
    }

    /**
     * Creates a VectorPolygon based on the Vectors in an array.
     *
     * @param vectors The vectors from which to build the VectorPolygon.
     * @return VectorPolygon based on vector array.
     */
    private VectorPolygon makeVectorPolygon(Vector[] vectors)
    {
        Vector origin = new Vector(xOrigin, yOrigin);
        return new VectorPolygon(vectors, origin);
    }

    /**
     * Draws a polygon on the grid based on a VectorPolygon built from a Vector array.
     * @param vectors The vectors from which to draw the polygon.
     */
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
            } catch (NullPointerException npe) { npe.printStackTrace(); }
        }

        getChildren().add(polygon);
    }

    /**
     * Draws points on the grid based on Vectors in an array.
     *
     * @param vectors The vectors from which to draw the points.
     */
    public void drawPositionVectors(Vector[] vectors)
    {
        getChildren().clear();
        setAxes();
        for (Vector vector : vectors)
        {
            try {
                drawPositionVector(vector.getX(), vector.getY());
            } catch (NullPointerException npe) { npe.printStackTrace(); }

        }
    }
}
