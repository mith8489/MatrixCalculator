package sample;

import java.text.DecimalFormat;

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

    public double getElement(int i, int j)
    {
        return data[i][j];
    }

    /**--------------------CONSTRUCTORS--------------------------------------------------*/

    /**
     * Creates an empty Matrix of given dimensions.
     * @param M :: Number of rows.
     * @param N :: Number of columns.
     */
    public Matrix(int M, int N)
    {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    /**
     * Creates a matrix around an existing 2D array.
     * @param data 2D array containing values.
     */
    public Matrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                this.data[i][j] = data[i][j];
    }

    /**
     * Creates an identity matrix.
     * @param n :: Rows and columns of the identity matrix.
     * @return n-Identity matrix.
     */
    public static Matrix identityMatrix(int n)
    {
        Matrix identityMatrix = new Matrix(n, n);

        for (int i = 0; i < n; i++)
        {
            identityMatrix.setElement(i, i, 1);
        }
        return identityMatrix;
    }

    /**--------------------ROW OPERATIONS--------------------------------------------------*/


    /**
     * Swaps two rows in a matrix.
     * @param i :: First row to be swapped.
     * @param j :: Second row to be swapped.
     */
    public void swapRows (int i, int j)
    {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * Subtracts a multiple of one row from another row.
     * @param i Row to subtract from.
     * @param j Row to subtract.
     * @param scalar Factor to multiply the j row by before subtraction.
     */
    public void subtractRows(int i, int j, double scalar)
    {
        for (int k = 0; k < N; k++)
        {
            data[i][k] = data[i][k] - data[j][k] * scalar;
        }
    }

    /**
     * Divides a row by a given value.
     * @param i Row to divide.
     * @param divisor Divisor in the division.
     */
    private void divideRow(int i, double divisor)
    {
        for (int j = 0; j < N; j++)
        {
            data[i][j] = data[i][j] / divisor;
        }
    }

    /**--------------------BASIC MATRIX OPERATIONS--------------------------------------------------*/

    /**
     * Adds another matrix to this matrix.
     * @param matrixB :: The matrix to be added to this one.
     * @return Resultant matrix of addition.
     */
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

    /**
     * Subtracts another matrix from this matrix.
     * @param matrixB :: The matrix to be subtracted from this one.
     * @return Resultant matrix of subtraction.
     */
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

    /**
     * Performs scalar multiplication on this matrix (all elements multiplied individually by scalar).
     * @param scalar :: Scalar by which to multiply.
     * @return Resultant matrix of scalar multiplication.
     */
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

    /**
     * Performs matrix multiplication with this matrix and another matrix.
     * @param matrixB Second matrix in the multiplication (this × matrixB).
     * @return Resultant matrix of matrix multiplication.
     */
    public Matrix matrixMult(Matrix matrixB) {
        Matrix matrixA = this;

        if (matrixA.getN() != matrixB.getM()) throw new IllegalArgumentException("Incompatible matrix dimensions!");

        Matrix matrixC = new Matrix(matrixA.getM(), matrixB.getN());

        for (int i = 0; i < matrixA.getM(); i++)
        {

            for (int j = 0; j < matrixB.getN(); j++)
            {
                double newCellValue = 0;

                for (int k = 0; k < matrixB.getM(); k++)
                {
                    newCellValue += matrixA.getData()[i][k] * matrixB.getData()[k][j];
                }

                matrixC.setElement(i, j, newCellValue);
            }
        }
        return matrixC;
    }

    /**--------------------GAUSS-JORDAN METHODS--------------------------------------------------*/

    /**
     * Sets the next pivot element, either through row swapping or through row division.
     * @param i :: Row-and-column index of the next pivot element.
     */
    private void setPivotElement(int i)
    {
        if (!(data[i][i] == 1))
        {
            for (int j = i + 1; j < M; j++)
            {
                if (data[j][i] == 1)
                {
                    swapRows(i, j);
                    break;
                }
            }
            if (data[i][i] == 0)
            {
                for (int j = i + 1; j < M; j++)
                {
                    if (data[j][i] != 0)
                    {
                        swapRows(i, j);
                        break;
                    }
                }
            }
            divideRow(i, data[i][i]);
        }
    }

    /**
     * Performs a Gauss-Jordan elimination on an augmented matrix.
     * @return :: Reduced row echelon form of the given matrix.
     */
    public Matrix gaussJordanEliminate()
    {
        Matrix matrix = this;
        for (int i = 0; i < (matrix.getN() - 1); i++)
        {
            System.out.println("SOLVING ROW " + i);
            System.out.println("----------------");
            matrix.show();
            if (!(i >= matrix.getM())) matrix.setPivotElement(i);
            else break;
            matrix.show();
            for (int j = 0; j < matrix.getM(); j++)
            {
                matrix.show();
                if (j != i)
                {
                    matrix.subtractRows(j, i, matrix.getData()[j][i]);
                }
            }
        }
        matrix.show();
        return matrix;
    }

    /**
     *
     */
    public Matrix transpose()
    {
        Matrix transposedMatrix = new Matrix(N, M);

        for (int i = 0; i < M; i++)
        {
            for (int j = 0; j < N; j++)
            {
                transposedMatrix.setElement(j, i, data[i][j]);
            }
        }
        return transposedMatrix;
    }

    /**--------------------INVERSION METHODS--------------------------------------------------*/

    /**
     * Sets the next pivot element, either through row swapping or through row division.
     * @param i :: Row-and-column index of the next pivot element.
     */

    /**
     *
     * @return
     */
    public Matrix invert()
    {
        if (M != N) throw new IllegalArgumentException("Matrix not invertible!");
        Matrix augmentedMatrix = this.augmentWithIdentity();

        return augmentedMatrix.gaussJordanEliminate();
    }

    public Matrix augmentWithIdentity()
    {
        Matrix augmentedMatrix = new Matrix(M, N*2);
        System.out.println(augmentedMatrix.getM());
        System.out.println(augmentedMatrix.getN());
        Matrix identityMatrix = Matrix.identityMatrix(M);

        for (int i = 0; i < M; i++)
        {
            for (int j = 0; j < N; j++)
            {
                augmentedMatrix.setElement(i, j, data[i][j]);
            }
            for (int j = N; j < N * 2; j++)
            {
                augmentedMatrix.setElement(i, j, identityMatrix.getData()[i][j - N]);
            }
        }
        augmentedMatrix.show();
        return augmentedMatrix;
    }

    /**
     * Prints this matrix to the console.
     */
    public void show() {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(new DecimalFormat("#.##").format(data[i][j]) + " ");
            System.out.println();
        }
        System.out.println("----------------");
    }


}
