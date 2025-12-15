package shared.grid

import shared.geometry2d.Point2dInt
import shared.geometry2d.Rectangle2dInt
import shared.geometry2d.Vector2dInt

interface MutableGrid<T> : Grid<T> {
    fun set(point: Point2dInt, value: T)
}

fun <T> MutableGrid<T>.setInDirection(point: Point2dInt, direction: Vector2dInt, values: List<T>) {
    values.forEachIndexed { i, value -> set(point + direction * i, value) }
}

fun <T> MutableGrid<T>.fill(rectangle: Rectangle2dInt, value: T) {
    rectangle.points().forEach { set(it, value) }
}

fun <T> MutableGrid<T>.fill(rectangles: Collection<Rectangle2dInt>, value: T) {
    rectangles.forEach { fill(it, value) }
}