package aock2025

import shared.Point3d
import shared.distinctPairs
import shared.product
import shared.sanitize
import shared.toDoubles

data class Year2025Day08(
    private val points: Set<Point3d>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.toDoubles().let { (x, y, z) -> Point3d(x, y, z) } }
            .toSet()
    )

    // BoundedBox

    fun partOne(connections: Int): Int {
        val circuits = Circuits(points.map { Circuit(it) }.toMutableSet())

        val a = points.distinctPairs()
            .sortedBy { it.first.length(it.second) }
            .toMutableList()

        // (162,817,812) (425,690,689) (431,825,988)
        // (906,360,560) (805,96,715)

        repeat(connections) {
            val b = a.removeFirst()
            circuits.merge(b.first, b.second)
        }

        return circuits.circuits
            .map { it.size() }
            .sortedDescending()
            .take(3)
            .product()
    }

    fun partTwo(): Int {
        val circuits = Circuits(points.map { Circuit(it) }.toMutableSet())

        val a = points.distinctPairs()
            .sortedBy { it.first.length(it.second) }
            .toMutableList()

        while (true) {
            val b = a.removeFirst()
            circuits.merge(b.first, b.second)

            if (circuits.circuits.all { it.size() > 1 }) {
                return (b.first.x * b.second.x).toInt()
            }
        }
    }
}

data class Circuits(
    val circuits: MutableSet<Circuit> = mutableSetOf(),
) {
    fun merge(p1: Point3d, p2: Point3d): Boolean {
        val impactedCircuits = circuits.filter { it.points.contains(p1) || it.points.contains(p2) }

        if (impactedCircuits.isEmpty()) {
            circuits += Circuit(mutableSetOf(p1, p2))
        } else if (impactedCircuits.size == 1) {
            val isNew = impactedCircuits.first().points.containsAll(setOf(p1, p2))
            impactedCircuits.first().points.addAll(setOf(p1, p2))
            return isNew
        } else {
            val combinedCircuit = Circuit(impactedCircuits.flatMap { it.points }.toMutableSet())
            circuits.removeAll(impactedCircuits.toSet())
            circuits += combinedCircuit
        }

        return true
    }

    fun size() = circuits.sumOf { it.points.size }

}

data class Circuit(
    val points: MutableSet<Point3d>,
) {
    constructor(vararg points: Point3d) : this(points.toMutableSet())

    fun size() = points.size
}
