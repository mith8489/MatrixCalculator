package maths;

public interface MatrixOperational {

    public Matrix add(Matrix B);

    public Matrix subtract(Matrix B);

    public Matrix scalarMult(int scalar);

    public Matrix matrixMult(Matrix B);

    public Matrix transpose();

    public Matrix gaussJordanEliminate(boolean isAugmented);

    public Matrix invert();

    public double getDeterminant();

    public int getRank();
}
