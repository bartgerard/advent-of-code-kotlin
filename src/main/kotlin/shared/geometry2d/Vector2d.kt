package shared.geometry2d

interface Vector2d<T : Number> {
    val x: T
    val y: T

    operator fun times(scalar: T): Vector2d<T>

    fun length(): Double

    fun dot(v: Vector2d<T>): T
    fun cross(v: Vector2d<Int>): T
}