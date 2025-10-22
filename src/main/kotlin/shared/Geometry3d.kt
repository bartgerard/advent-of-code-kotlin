package shared

import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.sqrt

fun <T> Triple<T, T, T>.x() = first
fun <T> Triple<T, T, T>.y() = second
fun <T> Triple<T, T, T>.z() = third

data class Vector3d(
    val x: Double,
    val y: Double,
    val z: Double = 0.0
) {
    companion object {
        val ZERO = Vector3d(0.0, 0.0)
    }

    constructor(values: List<Double>) : this(values[0], values[1], values[2])
    constructor(values: Map<Axis, Double>) : this(values[Axis.X] ?: 0.0, values[Axis.Y] ?: 0.0, values[Axis.Z] ?: 0.0)

    operator fun plus(v: Vector3d) = Vector3d(x + v.x, y + v.y, z + v.z)
    operator fun minus(v: Vector3d) = Vector3d(x - v.x, y - v.y, z - v.z)

    operator fun times(scalar: Int) = Vector3d(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Double) = Vector3d(x * scalar, y * scalar, z * scalar)

    operator fun div(scalar: Int) = Vector3d(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Double) = Vector3d(x / scalar, y / scalar, z / scalar)

    fun length() = sqrt(x * x + y * y + z * z)

    infix fun dot(v: Vector3d) = x * v.x + y * v.y + z * v.z

    infix fun cross(v: Vector3d) = Vector3d(
        y * v.z - z * v.y,
        z * v.x - x * v.z,
        x * v.y - y * v.x
    )

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> z
    }

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
    operator fun plus(v: Vector3d) = Point3d(x + v.x, y + v.y, z + v.z)

    operator fun minus(p: Point3d) = Vector3d(x - p.x, y - p.y, z - p.z)
    operator fun minus(v: Vector3d) = Point3d(x - v.x, y - v.y, z - v.z)

    operator fun times(p: Point3d) = Point3d(x * p.x, y * p.y, z * p.z)

    fun manhattan(p: Point3d) = (x - p.x).absoluteValue + (y - p.y).absoluteValue + (z - p.z).absoluteValue

    infix fun to(p: Point3d) = Box3d(x..p.x, y..p.y, z..p.z)

    fun on(axis: Axis) = when (axis) {
        Axis.X -> x
        Axis.Y -> y
        Axis.Z -> z
    }

    override fun toString() = "p($x, $y, $z)"
}

data class Ray3d(
    val point: Point3d,
    val direction: Vector3d
) {
    companion object {
        fun intersect(rays: List<Ray3d>): List<Point3d> = rays.flatMapIndexed { index, r1 ->
            rays.subList(index + 1, rays.size).mapNotNull { r2 -> r1 intersect r2 }
        }

        fun intersectionsFor(rays: List<Ray3d>): List<Intersection> = rays.flatMapIndexed { index, r1 ->
            rays.subList(index + 1, rays.size).mapNotNull { r2 -> r1 intersection r2 }
        }
    }

    /// p1 + t * v1 = p2 + s * v2
    ///
    /// t = ((p2 - p1) cross v2) dot (v1 cross v2) / ((v1 cross v2) * (v1 cross v2))
    infix fun intersect(r: Ray3d): Point3d? = intersectionTime(r)?.let { t -> at(t) }

    private fun intersectionTime(r: Ray3d): Double? {
        val p1 = point
        val v1 = direction
        val p2 = r.point
        val v2 = r.direction

        val a = v1 cross v2
        val aDot = a dot a

        if (aDot == 0.0) {
            // r1 and r2 are parallel
            return null
        }

        val b = (p2 - p1) cross v2
        return b dot a / aDot
    }

    infix fun intersection(ray2: Ray3d): Intersection? {
        val time1 = intersectionTime(ray2) ?: return null
        val time2 = ray2.intersectionTime(this) ?: return null

        return Intersection(
            setOf(
                IntersectedRay3d(this, time1),
                IntersectedRay3d(ray2, time2)
            )
        )
    }

    fun at(t: Int) = point + direction * t
    fun at(t: Double) = point + direction * t
}

data class IntersectedRay3d(
    val ray: Ray3d,
    val time: Double
)

data class Intersection(
    val rays: Set<IntersectedRay3d>
) {
    fun intersectionTimes(): List<Double> = rays.map { it.time }
    fun intersectionPoint(): Point3d = rays.first().let { it.ray.at(it.time) }
}

data class Sphere(
    val center: Point3d = Point3d.ZERO,
    val radius: Double
) {
    fun diameter() = radius * 2
    fun area() = PI * radius * radius * 4
    fun volume() = (PI * radius * radius * radius * 4) / 3
}

data class Box3d(
    val x: ClosedFloatingPointRange<Double>,
    val y: ClosedFloatingPointRange<Double>,
    val z: ClosedFloatingPointRange<Double> = 0.0..0.0
) {
    constructor(length: Double, width: Double, height: Double = 0.0) : this(0.0..length, 0.0..width, 0.0..height)

    fun length() = x.endInclusive - x.start
    fun width() = y.endInclusive - y.start
    fun height() = z.endInclusive - z.start

    fun volume() = length() * width() * height()
    fun surfaceArea() = 2 * (length() * width() + width() * height() + height() * length())
    fun areaOfSmallestSide() = sequenceOf(length() * width(), width() * height(), height() * length()).min()
    fun smallestPerimeter() = 2 * sequenceOf(length() + width(), width() + height(), height() + length()).min()

    fun contains(p: Point3d) = p.x in x && p.y in y && p.z in z
}

data class Box(
    val length: Long,
    val width: Long,
    val height: Long
) {
    fun volume() = length * width * height
    fun surfaceArea() = 2 * (length * width + width * height + height * length)
    fun areaOfSmallestSide() = sequenceOf(length * width, width * height, height * length).min()
    fun smallestPerimeter() = 2 * sequenceOf(length + width, width + height, height + length).min()
}