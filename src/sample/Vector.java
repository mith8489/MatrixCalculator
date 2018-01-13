package sample;

public class Vector extends Matrix {

    public Vector(int M)
    {
        super(M, 1);
    }

    public Vector(Double... values)
    {
        M = values.length;
        N = 1;
        data = new double[M][N];
        for (int i = 0; i < values.length; i++)
        {
            data[i][0] = values[i];
        }
    }

    public Double getElement(int index)
    {
        return data[index][0];
    }

    public void setElement(int i, Double val)
    {
        data[i][0] = val;
    }

    private Matrix make2DMatrix(double x1, double x2, double y1, double y2)
    {
        double[][] matrixData = {{x1, x2}, {y1, y2}};

        return new Matrix(matrixData);
    }

    public Vector transform(Matrix transformationMatrix)
    {
        if (transformationMatrix.getN() != transformationMatrix.getM()) throw new IllegalArgumentException("Transformation doesn't preserve vector dimensions!");

        Vector mappedVector = (Vector) transformationMatrix.matrixMult(this);
        mappedVector.show();
        return mappedVector;
    }

    /**--------------------SCALING METHODS--------------------------------------------------*/

    public Vector scale(int width, int height)
    {
        Matrix scalingMatrix = make2DMatrix(width, 0, 0, height);
        return transform(scalingMatrix);
    }

    public Vector scaleProportional(int scalar)
    {
        return scale(scalar, scalar);
    }

    /**--------------------REFLECTION METHODS--------------------------------------------------*/

    public Vector reflectX()
    {
        Matrix reflectionMatrix = make2DMatrix(1, 0, -1, 0);
        return transform(reflectionMatrix);
    }

    public Vector reflectY()
    {
        Matrix reflectionMatrix = make2DMatrix(-1, 0, 1, 0);
        return transform(reflectionMatrix);
    }

    public Vector reflectOrigin()
    {
        Matrix reflectionMatrix = make2DMatrix(-1, 0, -1, 0);
        return transform(reflectionMatrix);
    }

    /**--------------------ROTATION METHODS--------------------------------------------------*/

    public Vector rotate2D(Double angle, boolean isCounterClockWise)
    {
        if (M != 2) throw new IllegalArgumentException("Vector not member of R^2");

        Matrix rotationMatrix;

        if (isCounterClockWise)
        {
            rotationMatrix = make2DMatrix(Math.cos(angle), Math.sin(angle), -Math.sin(angle), Math.cos(angle));
        }
        else
        {
            rotationMatrix = make2DMatrix(Math.cos(angle), -Math.sin(angle), Math.sin(angle), Math.cos(angle));
        }

        return transform(rotationMatrix);
    }

    /**--------------------SHEAR METHODS--------------------------------------------------*/

    public Vector shear2DX(int scalar)
    {
        return shear(2, 0, 1, scalar);
    }

    public Vector shear2DY(int scalar)
    {
        return shear(2, 1, 0, scalar);
    }

    private Vector shear(int matrixDim, int i, int j, int scalar)
    {
        Matrix shearMatrix = Matrix.Identity(matrixDim);
        shearMatrix.setElement(i, j, scalar);

        return transform(shearMatrix);
    }
}
