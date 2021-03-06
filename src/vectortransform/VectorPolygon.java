package vectortransform;

import javafx.collections.ObservableList;
import vectortransform.Vector;

import java.util.LinkedList;

/**
 * VectorPolygon.java: Class containing Vectors to be arranged in a polygon.
 * Can arrange Vectors in the correct order for polygon formation.
 * @author Max Thurell
 * @version 1.0
 */
public class VectorPolygon {

    private LinkedList<Vector> vectors;
    private Vector[] orderedVectors;
    private Vector[] adjustedVectors;

    private Vector origin;

    public Vector[] getAdjustedVectors() {
        return adjustedVectors;
    }

    /**
     * Creates a new VectorPolygon with the given Vector array and origin point.
     * @param vectors Array of vectors from which to form polygon.
     * @param origin The origin point towards which to adjust the Vectors in the polygon.
     */
    VectorPolygon(LinkedList<Vector> vectors, Vector origin)
    {
        this.vectors = vectors;
        this.origin = origin;
        grahamScan(vectors);
        adjustVectors();
    }

    /**
     * Finds the vector with the smallest y coordinate, for the purposes of ordering Vectors for polygon formation.
     *
     * @return Vector with the lowest y value.
     */
    private Vector findBottomVector()
    {
        Vector bottomVector = vectors.get(0);

        for (Vector vector : vectors)
        {
            if (vector.getY() < bottomVector.getY()) bottomVector = vector;
        }

        return bottomVector;
    }

    /**
     * Orders the Vectors in a list to create a non-overlapping polygon using a modified version of the Graham Scan.
     *
     * @param vectors Vectors to rearrange.
     */
    private void grahamScan(LinkedList<Vector> vectors)
    {
        orderedVectors = new Vector[vectors.size()];

        Vector bottomVector = findBottomVector();
        orderedVectors[0] = bottomVector;

        Vector currentVector = bottomVector;
        System.out.println("LOWEST Y VALUE " + currentVector.getY());
        for (int i = 1; i < orderedVectors.length; i++)
        {
            currentVector = getNextVector(vectors, currentVector);
            orderedVectors[i] = currentVector;
        }
    }

    /**
     *  Gets a vector from a list, based on the angle between a base vector and it.
     *
     * @param vectors Vectors from which to find the lowest-angled Vector.
     * @param baseVector The base Vector used to find angles.
     * @return Vector with the lowest angle to baseVector.
     */
    private Vector getNextVector(LinkedList<Vector> vectors, Vector baseVector)
    {
        Vector nextVector = vectors.get(0);
        double currentLowestAngle = 2*Math.PI;

        for (Vector vector : vectors)
        {

            if (!vector.equals(baseVector))
            {
                System.out.println("BASE Y: " + baseVector.getY());
                System.out.println("NEW Y: " + vector.getY());
                double newAngle = baseVector.get2DAngle(vector);
                if (newAngle < 0.0) newAngle += 2*Math.PI;
                if (newAngle < currentLowestAngle)
                {
                    currentLowestAngle = newAngle;
                    nextVector = vector;
                }
            }
        }
        return nextVector;
    }

    /**
     * Creates an array of adjusted, scaled up and flipped (for y coordinates) Vectors to place their origin points
     * in the middle of a CoordinateGrid and scale them up to an appropriate size to be clearly visible on the grid.
     */
    private void adjustVectors()
    {
        adjustedVectors = new Vector[orderedVectors.length];
        for (int i = 0; i < adjustedVectors.length; i++)
        {
            double xCoord = origin.getX() + orderedVectors[i].getX() * 10;
            double yCoord = origin.getY() - orderedVectors[i].getY() * 10;
            adjustedVectors[i] = new Vector(xCoord, yCoord);
        }
    }

}
