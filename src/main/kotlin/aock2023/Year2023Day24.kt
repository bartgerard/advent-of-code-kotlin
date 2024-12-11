package aock2023

import com.microsoft.z3.Context
import com.microsoft.z3.IntNum
import com.microsoft.z3.Status
import shared.*
import kotlin.math.roundToLong

data class Year2023Day24(
    private val rays: List<Ray3d>
) {
    companion object {
        fun equation(i: Int, axis: Axis) = "pr$axis + vr$axis * t$i = p$i$axis + v$i$axis * t$i"
        fun equations() = (1..3).flatMap { i -> Axis.THREE_DIMENSIONAL.map { axis -> equation(i, axis) } }
    }

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

    fun partTwo(): Long = findInitialPosition(rays.subList(0, 3)).let { it.x.roundToLong() + it.y.roundToLong() + it.z.roundToLong() }

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

    fun partTwoZ3(): Long = solve(rays.subList(0, 3))

    private fun solve(rays: List<Ray3d>): Long {
        return Context().use { ctx ->
            val variables = buildMap {
                putAll(rays.indices.map { "t$it" }.associateWith { ctx.mkIntConst(it) })
                putAll(Axis.THREE_DIMENSIONAL.map { "pr$it" }.associateWith { ctx.mkIntConst(it) })
                putAll(Axis.THREE_DIMENSIONAL.map { "vr$it" }.associateWith { ctx.mkIntConst(it) })
            }

            val solver = ctx.mkSolver()

            rays.indices.map { "t$it" }.map { variables[it] }.forEach { solver.add(ctx.mkGe(it, ctx.mkInt(0))) }

            // prX + vrX * t{i} = p{i}X + v{i}X * t{i}
            val equations = rays.flatMapIndexed { i, ray ->
                Axis.THREE_DIMENSIONAL.map { axis ->
                    ctx.mkEq(
                        ctx.mkAdd(variables["pr$axis"], ctx.mkMul(variables["vr$axis"], variables["t$i"])),
                        ctx.mkAdd(ctx.mkInt(ray.point.on(axis).roundToLong()), ctx.mkMul(ctx.mkInt(ray.direction.on(axis).roundToLong()), variables["t$i"]))
                    )
                }
            }
            equations.forEach { solver.add(it) }

            when (solver.check()) {
                Status.SATISFIABLE -> {
                    val x = solver.model.eval(variables["prX"], false) as IntNum
                    val y = solver.model.eval(variables["prY"], false) as IntNum
                    val z = solver.model.eval(variables["prZ"], false) as IntNum

                    println("Solution: p (x = $x, y = $y, z = $z)")
                    return x.int64 + y.int64 + z.int64
                }

                else -> throw IllegalStateException("No solution found!")
            }
        }
    }

}