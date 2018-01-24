package sample;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.LinkedList;

public class VectorPolygon {

    private Vector[] vectors;
    private Vector[] orderedVectors;
    private Vector[] adjustedVectors;

    private Vector origin;

    public Vector[] getAdjustedVectors() {
        return adjustedVectors;
    }

    public Vector[] getOrderedVectors() {
        return orderedVectors;
    }

    public VectorPolygon(Vector[] vectors, Vector origin)
    {
        this.vectors = vectors;
        this.origin = origin;
        grahamScan(vectors);
//        orderedVectors = orderCCW(vectors);
        adjustVectors();
    }

    private double getDistance(Vector v1, Vector v2)
    {
        double xDist = v1.getX() - v2.getX();
        double yDist = v1.getY() - v2.getY();
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

        return dist;
    }

    private Vector findBottomVector()
    {
        Vector bottomVector = vectors[0];

        System.out.println(vectors.length);
        for (Vector vector : vectors)
        {
            if (vector.getY() < bottomVector.getY()) bottomVector = vector;
        }

        return bottomVector;
    }

    public void grahamScan(Vector[] vectors)
    {
        orderedVectors = new Vector[vectors.length];

        Vector bottomVector = findBottomVector();
        orderedVectors[0] = bottomVector;

        Vector currentVector = bottomVector;
        for (int i = 1; i < orderedVectors.length; i++)
        {
            currentVector = getNextVector(vectors, currentVector);
            orderedVectors[i] = currentVector;
        }
    }

    private Vector getNextVector(Vector[] vectors, Vector baseVector)
    {
        Vector nextVector = vectors[0];
        double currentLowestAngle = 2*Math.PI;

        for (Vector vector : vectors)
        {

            if (!vector.isEqualTo(baseVector))
            {
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
