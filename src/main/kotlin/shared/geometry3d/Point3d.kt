package shared.geometry3d

import shared.spatial.Axis
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

data class Point3d(
    val x: Double,
    val y: Double,
    val z: Double = 0.0
) {
    companion object {
        val ZERO = Point3d(0.0, 0.0, 0.0)
    }

    constructor(values: List<Double>) : this(values[0], values[1], values[2])

    operator fun plus(p: Point3d) = Point3d(x + p.x, y + p.y, z + p.z)
    operator fun plus(v: Vector3d) = Point3d(x + v.x, y + v.y, z + v.z)

    operator fun minus(p: Point3d) = Vector3d(x - p.x, y - p.y, z - p.z)
    operator fun minus(v: Vector3d) = Point3d(x - v.x, y - v.y, z - v.z)

    operator fun times(p: Point3d) = Point3d(x * p.x, y * p.y, z * p.z)

    fun manhattanDistanceTo(p: Point3d) = (x - p.x).absoluteValue + (y - p.y).absoluteValue + (z - p.z).absoluteValue
    fun euclideanDistanceTo(p: Point3d) = sqrt((x - p.x).pow(2) + (y - p.y).pow(2) + (z - p.z).pow(2))

    infix fun to(p: Point3d) = Box3d(x..p.x, y..p.y, z..p.z)

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> z
    }

    override fun toString() = "p($x, $y, $z)"
}