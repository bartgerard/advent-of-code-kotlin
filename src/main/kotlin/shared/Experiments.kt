package shared

import Jama.Matrix
import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.LUDecomposition


fun solveLinearWithCommonsMath() {
    val coefficients = Array2DRowRealMatrix(
        arrayOf(
            doubleArrayOf(1.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0)
        )
    )

    val constants = ArrayRealVector(doubleArrayOf(1.0, 1.0, 1.0))

    val solver = LUDecomposition(coefficients).solver
    val solution = solver.solve(constants)

    println("Solution: $solution")
}

fun solveLinearWithJama() {
    val coefficients = arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0),
        doubleArrayOf(0.0, 1.0, 0.0),
        doubleArrayOf(0.0, 0.0, 1.0)
    )
    val constants = doubleArrayOf(1.0, 1.0, 1.0)

    val matrixA = Matrix(coefficients)
    val matrixB = Matrix(constants, constants.size)

    val solution = matrixA.solve(matrixB)

    println("Solution: $solution")
}

fun main() {
    solveLinearWithCommonsMath()
    solveLinearWithJama()
}