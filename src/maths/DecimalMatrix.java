package maths;

import java.text.DecimalFormat;

public class DecimalMatrix extends Matrix {

    protected int M;
    protected int N;

    protected double[][] data;

    /**
     * --------------------GETTER/SETTER METHODS--------------------------------------------------
     */

    public int getM() {
        return M;
    }

    public int getN() {
        return N;
    }

    public void setM(int m) {
        M = m;
    }

    public void setN(int n) {
        N = n;
    }

    public double[][] getData() {
        return data;
    }

    public void setData(double[][] data) {
        this.data = data;
    }

    public void setElement(int i, int j, double val) {
        data[i][j] = val;
    }

    public double getElement(int i, int j) {
        return data[i][j];
    }

    /**--------------------CONSTRUCTORS--------------------------------------------------*/

    public DecimalMatrix(){} //Default constructor for inheritance

    /**
     * Creates an empty DecimalMatrix of given dimensions.
     *
     * @param M :: Number of rows.
     * @param N :: Number of columns.
     */
    public DecimalMatrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new double[M][N];
    }

    /**
     * Creates a matrix around an existing 2D array.
     *
     * @param data 2D array containing values.
     */
    public DecimalMatrix(double[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new double[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                this.data[i][j] = data[i][j];
    }

    /**
     * Creates an identity matrix.
     *
     * @param n :: Rows and columns of the identity matrix.
     * @return n-Identity matrix.
     */
    public static DecimalMatrix Identity(int n) {
        DecimalMatrix identityMatrix = new DecimalMatrix(n, n);

        for (int i = 0; i < n; i++) {
            identityMatrix.setElement(i, i, 1);
        }
        return identityMatrix;
    }

    /**--------------------MATRIX BUILDING METHODS--------------------------------------------------*/

    public void addData(double[][] data)
    {
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                this.data[i][j] = data[i][j];
    }

    public DecimalMatrix concatenateVector(Vector vector)
    {
        if (vector.getM() != M) throw new IllegalArgumentException("Incompatible vector dimensions");
        DecimalMatrix newMatrix = new DecimalMatrix(M, N + 1);
        newMatrix.addData(data);
        for (int i = 0; i < M; i++)
        {
            newMatrix.setElement(i, N, vector.getElement(i));
        }

        return newMatrix;
    }

    public static DecimalMatrix buildFromVectors(Vector... vectors)
    {
        DecimalMatrix matrix = vectors[0];

        for (int i = 1; i < vectors.length; i++)
        {
            matrix = matrix.concatenateVector(vectors[i]);
        }

        return matrix;
    }

    public Vector extractVector(int j)
    {
        Vector vector = new Vector(M);

        for (int i = 0; i < M; i++)
        {
            vector.setElement(i, getElement(i, j));
        }

        return vector;
    }

    /**--------------------ROW OPERATIONS--------------------------------------------------*/


    /**
     * Swaps two rows in a matrix.
     *
     * @param i :: First row to be swapped.
     * @param j :: Second row to be swapped.
     */
    public void swapRows(int i, int j) {
        double[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * Subtracts a multiple of one row from another row.
     *
     * @param i      Row to subtract from.
     * @param j      Row to subtract.
     * @param scalar Factor to multiply the j row by before subtraction.
     */
    public void addRows(int i, int j, double scalar) {
        for (int k = 0; k < N; k++) {
            data[i][k] = data[i][k] + data[j][k] * scalar;
        }
    }

    /**
     * Divides a row by a given value.
     *
     * @param i       Row to divide.
     * @param divisor Divisor in the division.
     */
    private void divideRow(int i, double divisor) {
        for (int j = 0; j < N; j++) {
            data[i][j] = data[i][j] / divisor;
        }
    }

    /**--------------------BASIC MATRIX OPERATIONS--------------------------------------------------*/

    /**
     * Adds another matrix to this matrix.
     *
     * @param B :: The matrix to be added to this one.
     * @return Resultant matrix of addition.
     */
    public DecimalMatrix add(DecimalMatrix B) {
        DecimalMatrix A = this;

        if (B.getM() != A.getM() || B.getN() != A.getN())
            throw new IllegalArgumentException("Incompatible matrix dimensions!");

        DecimalMatrix C = new DecimalMatrix(M, N);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                C.setElement(i, j, A.getData()[i][j] + B.getData()[i][j]);
            }
        }

        return C;
    }

    /**
     * Subtracts another matrix from this matrix.
     *
     * @param B :: The matrix to be subtracted from this one.
     * @return Resultant matrix of subtraction.
     */
    public DecimalMatrix subtract(DecimalMatrix B) {
        DecimalMatrix A = this;

        if (B.getM() != A.getM() || B.getN() != A.getN())
            throw new IllegalArgumentException("Incompatible matrix dimensions!");

        DecimalMatrix C = new DecimalMatrix(M, N);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                C.setElement(i, j, A.getData()[i][j] - B.getData()[i][j]);
            }
        }

        return C;
    }

    /**
     * Performs scalar multiplication on this matrix (all elements multiplied individually by scalar).
     *
     * @param scalar :: Scalar by which to multiply.
     * @return Resultant matrix of scalar multiplication.
     */
    public DecimalMatrix scalarMult(int scalar) {
        DecimalMatrix B = new DecimalMatrix(M, N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                B.getData()[i][j] = data[i][j] * scalar;
            }
        }
        return B;
    }

    /**
     * Gets the transpose of this matrix (row and column indices swapped).
     *
     * @return Transpose of this matrix.
     */
    public DecimalMatrix transpose() {
        DecimalMatrix transposedMatrix = new DecimalMatrix(N, M);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                transposedMatrix.setElement(j, i, data[i][j]);
            }
        }
        return transposedMatrix;
    }

    /**
     * Performs matrix multiplication with this matrix and another matrix.
     *
     * @param B Second matrix in the multiplication (this × B).
     * @return Resultant matrix of matrix multiplication.
     */
    public DecimalMatrix matrixMult(DecimalMatrix B) {
        DecimalMatrix A = this;

        if (A.getN() != B.getM()) throw new IllegalArgumentException("Incompatible matrix dimensions!");

        DecimalMatrix C;
        if (B instanceof Vector) C = new Vector(A.getM());
        else                           C = new DecimalMatrix(A.getM(), B.getN());


        for (int i = 0; i < A.getM(); i++) {

            for (int j = 0; j < B.getN(); j++) {
                double newCellValue = 0;

                for (int k = 0; k < B.getM(); k++) {
                    newCellValue += A.getData()[i][k] * B.getData()[k][j];
                }

                C.setElement(i, j, newCellValue);
            }
        }
        return C;
    }

    public void swap(DecimalMatrix B) {
        DecimalMatrix tempMatrix = new DecimalMatrix(getData());
        setData(B.getData());
        setM(data.length);
        setN(data[0].length);
        B.setData(tempMatrix.getData());
        B.setM(B.getData().length);
        B.setN(B.getData()[0].length);
    }

    /**--------------------GAUSS-JORDAN METHODS--------------------------------------------------*/

    /**
     * Sets the next pivot element, either through row swapping or through row division.
     *
     * @param i :: Row-and-column index of the next pivot element.
     */
    private void setPivotElement(int i) {
        if (!(data[i][i] == 1)) {
            for (int j = i + 1; j < M; j++) {
                if (data[j][i] == 1) {
                    swapRows(i, j);
                    show();
                    break;
                }
            }
            if (data[i][i] == 0) {
                for (int j = i + 1; j < M; j++) {
                    if (data[j][i] != 0) {
                        swapRows(i, j);
                        show();
                        break;
                    }
                }
            }
            if (data[i][i] != 0) {
                divideRow(i, data[i][i]);
                show();
            }
        }
    }

    /**
     * Performs a Gauss-Jordan elimination on an augmented matrix.
     *
     * @return :: Reduced row echelon form of the given matrix.
     */
    public DecimalMatrix gaussJordanEliminate(boolean isAugmented) {
        int columnReduction = 0;
        if (isAugmented) columnReduction = 1;
        DecimalMatrix matrix = this;
        for (int i = 0; i < (matrix.getN() - columnReduction); i++) {
            System.out.println("SOLVING ROW " + (i + 1));
            System.out.println("----------------");
            matrix.show();
            if (!(i >= matrix.getM())) {
                matrix.setPivotElement(i);
            } else break;
            for (int j = 0; j < matrix.getM(); j++) {
                if (j != i) {
                    matrix.addRows(j, i, -matrix.getData()[j][i]);
                    matrix.show();
                }
            }
        }
        return matrix;
    }

    /**--------------------INVERSION METHODS--------------------------------------------------*/

    /**
     * @return
     */
    public DecimalMatrix invert() {
        if (M != N) throw new IllegalArgumentException("DecimalMatrix not invertible!");
        DecimalMatrix augmentedMatrix = this.augmentWithIdentity();

        return augmentedMatrix.gaussJordanEliminate(true);
    }


    public DecimalMatrix augmentWithIdentity() {
        DecimalMatrix augmentedMatrix = new DecimalMatrix(M, N * 2);
        System.out.println(augmentedMatrix.getM());
        System.out.println(augmentedMatrix.getN());
        DecimalMatrix identityMatrix = DecimalMatrix.Identity(M);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {

                augmentedMatrix.setElement(i, j, data[i][j]);
            }
            for (int j = N; j < N * 2; j++) {
                augmentedMatrix.setElement(i, j, identityMatrix.getData()[i][j - N]);
            }
        }
        augmentedMatrix.show();
        return augmentedMatrix;
    }

    /**--------------------DETERMINANT METHODS--------------------------------------------------*/

    /**
     * Finds the determinant of a square matrix.
     *
     * @return Determinant of matrix.
     */
    public double getDeterminant() {
        if (M != N) throw new IllegalArgumentException("DecimalMatrix not square!");
        double determinant = 0;

        if (M == 1) determinant = data[0][0];
        if (M == 2) determinant = (data[0][0] * data[1][1]) - (data[0][1] * data[1][0]);
        else {
            for (int i = 0; i < N; i++) {
                double minorMatrixDet = getMinorMatrix(i).getDeterminant();
                double newVal = data[0][i] * minorMatrixDet;

                if (i % 2 == 0) {
                    System.out.println("i: " + i + ", adding " + newVal);
                    determinant += data[0][i] * minorMatrixDet;
                } else {
                    System.out.println("i: " + i + ", subtracting " + newVal);
                    determinant -= data[0][i] * minorMatrixDet;
                }
            }
        }
        return determinant;
    }

    /**
     * Gets the minor matrix of this matrix, with the top row and column i removed.
     *
     * @param i Index of column to remove.
     * @return Minor matrix.
     */
    public DecimalMatrix getMinorMatrix(int i) {
        DecimalMatrix minorMatrix = new DecimalMatrix(M - 1, N - 1);
        for (int j = 0; j < N; j++) {
            if (j != i) {
                for (int k = 1; k < M; k++) {
                    if (j < i) minorMatrix.setElement(k - 1, j, data[k][j]);
                    if (j > i) minorMatrix.setElement(k - 1, j - 1, data[k][j]);
                }
            }
        }
        return minorMatrix;
    }

    /**
     * --------------------RANK METHODS--------------------------------------------------
     */

    public int getRank() {
        DecimalMatrix matrix = new DecimalMatrix(data);
        DecimalMatrix reducedRowEchelonMatrix = matrix.gaussJordanEliminate(false);

        int rank = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (reducedRowEchelonMatrix.data[i][j] != 0) {
                    rank++;
                    break;
                }
            }
        }
        return rank;
    }

    /**--------------------------------------------------------------------------------------*/
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
