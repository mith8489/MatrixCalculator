package sample;

public class Matrix {

    private int M;
    private int N;

    private double[][] data;

    /**--------------------GETTER/SETTER METHODS--------------------------------------------------*/

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public double[][] getData() {
        return data;
    }

    public void setElement(int i, int j, double val)
    {
        data[i][j] = val;
    }

    /**--------------------CONSTRUCTORS--------------------------------------------------*/

    public Matrix(int M, int N)
    {
        data = new double[M][N];
    }

    public static Matrix identityMatrix(int n)
    {
        Matrix identityMatrix = new Matrix(n, n);

        for (int i = 0; i < n; i++)
        {
            identityMatrix.setElement(i, i, 1);
        }
        return identityMatrix;
    }

    public Matrix plus(Matrix matrixB)
    {
        Matrix matrixA = this;

        if (matrixB.getM() != matrixA.getM() || matrixB.getN() != matrixA.getN()) throw new IllegalArgumentException("Incompatible matrix dimensions!");

        Matrix matrixC = new Matrix(M, N);

        for (int i = 0; i < M; i++)
        { for (int j = 0; j < N; j++)
            {
                matrixC.setElement(i, j, matrixA.getData()[i][j] + matrixB.getData()[i][j]);
            }
        }

        return matrixC;
    }

    public Matrix minus(Matrix matrixB)
    {
        Matrix matrixA = this;

        if (matrixB.getM() != matrixA.getM() || matrixB.getN() != matrixA.getN()) throw new IllegalArgumentException("Incompatible matrix dimensions!");

        Matrix matrixC = new Matrix(M, N);

        for (int i = 0; i < M; i++)
        { for (int j = 0; j < N; j++)
        {
            matrixC.setElement(i, j, matrixA.getData()[i][j] - matrixB.getData()[i][j]);
        }
        }

        return matrixC;
    }

    public Matrix scalarMult(int scalar)
    {
        Matrix matrixB = new Matrix(M, N);
        for (int i = 0; i < M; i++)
        { for (int j = 0; j < N; j++)
        {
            matrixB.getData()[i][j] = data[i][j] * scalar;
        }
        }
        return matrixB;
    }


}
