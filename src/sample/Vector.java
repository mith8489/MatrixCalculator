package sample;

public class Vector extends Matrix {

    public Double getX() { return getElement(0);};
    public Double getY() { return getElement(1);};
    public void setX(double value) { data[0][0] = value;}
    public void setY(double value) { data[1][0] = value;}

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

    public static Matrix make2DMatrix(double x1, double x2, double y1, double y2)
    {
        double[][] matrixData = {{x1, x2}, {y1, y2}};

        return new Matrix(matrixData);
    }

    public boolean isEqualTo(Vector v2)
    {
        Vector v1 = this;
        for (int i = 0; i < data.length; i++)
        {
            if (!v1.getElement(i).equals(v2.getElement(i))) return false;
        }
        return true;
    }

    public double getMagnitude()
    {
        double magnitude = 0;
        for (double[] val : data)
        {
            magnitude += Math.pow(val[0], 2);
        }

        return Math.sqrt(magnitude);
    }

    public double getDotProduct(Vector v2)
    {
        Vector v1 = this;
        if (v1.getData().length != v2.getData().length) throw new IllegalArgumentException("Vectors not equal length!");

        double dotProduct = 0;

        for (int i = 0; i < v1.data.length; i++)
        {
            dotProduct += v1.getElement(i) * v2.getElement(i);
        }

        return dotProduct;
    }

    public double get2DAngle(Vector v2)
    {
        Vector v1 = this;
        double dot = v1.getDotProduct(v2);;
        double det = v1.concatenateVector(v2).getDeterminant();
        return Math.atan2(det, dot);
    }

    public double getAngle(Vector v2)
    {
        Vector v1 = this;

        double dotProduct = v1.getDotProduct(v2);
        double v1Mag = v1.getMagnitude();
        double v2Mag = v2.getMagnitude();


        double angle = Math.acos(dotProduct / (v1Mag * v2Mag));
        return angle;
    }

    public Vector transform(Matrix transformationMatrix)
    {
        if (transformationMatrix.getN() != transformationMatrix.getM()) throw new IllegalArgumentException("Transformation doesn't preserve vector dimensions!");

        Vector mappedVector = (Vector) transformationMatrix.matrixMult(this);
        return mappedVector;
    }

    /**--------------------SCALING METHODS--------------------------------------------------*/

    public Vector scale(double width, double height)
    {
        Matrix scalingMatrix = make2DMatrix(width, 0, 0, height);
        return transform(scalingMatrix);
    }

    public Vector scaleX(double scalar)
    {
        return scale(scalar, 1);
    }

    public Vector scaleY(double scalar)
    {
        return scale(1, scalar);
    }

    public Vector scaleProportional(double scalar)
    {
        return scale(scalar, scalar);
    }

    /**--------------------REFLECTION METHODS--------------------------------------------------*/

    public Vector reflect2DX()
    {
        Matrix reflectionMatrix = make2DMatrix(-1, 0, 0, 1);
        return transform(reflectionMatrix);
    }

    public Vector reflect2DY()
    {
        Matrix reflectionMatrix = make2DMatrix(1, 0, 0, -1);
        return transform(reflectionMatrix);
    }

    public Vector reflect2DOrigin()
    {
        Matrix reflectionMatrix = make2DMatrix(-1, 0, 0, -1);
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

    public Vector shear2DX(double scalar)
    {
        return shear(2, 0, 1, scalar);
    }

    public Vector shear2DY(double scalar)
    {
        return shear(2, 1, 0, scalar);
    }

    private Vector shear(int matrixDim, int i, int j, double scalar)
    {
        Matrix shearMatrix = Matrix.Identity(matrixDim);
        shearMatrix.setElement(i, j, scalar);

        return transform(shearMatrix);
    }

    public void show()
    {
        System.out.println(getX() + ", " + getY());
        System.out.println("________");
    }
}
