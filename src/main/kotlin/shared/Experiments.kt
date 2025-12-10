package shared

import Jama.Matrix
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
    runBlocking {
        test()
    }
}

suspend fun test() = coroutineScope {
    val channel = Channel<String>()

    launch {
        println("Channel Sent data 1 to channel")
        channel.send("Data 1")
        println("Channel Sent data 2 to channel")
        channel.send("Data 2")
        channel.close() // we're done sending so channel should be closed
    }

    launch {
        channel.consumeEach {
            println("Channel Received: $it")
        }
        println("Done!")
    }
}