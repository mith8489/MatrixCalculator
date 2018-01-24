package maths;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.text.DecimalFormat;

public class FractionMatrix extends Matrix {

    protected int M;
    protected int N;

    protected Fraction[][] data;

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

    public Fraction[][] getData() {
        return data;
    }

    public void setData(Fraction[][] data) {
        this.data = data;
    }

    public void setElement(int i, int j, Fraction val) {
        data[i][j] = val;
    }

    public Fraction getElement(int i, int j) {
        return data[i][j];
    }

    /**--------------------CONSTRUCTORS--------------------------------------------------*/

    public FractionMatrix(){} //Default constructor for inheritance

    /**
     * Creates an empty FractionMatrix of given dimensions.
     *
     * @param M :: Number of rows.
     * @param N :: Number of columns.
     */
    public FractionMatrix(int M, int N) {
        this.M = M;
        this.N = N;
        data = new Fraction[M][N];
    }

    /**
     * Creates a matrix around an existing 2D array.
     *
     * @param data 2D array containing values.
     */
    public FractionMatrix(Fraction[][] data) {
        M = data.length;
        N = data[0].length;
        this.data = new Fraction[M][N];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                this.setElement(i, j, data[i][j]);
    }

    /**
     * Creates an identity matrix.
     *
     * @param n :: Rows and columns of the identity matrix.
     * @return n-Identity matrix.
     */
    public static FractionMatrix Identity(int n) {
        FractionMatrix identityMatrix = new FractionMatrix(n, n);

        for (int i = 0; i < n; i++) {
            identityMatrix.setElement(i, i, new Fraction(1));
        }
        return identityMatrix;
    }

    /**--------------------MATRIX BUILDING METHODS--------------------------------------------------*/

    public void addData(Fraction[][] data)
    {
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                this.setElement(i, j, data[i][j]);
    }

    /**--------------------ROW OPERATIONS--------------------------------------------------*/


    /**
     * Swaps two rows in a matrix.
     *
     * @param i :: First row to be swapped.
     * @param j :: Second row to be swapped.
     */
    public void swapRows(int i, int j) {
        Fraction[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    /**
     * Adds a multiple of one row to another row.
     *
     * @param i      Row to subtract from.
     * @param j      Row to subtract.
     * @param scalar Factor to multiply the j row by before subtraction.
     */
    public void addRows(int i, int j, Fraction scalar) {
        for (int k = 0; k < N; k++) {
            setElement(i, k, data[i][k].add(data[j][k].multiply(scalar)));
        }
    }

    /**
     * Divides a row by a given value.
     *
     * @param i       Row to divide.
     * @param divisor Divisor in the division.
     */
    private void divideRow(int i, Fraction divisor) {
        for (int j = 0; j < N; j++) {
            setElement(i, j, data[i][j].divide(divisor));
        }
    }

    /**--------------------BASIC MATRIX OPERATIONS--------------------------------------------------*/

    /**
     * Adds another matrix to this matrix.
     *
     * @param B :: The matrix to be added to this one.
     * @return Resultant matrix of addition.
     */
    public FractionMatrix add(FractionMatrix B) {
        FractionMatrix A = this;

        if (B.getM() != A.getM() || B.getN() != A.getN())
            throw new IllegalArgumentException("Incompatible matrix dimensions!");

        FractionMatrix C = new FractionMatrix(M, N);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                C.setElement(i, j, A.getData()[i][j].add(B.getData()[i][j]));
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
    public FractionMatrix subtract(FractionMatrix B) {
        FractionMatrix A = this;

        if (B.getM() != A.getM() || B.getN() != A.getN())
            throw new IllegalArgumentException("Incompatible matrix dimensions!");

        FractionMatrix C = new FractionMatrix(M, N);

        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                C.setElement(i, j, A.getData()[i][j].subtract(B.getData()[i][j]));
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
    public FractionMatrix scalarMult(int scalar) {
        FractionMatrix B = new FractionMatrix(M, N);
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                B.getData()[i][j] = data[i][j].multiply(new Fraction(scalar));
            }
        }
        return B;
    }

    /**
     * Gets the transpose of this matrix (row and column indices swapped).
     *
     * @return Transpose of this matrix.
     */
    public FractionMatrix transpose() {
        FractionMatrix transposedMatrix = new FractionMatrix(N, M);

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
    public FractionMatrix matrixMult(FractionMatrix B) {
        FractionMatrix A = this;

        if (A.getN() != B.getM()) throw new IllegalArgumentException("Incompatible matrix dimensions!");

        FractionMatrix C = new FractionMatrix(A.getM(), B.getN());


        for (int i = 0; i < A.getM(); i++) {

            for (int j = 0; j < B.getN(); j++) {
                Fraction newCellValue = new Fraction(0);

                for (int k = 0; k < B.getM(); k++) {
                    newCellValue = newCellValue.add(A.getData()[i][k].multiply(B.getData()[k][j]));
                }

                C.setElement(i, j, newCellValue);
            }
        }
        return C;
    }

    public void swap(FractionMatrix B) {
        FractionMatrix tempMatrix = new FractionMatrix(getData());
        setData(B.getData());
        setM(data.length);
        setN(data[0].length);
        B.setData(tempMatrix.getData());
        B.setM(B.getData().length);
        B.setN(B.getData()[0].length);
    }

    private boolean isZeroMatrix()
    {
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (!getElement(i, j).equalsZero()) return false;
            }
        }
        return true;
    }

    /**--------------------GAUSS-JORDAN METHODS--------------------------------------------------*/

    /**
     * Sets the next pivot element, either through row swapping or through row division.
     *
     * @param i :: Row-and-column index of the next pivot element.
     */
    private void setPivotElement(int i) {
        if (!(data[i][i].equalsOne())) {
            for (int j = i + 1; j < M; j++) {
                if (data[j][i].equalsOne()) {
                    swapRows(i, j);
                    show();
                    break;
                }
            }
            if (data[i][i].equalsZero()) {
                for (int j = i + 1; j < M; j++) {
                    if (!data[j][i].equalsZero()) {
                        swapRows(i, j);
                        show();
                        break;
                    }
                }
            }
            if (!data[i][i].equalsZero()) {
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
    public FractionMatrix gaussJordanEliminate(boolean isAugmented) {
        if (isZeroMatrix()) return this;
        int columnReduction = 0;
        if (isAugmented) columnReduction = 1;
        FractionMatrix matrix = this;
        for (int i = 0; i < (matrix.getN() - columnReduction); i++) {
            matrix.show();
            if (!(i >= matrix.getM())) {
                matrix.setPivotElement(i);
            } else
            {
                break;
            }
            for (int j = 0; j < matrix.getM(); j++) {
                if (j != i) {
                    matrix.addRows(j, i, matrix.getData()[j][i].negate());
                }
            }
        }
        return matrix;
    }

    /**--------------------INVERSION METHODS--------------------------------------------------*/

    /**
     * @return
     */
    public FractionMatrix invert() {
        if (M != N) throw new IllegalArgumentException("FractionMatrix not invertible!");
        FractionMatrix augmentedMatrix = this.augmentWithIdentity();

        return augmentedMatrix.gaussJordanEliminate(true);
    }


    public FractionMatrix augmentWithIdentity() {
        FractionMatrix augmentedMatrix = new FractionMatrix(M, N * 2);
        FractionMatrix identityMatrix = FractionMatrix.Identity(M);

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
    public Fraction getDeterminant() {
        if (M != N) throw new IllegalArgumentException("FractionMatrix not square!");
        Fraction determinant = new Fraction(0);

        if (M == 1) determinant = data[0][0];
        if (M == 2) determinant = (data[0][0].multiply(data[1][1])).subtract(data[0][1].multiply(data[1][0]));
        else {
            for (int i = 0; i < N; i++) {
                Fraction minorMatrixDet = getMinorMatrix(i).getDeterminant();
                Fraction newVal = data[0][i].multiply(minorMatrixDet);

                if (i % 2 == 0) {
                    determinant = determinant.add(data[0][i].multiply(minorMatrixDet));
                } else {
                    determinant = determinant.subtract(data[0][i].multiply(minorMatrixDet));
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
    public FractionMatrix getMinorMatrix(int i) {
        FractionMatrix minorMatrix = new FractionMatrix(M - 1, N - 1);
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
        FractionMatrix matrix = new FractionMatrix(data);
        FractionMatrix reducedRowEchelonMatrix = matrix.gaussJordanEliminate(false);

        int rank = 0;
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (!reducedRowEchelonMatrix.data[i][j].equalsZero()) {
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
                System.out.print(data[i][j].toString() + " ");
            System.out.println();
        }
        System.out.println("----------------");
    }


}
