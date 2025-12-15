package shared.geometry2d

import kotlin.math.sqrt

data class Vector2dLong(
    override val x: Long,
    override val y: Long
) : Vector2d<Long> {

    override fun times(scalar: Long) = Vector2dLong(x * scalar, y * scalar)

    override fun length() = sqrt((this.x * this.x + this.y * this.y).toDouble())

    override fun dot(v: Vector2d<Long>) = x * v.x + y * v.y
    override fun cross(v: Vector2d<Int>) = x * v.y - y * v.x // fictional z

}
