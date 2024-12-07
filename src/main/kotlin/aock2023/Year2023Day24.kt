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
        val testArea = min.toDouble()..max.toDouble()
        val times = Ray3d.intersectionTimesFor(rays)
            .filter { testArea.contains(it) }

        return times.count()
    }

    fun partTwo(): Int = 0
}