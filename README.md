# Matrix Calculator

The matrix calculator has two modes: Matrix operations and vector transformations.

## Matrix Operations

The matrix operations mode allows the user to quickly and intuitively perform all the common operations relevant to mathematical matrices, including the more cumbersome and time-consuming ones such as Gauss-Jordan elimination and determinant calculation.

Usage is simple and intuitive. Select an operation from the dropdown menu and fill in the input matrix/matrices (Matrix **A** and, for some operations, Matrix B).
Values in the matrices are to be expressed as fractions, with the numerator and denominator divided by a [slash](https://en.wikipedia.org/wiki/Slash_(punctuation)).
Single values without a divider are treated as having a denominator of 1. Empty elements are treated as having a value of 0 upon execution.

The dimensions of the input matrix/matrices can be edited at will. Note that the calculator will refuse to perform an operation if it is incompatible with the current input dimensions.

The Clear button clears all values in the input matrix/matrices. 

Carry out the operation with the Solve button. The solution appears in the third matrix (matrix **C**).

### Addition

[Matrix addition](https://en.wikipedia.org/wiki/Matrix_addition) takes two matrices **A** and **B** as input and produces a new matrix **C** where each element **c[i,j]** equals **a[i,j]** + **b[i,j]**. Because of this, the input matrices must have the same dimensions.

### Subtraction

[Matrix subtraction](https://en.wikipedia.org/wiki/Matrix_subtraction) takes two matrices **A** and **B** as input and produces a new matrix **C** where each element **c[i,j]** equals **a[i,j]** - **b[i,j]**. Because of this, the input matrices must have the same dimensions.

### Scalar Multiplication

[Scalar multiplication](https://en.wikipedia.org/wiki/Scalar_multiplication) takes a matrix **A** and a scalar **x** (treated as a 1x1 matrix by the calculator) and produces a new matrix **C** where each element **c[i,j]** equals **a[i,j]** * **x**. There are no limitations on the matrix dimensions.

### Matrix multiplication

[Matrix multiplication](https://en.wikipedia.org/wiki/Matrix_multiplication) produces the matrix product **C** of two matrices **A** and B.

To perform matrix multiplication: If **A** is an **n** x **m** matrix, **B** must be an **m** x **p** matrix. The matrix **C** produced is then an **n** x **p** matrix (in other words, the width of **A** and height of **B** must be equal, and **C** has the height of **A** and width of **B** - or vice versa).

Each element c[i,j] in **C** is defined by the sum of the multiplication of each element in the *i*th row of **A** with each element in the *j*th column of **B** (expressed by [this sum](https://datachant.com/wp-content/uploads/2016/06/screenshot_121.png).

### Gauss-Jordan Eliminination

[Gauss-Jordan elimination](https://en.wikipedia.org/wiki/Gaussian_elimination) converts a matrix **A** to its [Reduced row echelon form (RREF)](https://en.wikipedia.org/wiki/Row_echelon_form#Reduced_row_echelon_form). This is useful in solving systems of linear equations (where the matrix is augmented with a final column representing the right side of the equations), as well as finding determinants, inverses and ranks of matrices.

RREF is achieved by repeatedly executing row operations on the rows of the matrix until they satisfy the requirements.

### Transposition

[Matrix transposition](https://en.wikipedia.org/wiki/Transpose) produces the transpose **A(T)** of a matrix **A**. If **A** is an **m** x **n** matrix, A(T) is an **n** x **m** matrix, with each element a(T)[i,j] defined as a(j,i). Thus, **A** is "flipped" around its own diagonal.

### Inversion

The [inverse](https://en.wikipedia.org/wiki/Invertible_matrix) of a matrix **A** is a matrix **B** such that **AB** = **BA** = **I**, where **I** is the [identity matrix]. The inversion of a matrix is found using [Gauss-Jordan elimination](#gauss-jordan-elimination) on a matrix **A** augmented with an identity matrix.

### Determinant

The [determinant](https://en.wikipedia.org/wiki/Determinant) of a matrix **A**, written det(**A**) or |**A**| is a property of a matrix that is useful in a variety of different calculations. It is computed recursively (for matrices larger than 2 x 2) through multiplication, addition and subtraction of elements of sub-matrices. It is a long and cumbersome process for any larger matrices. 

Determinants can only be computed for square (**m** x **m**) matrices.

### Rank

The [rank](https://en.wikipedia.org/wiki/Rank_(linear_algebra)) of a matrix **A** is equivalent to the number of [linearly independent](https://en.wikipedia.org/wiki/Linear_independence) row vectors in **A**. Rank is calculated using Gauss-Jordan elimination (the process automatically zeroes all linearly dependent row vectors until linear independence is achieved).
