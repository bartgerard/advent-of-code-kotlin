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
        return Ray3d.intersectionsFor(rays)
            .filter { it.intersectionTimes().all { t -> t >= 0 } }
            .count { testArea.contains(it.intersectionPoint()) }
    }

    /*

          ^
          |
    |-----A---> (ray0 -> point0 + direction0 * time0 = A)
          |
    |-----B---> (ray1 -> point1 + direction1 * time1 = B)
          |
    |-----C---> (ray2 -> point2 + direction2 * time2 = C)
          |
          _

    |AB| collinear |BC|

    collinear
    * common direction
    * common point

    inspired by DaveBaum

    (p1 + v1 * t1) cross (p2 + v2 * t2) = 0

    [distributive cross product: a * (b + c) = ab + ac ]
    => (p1 cross p2) + (v1 cross p2) * t1 + (p1 cross v2) * t2 + (v1 cross v2) * t1 * t2 = 0

    [orthogonality: (v1 cross v2) dot v1 = (v1 cross v2) dot v2 = 0 ]

    [multiply with v2]
    => (p1 cross p2) dot v2 + (v1 cross p2) dot v2 * t1 = 0
    => t1 = -((p1 cross p2) dot v2) / ((v1 cross p2) dot v2)

    [multiply with v1]
    => (p1 cross p2) dot v1 + (p1 cross v2) dot v1 * t2 = 0
    => t2 = -((p1 cross p2) dot v1) / ((p1 cross v2) dot v1)

     */

    fun partTwo(): Int = findInitialPosition(rays.subList(0, 3)).let { (it.x + it.y + it.z).toInt() }

    private fun findInitialPosition(rays: List<Ray3d>): Point3d {
        val (ray0, ray1, ray2) = rays.subList(0, 3)

        val p1 = ray1.point - ray0.point
        val v1 = ray1.direction - ray0.direction

        val p2 = ray2.point - ray0.point
        val v2 = ray2.direction - ray0.direction

        val t1 = -((p1 cross p2) dot v2) / ((v1 cross p2) dot v2)
        val t2 = -((p1 cross p2) dot v1) / ((p1 cross v2) dot v1)

        val collision1 = ray1.at(t1)
        val collision2 = ray2.at(t2)

        val vector: Vector3d = (collision2 - collision1) / (t2 - t1)
        val initialPosition = collision1 - vector * t1

        return initialPosition
    }

}