package shared.geometry2d

data class Ray2d(
    val point: Point2dInt = Point2dInt.ZERO,
    val direction: Vector2dInt
)