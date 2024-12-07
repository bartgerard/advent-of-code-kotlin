package aock2023

import shared.*

data class Year2023Day24(
    private val rays: List<Ray3d>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map {
                val (point, direction) = it.split("@")
                Ray3d(Point3d(point.toDoubles()), Vector3d(direction.toDoubles()))
            }
    )

    fun to2d(): Year2023Day24 =
        Year2023Day24(rays.map { Ray3d(Point3d(it.point.x, it.point.y), Vector3d(it.direction.x, it.direction.y)) })

    fun partOne(min: Long, max: Long): Int {
        val testArea = (min.toDouble()..max.toDouble()).let { it -> Box3d(it, it) }
        return Ray3d.intersectionMetaDataFor(rays)
            .filter { it.times.all { t -> t >= 0 } }
            .count { testArea.contains(it.point) }
    }

    fun partTwo(): Int = 0
}