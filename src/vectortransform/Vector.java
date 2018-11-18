package vectortransform;


/**
 * Vector.java: Vector data structure with elements represented by Double values. Inherits from DecimalMatrix class.
 * Can perform a number of different vector operations, as well as transform itself using transformation matrices.
 * @author Max Thurell
 * @version 1.0
 */
public class Vector extends DecimalMatrix {

    /**
     * Gets the value of the first element in the vector, representing its x coordinate in a Cartesian coordinate system.
     * @return X value.
     */
    public Double getX() { return getElement(0);}

    /**
     * Gets the value of the second element in the vector, representing its y coordinate in a Cartesian coordinate system.
     * @return Y value.
     */
    public Double getY() { return getElement(1);}


    public Double getElement(int index) { return data[index][0]; }
    public void setElement(int i, Double val) { data[i][0] = val; }

    /*--------------------CONSTRUCTORS----------------------------------------*/

    /**
     * Creates an empty Vector of indicated length.
     *
     * @param M Number of elements in Vector.
     */
    Vector(int M)
    {
        super(M, 1);
    }

    /**
     * Creates a vector from received values, with a length equal to the length of the value array.
     *
     * @param values Array of values to be held by the Vector.
     */
    public Vector(Double... values)
    {
        super(values);

        data = new double[M][N];
        for (int i = 0; i < values.length; i++) data[i][0] = values[i];
    }

    /*--------------------------------------------------------------------------------*/

    /**
     * Creates a 2x2 Matrix with the received values. Can be used as a transformation matrix for two-dimensional Vectors.
     *
     * @param x1 First x value.
     * @param x2 Second x value.
     * @param y1 First y value.
     * @param y2 Second y value.
     * @return 2x2 matrix containing x1, x2, y1, and y2.
     */
    private static DecimalMatrix make2DMatrix(double x1, double x2, double y1, double y2)
    {
        double[][] matrixData = {{x1, x2}, {y1, y2}};

        return new DecimalMatrix(matrixData);
    }

    /**
     * Override of equals method.
     * Returns true if there is an equivalence relation between this vector and the object received.
     *
     * @param object Object to compare.
     * @return true if this Vector is equal to object, false if not.
     */
    public boolean equals(Object object)
    {
        if (!(object instanceof Vector)) return false;
        Vector v1 = this;
        Vector v2 = (Vector) object;
        for (int i = 0; i < data.length; i++) if (!v1.getElement(i).equals(v2.getElement(i))) return false;
        return true;
    }

    /**
     * Creates a unique hashcode for this Vector.
     * The condition (a.equals(b) implies a.hashCode() == b.hashCode()) must be fulfilled.
     *
     * @return The hashcode for this Vector object.
     */
    public int hashCode()
    {
        int hash = 3;
        for (double val : data[0]) hash = 31 * hash + (int) val;
        return hash;
    }

    /*--------------------VECTOR OPERATIONS----------------------------------------*/

    /**
     * Gets the magnitude of this vector (defined as the square root of the sum of the squares of all vector elements).
     *
     * @return Magnitude of this Vector.
     */

    private double getMagnitude()
    {
        double magnitude = 0;
        for (double[] val : data) magnitude += Math.pow(val[0], 2);

        return Math.sqrt(magnitude);
    }

    /**
     * Gets the dot product of this Vector with another Vector
     * (defined as the sum of the products of all values at corresponding elements).
     *
     * @param v2 Vector with which to multiply this.
     * @return Dot product of two vectors.
     */
    private double getDotProduct(Vector v2)
    {
        Vector v1 = this;
        if (v1.getData().length != v2.getData().length) throw new IllegalArgumentException("Vectors not equal length!");

        double dotProduct = 0;

        for (int i = 0; i < v1.data.length; i++) dotProduct += v1.getElement(i) * v2.getElement(i);

        return dotProduct;
    }


    /**
     * Gets the (signed) angle between two two-dimensional vectors,
     * seen from a position orthogonal to the x-and-y-axes on a Cartesian plane.
     *
     * @param v2 Vector to find angle to.
     * @return Angle between two vectors.
     */
    public double get2DAngle(Vector v2)
    {
        Vector v1 = this;
        double dot = v1.getDotProduct(v2);
        v2.show();
        double det = v1.concatenateVector(v2).getDeterminant();
        return Math.atan2(det, dot);
    }


    /**
     * Gets the general angle between two vectors of arbitrary (but equal) dimensions.
     * Defined by the arccos value of the dot product of the matrices divided by the product of the magnitudes.
     *
     * @param v2 Vector to find angle to.
     * @return Angle between two vectors.
     */
    public double getAngle(Vector v2)
    {
        Vector v1 = this;

        double dotProduct = v1.getDotProduct(v2);
        double v1Mag = v1.getMagnitude();
        double v2Mag = v2.getMagnitude();

        return Math.acos(dotProduct / (v1Mag * v2Mag));
    }

    /**
     * Performs a transformation on this Vector, using a transformation matrix.
     *
     * @param transformationMatrix Transformation matrix by which to multiply this Vector.
     * @return This Vector, transformed by the transformation matrix.
     */
    private Vector transform(DecimalMatrix transformationMatrix)
    {
        if (transformationMatrix.getN() != transformationMatrix.getM()) throw new IllegalArgumentException("Transformation doesn't preserve vector dimensions!");

        return (Vector) transformationMatrix.matrixMult(this);
    }

    /*--------------------TRANSFORMATION METHODS--------------------------------------------------*/
    /* All transformation methods utilise transformation matrices to remap vectors.*/

    /*--------------------SCALING METHODS--------------------------------------------------*/

    /**
     * Scales this Vector up by the received width and height values.
     *
     * @param width The factor by which to scale the Vector's x value.
     * @param height The factor by which to scale the Vector's y value.
     * @return This Vector, scaled by given x-and-y values.
     */
    private Vector scale(double width, double height)
    {
        DecimalMatrix scalingMatrix = make2DMatrix(width, 0, 0, height);
        return transform(scalingMatrix);
    }

    /**
     * Scales the x value of this Vector up by the received scalar.
     *
     * @param scalar The scalar by which to scale the Vector.
     * @return This Vector, scaled along the x axis by the given scalar.
     */
    public Vector scaleX(double scalar)
    {
        return scale(scalar, 1);
    }

    /**
     * Scales the y value of this Vector up by the received scalar.
     *
     * @param scalar The scalar by which to scale the Vector.
     * @return This Vector, scaled along the y axis by the given scalar.
     */
    public Vector scaleY(double scalar)
    {
        return scale(1, scalar);
    }

    /**
     * Scales the y and x values of this Vector up by the received scalar.
     *
     * @param scalar The scalar by which to scale the Vector.
     * @return This Vector, scaled along both axes by the given scalar.
     */
    public Vector scaleProportional(double scalar)
    {
        return scale(scalar, scalar);
    }

    /*--------------------REFLECTION METHODS--------------------------------------------------*/

    /**
     * Reflects this Vector around the x axis, by changing the sign of its y coordinate.
     *
     * @return This Vector, reflected around the x axis.
     */
    public Vector reflect2DX()
    {
        DecimalMatrix reflectionMatrix = make2DMatrix(1, 0, 0, -1);
        return transform(reflectionMatrix);
    }

    /**
     * Reflects this Vector around the y axis, by changing the sign of its x coordinate.
     *
     * @return This Vector, reflected around the y axis.
     */
    public Vector reflect2DY()
    {
        DecimalMatrix reflectionMatrix = make2DMatrix(-1, 0, 0, 1);
        return transform(reflectionMatrix);
    }

    /**
     * Reflects this Vector around both axes, by changing the sign of both of its coordinates.
     *
     * @return This Vector, reflected around the x axis.
     */
    public Vector reflect2DOrigin()
    {
        DecimalMatrix reflectionMatrix = make2DMatrix(-1, 0, 0, -1);
        return transform(reflectionMatrix);
    }

    /*--------------------ROTATION METHODS--------------------------------------------------*/

    /**
     * Rotates this Vector around the origin point, by the given angle and in the given direction.
     *
     * @param angle Angle by which to rotate, expressed in radians.
     * @param isClockWise Whether to rotate Vector clockwise or counter-clockwise.
     * @return This Vector, rotated by a given angle in a given direction.
     */
    public Vector rotate2D(Double angle, boolean isClockWise)
    {
        if (M != 2) throw new IllegalArgumentException("Vector not member of R^2");

        DecimalMatrix rotationMatrix;

        if (isClockWise) rotationMatrix = make2DMatrix(Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle));
        else rotationMatrix = make2DMatrix(Math.cos(angle), -Math.sin(angle), Math.sin(angle), Math.cos(angle));

        return transform(rotationMatrix);
    }

    /*--------------------SHEAR METHODS--------------------------------------------------*/

    /**
     * Performs a shear mapping on this Vector by replacing an element in the identity matrix by a scalar and
     * transforming the vector with the resulting matrix.
     * A shear mapping displaces a point along one axis based on its signed distance from a line parallel to that axis.
     *
     * @param matrixDim Dimensions of identity matrix for transformation.
     * @param i Row index of element to be replaced (this indicates the axis along which the point will be displaced).
     * @param j Column index of element to be replaced (this indicates which axis value will dictate the magnitude of displacement).
     * @param scalar Replaces a zero-value element in the identity matrix and dictates the multiplicative size of displacement.
     * @return This Vector, shear mapped in the i dimension based on its position in the j dimension.
     */
    private Vector shear(int matrixDim, int i, int j, double scalar)
    {
        DecimalMatrix shearMatrix = DecimalMatrix.Identity(matrixDim);
        shearMatrix.setElement(i, j, scalar);

        return transform(shearMatrix);
    }

    /**
     * Performs a shear mapping of a point along the x axis, based on its signed distance from the x axis (equivalent to its y coordinate).
     *
     * @param scalar Replaces a zero-value element in the identity matrix and dictates the multiplicative size of displacement.
     * @return This Vector, shear mapped along the x axis.
     */
    public Vector shear2DX(double scalar) { return shear(2, 0, 1, scalar); }

    /**
     * Performs a shear mapping of a point along the y axis, based on its signed distance from the y axis (equivalent to its x coordinate).
     *
     * @param scalar Replaces a zero-value element in the identity matrix and dictates the multiplicative size of displacement.
     * @return This Vector, shear mapped along the x axis.
     */
    public Vector shear2DY(double scalar)
    {
        return shear(2, 1, 0, scalar);
    }

    /*------------------------------------------------------------------------------------------*/

    /**
     * Prints the contents of this Vector to the console. Vector must be two-dimensional.
     */
    public void show() { System.out.println(getX() + ", " + getY()); }
}
