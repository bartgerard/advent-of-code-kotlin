package shared

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.LUDecomposition

class SystemOfLinearEquations {
    companion object {
        fun <A : Number, C : Number> solve(
            coefficients: List<List<A>>,
            constants: List<C>
        ): List<Double> {
            val a = Array2DRowRealMatrix(
                coefficients
                    .map { row ->
                        row.map { it.toDouble() }
                            .toDoubleArray()
                    }
                    .toTypedArray()
            )

            val b = ArrayRealVector(
                constants
                    .map { it.toDouble() }
                    .toDoubleArray()
            )

            val solver = LUDecomposition(a).solver
            //val solver = QRDecomposition(a).solver
            //val solver = SingularValueDecomposition(a).solver

            val solution = solver.solve(b)

            return solution.map { it }
                .toArray()
                .toList()
        }
    }
}