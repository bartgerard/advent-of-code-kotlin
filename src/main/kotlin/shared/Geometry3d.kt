package shared

import kotlin.math.PI
import kotlin.math.abs


data class Box(
    private val length: Long,
    private val width: Long,
    private val height: Long
) {
    fun volume(): Long {
        return length * width * height
    }

    fun surfaceArea(): Long {
        return 2 * (length * width + width * height + height * length)
    }

    fun areaOfSmallestSide(): Long {
        return sequenceOf(
            length * width,
            width * height,
            height * length
        )
            .min()
    }

    fun smallestPerimeter(): Long {
        return 2 * sequenceOf(
            length + width,
            width + height,
            height + length
        )
            .min()
    }
}


data class Vector3d(
    val x: Double,
    val y: Double,
    val z: Double = 0.0
) {
    companion object {
        val ZERO = Vector3d(0.0, 0.0)
    }

    constructor(values: List<Double>) : this(values[0], values[1], values[2])

    operator fun times(scalar: Double) = Vector3d(x * scalar, y * scalar, z * scalar)

    infix fun dot(v: Vector3d): Double = x * v.x + y * v.y + z * v.z

    infix fun cross(v: Vector3d): Vector3d = Vector3d(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
    )

}

data class Point3d(
    val x: Double,
    val y: Double,
    val z: Double = 0.0
) {
    companion object {
        val ZERO = Point3d(0.0, 0.0, 0.0)
    }

    constructor(values: List<Double>) : this(values[0], values[1], values[2])

    operator fun plus(p: Point3d) = Point3d(x + p.x, y + p.y, z + p.z)

    operator fun minus(p: Point3d) = Vector3d(x - p.x, y - p.y, z - p.z)

    operator fun times(p: Point3d) = Point3d(x * p.x, y * p.y, z * p.z)

    fun manhattan(p: Point3d) = abs(x - p.x) + abs(y - p.y) + abs(z - p.z)

    operator fun plus(v: Vector3d) = Point3d(x + v.x, y + v.y, z + v.z)

    operator fun minus(v: Vector3d) = Point3d(x - v.x, y - v.y, z - v.z)

    infix fun to(p: Point3d) = Box3d(
        x..p.x,
        y..p.y,
        z..p.z
    )
}

data class Ray3d(
    val point: Point3d,
    val direction: Vector3d
) {
    companion object {
        fun intersectionsFor(rays: List<Ray3d>): List<Point3d> {
            val intersections = mutableListOf<Point3d>()

            rays.forEachIndexed { index, r1 ->
                rays.subList(index + 1, rays.size).forEach { r2 ->
                    val intersection = r1 intersect r2
                    intersection?.let { intersections += intersection }
                }
            }

            return intersections.toList()
        }

        fun intersectionTimesFor(rays: List<Ray3d>): List<Double> {
            val times = mutableListOf<Double>()

            //val flatMapIndexed: List<Double?> = rays.flatMapIndexed { index, r1 ->
            //    rays.subList(index + 1, rays.size).map { r2 -> r1 intersectionTime r2 }
            //}

            rays.forEachIndexed { index, r1 ->
                rays.subList(index + 1, rays.size).forEach { r2 ->
                    val intersection = r1 intersectionTime r2
                    intersection?.let { times += intersection }
                }
            }

            return times.toList()
        }
    }

    /// p1 + t * v1 = p2 + s * v2
    ///
    /// t = ((p2 - p1) cross v2) dot (v1 cross v2) / ((v1 cross v2) * (v1 cross v2))
    infix fun intersect(r: Ray3d): Point3d? = intersectionTime(r)?.let { t -> at(t) }

    infix fun intersectionTime(r: Ray3d): Double? {
        val p1 = point
        val v1 = direction
        val p2 = r.point
        val v2 = r.direction

        val a = v1 cross v2
        val dot = a dot a

        if (dot == 0.0) {
            // r1 and r2 are parallel
            return null
        }

        val b = (p2 - p1).cross(v2)

        return b.dot(a) / dot
    }

    fun at(t: Double): Point3d = point + direction * t
}

data class Sphere(
    val center: Point3d = Point3d.ZERO,
    val radius: Double
) {
    fun diameter(): Double = radius * 2

    fun area(): Double = PI * radius * radius * 4

    fun volume(): Double = (PI * radius * radius * radius * 4) / 3
}

data class Box3d(
    private val x: ClosedFloatingPointRange<Double>,
    private val y: ClosedFloatingPointRange<Double>,
    private val z: ClosedFloatingPointRange<Double> = 0.0..0.0
) {
    fun length(): Double = x.endInclusive - x.start
    fun width(): Double = y.endInclusive - y.start
    fun height(): Double = z.endInclusive - z.start

    fun volume(): Double = length() * width() * height()

    fun surfaceArea(): Double = 2 * (length() * width() + width() * height() + height() * length())

    fun areaOfSmallestSide(): Double = sequenceOf(
        length() * width(),
        width() * height(),
        height() * length()
    )
        .min()

    fun smallestPerimeter(): Double = 2 * sequenceOf(
        length() + width(),
        width() + height(),
        height() + length()
    )
        .min()

    fun contains(p: Point3d): Boolean = p.x in x && p.y in y && p.z in z
}