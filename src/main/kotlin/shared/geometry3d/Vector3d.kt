package shared.geometry3d

import shared.spatial.Axis
import kotlin.math.sqrt

data class Vector3d(
    val x: Double,
    val y: Double,
    val z: Double = 0.0
) {
    companion object {
        val ZERO = Vector3d(0.0, 0.0)
    }

    constructor(values: List<Double>) : this(values[0], values[1], values[2])
    constructor(values: Map<Axis, Double>) : this(values[Axis.X] ?: 0.0, values[Axis.Y] ?: 0.0, values[Axis.Z] ?: 0.0)

    operator fun plus(v: Vector3d) = Vector3d(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3d) = Vector3d(x - v.x, y - v.y, z - v.z)

    operator fun times(scalar: Int) = Vector3d(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Double) = Vector3d(x * scalar, y * scalar, z * scalar)

    operator fun div(scalar: Int) = Vector3d(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Double) = Vector3d(x / scalar, y / scalar, z / scalar)

    fun length() = sqrt(x * x + y * y + z * z)

    infix fun dot(v: Vector3d) = x * v.x + y * v.y + z * v.z

    infix fun cross(v: Vector3d) = Vector3d(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
    )

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> z
    }

}