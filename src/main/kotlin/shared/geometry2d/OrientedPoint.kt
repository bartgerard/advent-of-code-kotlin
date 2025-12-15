package shared.geometry2d

import shared.spatial.Direction

// Pose?
data class OrientedPoint(
    val point: Point2dInt = Point2dInt.ZERO,
    val direction: Direction
)