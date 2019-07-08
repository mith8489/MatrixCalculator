package matrixops;

import base.Fraction;

public class FractionMatrix {
    private final int M;
    private final int N;
    private Fraction[][] data;

    public int getM() {
        return this.M;
    }

    public int getN() {
        return this.N;
    }

    private Fraction[][] getData() {
        return this.data;
    }

    public void setElement(int i, int j, Fraction val) {
        this.data[i][j] = val;
    }

    public Fraction getElement(int i, int j) {
        return this.data[i][j];
    }

    public FractionMatrix(int M, int N) {
        this.M = M;
        this.N = N;
        this.data = new Fraction[M][N];
    }

    private FractionMatrix(Fraction[][] data) {
        this.M = data.length;
        this.N = data[0].length;
        this.data = new Fraction[this.M][this.N];

        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                this.setElement(i, j, data[i][j]);
            }
        }

    }

    private static FractionMatrix Identity(int n) {
        FractionMatrix identityMatrix = new FractionMatrix(n, n);

        int i;
        for(i = 0; i < n; ++i) {
            identityMatrix.setElement(i, i, new Fraction(1));
        }

        for(i = 0; i < n; ++i) {
            for(int j = 0; j < n; ++j) {
                if (i == j) {
                    identityMatrix.setElement(i, i, new Fraction(1));
                } else {
                    identityMatrix.setElement(i, j, new Fraction(0));
                }
            }
        }

        return identityMatrix;
    }

    private void swapRows(int i, int j) {
        Fraction[] temp = this.data[i];
        this.data[i] = this.data[j];
        this.data[j] = temp;
    }

    private void addRows(int i, int j, Fraction scalar) {
        for(int k = 0; k < this.N; ++k) {
            this.setElement(i, k, this.data[i][k].add(this.data[j][k].multiply(scalar)));
        }

    }

    private void divideRow(int i, Fraction divisor) {
        for(int j = 0; j < this.N; ++j) {
            this.setElement(i, j, this.data[i][j].divide(divisor));
        }

    }

    public void zero() {
        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                this.data[i][j] = new Fraction(0);
            }
        }

    }

    public FractionMatrix add(FractionMatrix B) {
        FractionMatrix A = this;
        if (B.getM() == this.getM() && B.getN() == this.getN()) {
            FractionMatrix C = new FractionMatrix(this.M, this.N);

            for(int i = 0; i < this.M; ++i) {
                for(int j = 0; j < this.N; ++j) {
                    C.setElement(i, j, A.getData()[i][j].add(B.getData()[i][j]));
                }
            }

            return C;
        } else {
            throw new IllegalArgumentException("Incompatible matrix dimensions!");
        }
    }

    public FractionMatrix subtract(FractionMatrix B) {
        FractionMatrix A = this;
        if (B.getM() == this.getM() && B.getN() == this.getN()) {
            FractionMatrix C = new FractionMatrix(this.M, this.N);

            for(int i = 0; i < this.M; ++i) {
                for(int j = 0; j < this.N; ++j) {
                    C.setElement(i, j, A.getData()[i][j].subtract(B.getData()[i][j]));
                }
            }

            return C;
        } else {
            throw new IllegalArgumentException("Incompatible matrix dimensions!");
        }
    }

    public FractionMatrix scalarMult(Fraction scalar) {
        FractionMatrix B = new FractionMatrix(this.M, this.N);

        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                B.getData()[i][j] = this.data[i][j].multiply(scalar);
            }
        }

        return B;
    }

    public FractionMatrix transpose() {
        FractionMatrix transposedMatrix = new FractionMatrix(this.N, this.M);

        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                transposedMatrix.setElement(j, i, this.data[i][j]);
            }
        }

        return transposedMatrix;
    }

    public FractionMatrix matrixMult(FractionMatrix B) {
        FractionMatrix A = this;
        if (this.getN() != B.getM()) {
            throw new IllegalArgumentException("Incompatible matrix dimensions!");
        } else {
            FractionMatrix C = new FractionMatrix(this.getM(), B.getN());

            for(int i = 0; i < A.getM(); ++i) {
                for(int j = 0; j < B.getN(); ++j) {
                    Fraction newCellValue = new Fraction(0);

                    for(int k = 0; k < B.getM(); ++k) {
                        newCellValue = newCellValue.add(A.getData()[i][k].multiply(B.getData()[k][j]));
                    }

                    C.setElement(i, j, newCellValue);
                }
            }

            return C;
        }
    }

    private boolean isZeroMatrix() {
        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                if (!this.getElement(i, j).equalsZero()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void setPivotElement(int i) {
        if (!this.data[i][i].equalsOne()) {
            int j;
            for(j = i + 1; j < this.M; ++j) {
                if (this.data[j][i].equalsOne()) {
                    this.swapRows(i, j);
                    this.show();
                    break;
                }
            }

            if (this.data[i][i].equalsZero()) {
                for(j = i + 1; j < this.M; ++j) {
                    if (!this.data[j][i].equalsZero()) {
                        this.swapRows(i, j);
                        this.show();
                        break;
                    }
                }
            }

            if (!this.data[i][i].equalsZero()) {
                this.divideRow(i, this.data[i][i]);
                this.show();
            }
        }

    }

    public FractionMatrix gaussJordanEliminate(boolean isAugmented) {
        if (this.isZeroMatrix()) {
            return this;
        } else {
            int columnReduction = 0;
            if (isAugmented) {
                columnReduction = 1;
            }

            FractionMatrix matrix = this;

            for(int i = 0; i < matrix.getN() - columnReduction && i < matrix.getM(); ++i) {
                matrix.setPivotElement(i);

                for(int j = 0; j < matrix.getM(); ++j) {
                    if (j != i) {
                        matrix.addRows(j, i, matrix.getData()[j][i].negate());
                    }
                }
            }

            return matrix;
        }
    }

    public FractionMatrix invert() {
        if (this.M != this.N) {
            throw new IllegalArgumentException("com.test.mthur.matrix.FractionMatrix not invertible!");
        } else {
            FractionMatrix augmentedMatrix = this.augmentWithIdentity();
            return augmentedMatrix.gaussJordanEliminate(true);
        }
    }

    private FractionMatrix augmentWithIdentity() {
        FractionMatrix augmentedMatrix = new FractionMatrix(this.M, this.N * 2);
        FractionMatrix identityMatrix = Identity(this.M);

        for(int i = 0; i < this.M; ++i) {
            int j;
            for(j = 0; j < this.N; ++j) {
                augmentedMatrix.setElement(i, j, this.data[i][j]);
            }

            for(j = this.N; j < this.N * 2; ++j) {
                augmentedMatrix.setElement(i, j, identityMatrix.getData()[i][j - this.N]);
            }
        }

        augmentedMatrix.show();
        return augmentedMatrix;
    }

    public Fraction getDeterminant() {
        if (this.M != this.N) {
            throw new IllegalArgumentException("com.test.mthur.matrix.FractionMatrix not square!");
        } else {
            Fraction determinant = new Fraction(0);
            if (this.M == 1) {
                determinant = this.data[0][0];
            }

            if (this.M == 2) {
                determinant = this.data[0][0].multiply(this.data[1][1]).subtract(this.data[0][1].multiply(this.data[1][0]));
            } else {
                for(int i = 0; i < this.N; ++i) {
                    Fraction minorMatrixDet = this.getMinorMatrix(i).getDeterminant();
                    if (i % 2 == 0) {
                        determinant = determinant.add(this.data[0][i].multiply(minorMatrixDet));
                    } else {
                        determinant = determinant.subtract(this.data[0][i].multiply(minorMatrixDet));
                    }
                }
            }

            return determinant;
        }
    }

    private FractionMatrix getMinorMatrix(int i) {
        FractionMatrix minorMatrix = new FractionMatrix(this.M - 1, this.N - 1);

        for(int j = 0; j < this.N; ++j) {
            if (j != i) {
                for(int k = 1; k < this.M; ++k) {
                    if (j < i) {
                        minorMatrix.setElement(k - 1, j, this.data[k][j]);
                    }

                    if (j > i) {
                        minorMatrix.setElement(k - 1, j - 1, this.data[k][j]);
                    }
                }
            }
        }

        return minorMatrix;
    }

    public int getRank() {
        FractionMatrix matrix = new FractionMatrix(this.data);
        FractionMatrix reducedRowEchelonMatrix = matrix.gaussJordanEliminate(false);
        int rank = 0;

        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                if (!reducedRowEchelonMatrix.data[i][j].equalsZero()) {
                    ++rank;
                    break;
                }
            }
        }

        return rank;
    }

    public void show() {
        System.out.println("\n----------------");

        for(int i = 0; i < this.M; ++i) {
            for(int j = 0; j < this.N; ++j) {
                System.out.print(this.data[i][j].toString() + " ");
            }

            System.out.println();
        }

        System.out.println("----------------");
    }
}
