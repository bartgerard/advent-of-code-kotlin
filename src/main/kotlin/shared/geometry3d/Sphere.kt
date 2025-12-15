package shared.geometry3d

import kotlin.math.PI

data class Sphere(
    val center: Point3d = Point3d.ZERO,
    val radius: Double
) {
    fun diameter() = radius * 2
    fun area() = PI * radius * radius * 4
    fun volume() = (PI * radius * radius * radius * 4) / 3
}