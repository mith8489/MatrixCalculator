package vectortransform;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Graphical interface for creating and displaying vectors, and performing transformations on them.
 * @author Max Thurell
 * @version 1.0
 */
public class TransformationWorkSpace extends BorderPane {

    private Vector[] points;

    private VectorInputBox vectorInputBox;

    private CoordinateGrid coordinateGrid;
    private final int COORD_GRID_WIDTH = 500;
    private final int COORD_GRID_HEIGHT = 500;

    private VBox transformationMenu;

    public CoordinateGrid getCoordinateGrid() {
        return coordinateGrid;
    }

    public Vector[] getPoints() {
        return points;
    }

    /**
     * Creates a new TransformationWorkSpace.
     *
     * @param initFields The number of Vector creation fields to start with.
     */
    public TransformationWorkSpace(int initFields)
    {
        vectorInputBox = new VectorInputBox(initFields, this);
        vectorInputBox.getStyleClass().add("vector-input-box");
        setTop(vectorInputBox);
        makeCoordinateGrid();
        makeTransformationMenu();
    }

    /**
     * Creates a new Vector array based on coordinates from the Vector creation fields.
     */
    public void getPositionVectors() {
        points = new Vector[vectorInputBox.getNumOfPoints()];

        for (int i = 0; i < points.length; i++) {
            try {
                Double xVal = Double.parseDouble(vectorInputBox.getxFields().get(i).getText());
                Double yVal = Double.parseDouble(vectorInputBox.getyFields().get(i).getText());
                points[i] = new Vector(xVal, yVal);
            } catch (NumberFormatException nfe) { }
        }
    }

    /**
     * Draws position vectors and a corresponding polygon on the CoordinateGrid based on Vectors in the points array.
     */
    public void drawVectors()
    {
        coordinateGrid.drawPositionVectors(points);
        coordinateGrid.drawVectorPolygon(points);
    }

    /**
     * Creates a new CoordinateGrid with size based on the width and height fields.
     */
    private void makeCoordinateGrid()
    {
        coordinateGrid = new CoordinateGrid(COORD_GRID_WIDTH, COORD_GRID_HEIGHT);
        setCenter(coordinateGrid);
    }

    /**
     * Creates the menu for performing a number of different vector transformations.
     */
    private void makeTransformationMenu()
    {
        /*--------------------SCALING--------------------*/
        HBox scaleBox = new HBox();
        HBox scaleXBox = new HBox();
        HBox scaleYBox = new HBox();

        Button scaleButton = new Button("Scale");
        Button scaleXButton = new Button("Scale X");
        Button scaleYButton = new Button("Scale Y");

        TextField scaleField = new TextField();
        TextField scaleXField = new TextField();
        TextField scaleYField = new TextField();

        scaleButton.setOnAction(event -> scaleProportional(Double.parseDouble(scaleField.getText())));
        scaleXButton.setOnAction(event -> scaleX(Double.parseDouble(scaleXField.getText())));
        scaleYButton.setOnAction(event -> scaleY(Double.parseDouble(scaleYField.getText())));

        scaleBox.getChildren().addAll(scaleButton, scaleField);
        scaleXBox.getChildren().addAll(scaleXButton, scaleXField);
        scaleYBox.getChildren().addAll(scaleYButton, scaleYField);

        /*--------------------REFLECTION--------------------*/
        Button reflectButton = new Button("Reflect");
        Button reflectXButton = new Button("Reflect X");
        Button reflectYButton = new Button("Reflect Y");

        reflectButton.setOnAction(event -> reflect());
        reflectXButton.setOnAction(event -> reflectX());
        reflectYButton.setOnAction(event -> reflectY());

        HBox reflectBox = new HBox(reflectButton);
        HBox reflectXBox = new HBox(reflectXButton);
        HBox reflectYBox = new HBox(reflectYButton);

        /*--------------------ROTATION--------------------*/
        HBox rotateCWBox = new HBox();
        HBox rotateCCWBox = new HBox();

        Button rotateCWButton = new Button("Rotate CW");
        Button rotateCCWButton = new Button("Rotate CCW");

        TextField rotateCWField = new TextField();
        TextField rotateCCWField = new TextField();

        rotateCWButton.setOnAction(event -> rotateCW(Integer.parseInt(rotateCWField.getText())));
        rotateCCWButton.setOnAction(event -> rotateCCW(Integer.parseInt(rotateCCWField.getText())));

        rotateCWBox.getChildren().addAll(rotateCWButton, rotateCWField);
        rotateCCWBox.getChildren().addAll(rotateCCWButton, rotateCCWField);

        /*--------------------SHEARING--------------------*/
        HBox shearXBox = new HBox();
        HBox shearYBox = new HBox();

        Button shearXButton = new Button("Shear X");
        Button shearYButton = new Button("Shear Y");

        TextField shearXField = new TextField();
        TextField shearYField = new TextField();

        shearXButton.setOnAction(event -> shearX(Double.parseDouble(shearXField.getText())));
        shearYButton.setOnAction(event -> shearY(Double.parseDouble(shearYField.getText())));

        shearXBox.getChildren().addAll(shearXButton, shearXField);
        shearYBox.getChildren().addAll(shearYButton, shearYField);

        transformationMenu = new VBox();
        transformationMenu.getStyleClass().add("transformation-menu");
        transformationMenu.getChildren().addAll(scaleBox, scaleXBox, scaleYBox, reflectBox, reflectXBox, reflectYBox, rotateCWBox, rotateCCWBox, shearXBox, shearYBox);
        for (Node node : transformationMenu.getChildren())
        {
            node.getStyleClass().add("transformation-hbox");
        }
        setRight(transformationMenu);
    }

    /*--------------------TRANSFORMATIONS----------------------------------------*/

    /**
     * Scales the x value of all Vectors in the points array by a scalar.
     * @param scalar The value by which to scale.
     */
    private void scaleX(double scalar)
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].scaleX(scalar);
        }
        drawVectors();
    }

    /**
     * Scales the y value of all Vectors in the points array by a scalar.
     * @param scalar The value by which to scale.
     */
    private void scaleY(double scalar)
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].scaleY(scalar);
        }
        drawVectors();
    }

    /**
     * Scales the x and y values of all Vectors in the points array by a scalar.
     * @param scalar The value by which to scale.
     */
    private void scaleProportional(double scalar)
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].scaleProportional(scalar);
        }
        drawVectors();
    }

    /**
     * Reflects all Vectors in the points array around the origin point.
     */
    private void reflect()
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].reflect2DOrigin();
        }
        drawVectors();
    }

    /**
     * Reflects all Vectors in the points array around the x axis.
     */
    private void reflectX()
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].reflect2DX();
        }
        drawVectors();
    }

    /**
     * Reflects all Vectors in the points array around the y axis.
     */
    private void reflectY()
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].reflect2DY();
        }
        drawVectors();
    }

    /**
     * Rotates all Vectors in the points array in a clockwise direction.
     * @param degrees The number of degrees by which to rotate the Vectors.
     */
    private void rotateCW(int degrees)
    {
        double rad = degrees * (Math.PI / 180);
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].rotate2D(rad, true);
        }
        drawVectors();
    }

    /**
     * Rotates all Vectors in the points array in a counter-clockwise direction.
     * @param degrees The number of degrees by which to rotate the Vectors.
     */
    private void rotateCCW(int degrees)
    {
        double rad = degrees * (Math.PI / 180);
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].rotate2D(rad, false);
        }
        drawVectors();
    }

    /**
     * Shears all Vectors in the points array along the x axis. {@see vectortransform.Vector.shear(int, int, int, double) shear} method.
     * @param scalar The value by which to scale.
     */
    private void shearX(double scalar)
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].shear2DX(scalar);
        }
        drawVectors();
    }

    /**
     * Scales all Vectors in the points array along the y axis. {@see vectortransform.Vector.shear(int, int, int, double) shear} method.
     * @param scalar The value by which to scale.
     */
    private void shearY(double scalar)
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i] = points[i].shear2DY(scalar);
        }
        drawVectors();
    }


}
