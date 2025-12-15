package shared.geometry2d

import shared.spatial.Axis
import shared.spatial.Direction

interface Point2d<T : Number> {
    val x: T
    val y: T

    operator fun plus(p: Point2d<T>): Point2d<T>
    operator fun times(p: Point2d<T>): Point2d<T>

    fun manhattan(p: Point2d<T>): T

    operator fun minus(p: Point2d<T>): Vector2d<T>

    operator fun plus(v: Vector2d<T>): Point2d<T>
    operator fun minus(v: Vector2d<T>): Point2d<T>

    operator fun plus(direction: Direction): Point2d<T>
    operator fun plus(directions: Collection<Direction>) = directions.map { this + it }

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> error("Unsupported axis $axis")
    }
}