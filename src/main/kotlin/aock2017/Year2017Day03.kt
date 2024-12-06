package aock2017

import shared.Point2d
import shared.sanitize
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Year2017Day03(
    private val squareId: Int
) {
    constructor(input: String) : this(input.sanitize().toInt())

    // 1, 9, 25, 49, 81..
    // 1^2, 3^2, 5^2, 7^2, 9^2..
    fun partOne(): Int {
        //return positionOf(squareId).manhattan(Point2d.ZERO)

        if (squareId == 1) {
            return 0
        }

        val lowerRingId = sqrt(squareId.toDouble() - 1).toInt()
            .let { if (it % 2 == 0) it - 1 else it }
        val side = lowerRingId + 1
        val middle = side / 2

        val maxValueLowerRing = lowerRingId.toDouble().pow(2).toInt()
        val lengthOnRing = squareId - maxValueLowerRing
        val lengthSideToCorner = lengthOnRing % side

        val lengthSideToAccessPort = lowerRingId - 1
        val lengthToMiddle = abs(lengthSideToCorner - middle)

        return lengthSideToAccessPort + lengthToMiddle

    }

    fun partTwo(): Int = 0

    fun positionOf(squareId: Int): Point2d {
        val ringId = sqrt(squareId.toDouble()).toInt()
            .let { if (it % 2 == 0) it - 1 else it }
        val side = ringId + 1
        val middle = side / 2

        val maxValueLowerRing = ringId.toDouble().pow(2).toInt()
        val lengthOnRing = squareId - maxValueLowerRing
        val quadrant = lengthOnRing / side
        val lengthToLowerCorner = lengthOnRing % side

        return Point2d(0, 0)
    }

}