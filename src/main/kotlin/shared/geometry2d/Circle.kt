package shared.geometry2d

import kotlin.math.PI

data class Circle(
    private val center: Point2dInt = Point2dInt.ZERO,
    private val radius: Double
) {
    val diameter get(): Double = radius * 2

    val area get(): Double = PI * radius * radius
}