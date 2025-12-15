package aock2019

import shared.geometry3d.Point3d
import shared.geometry3d.Vector3d
import shared.lcm
import shared.sanitize
import shared.spatial.Axis
import shared.toDoubles
import kotlin.math.abs

data class Year2019Day12(
    val moons: List<Moon>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { Moon(Point3d(it.toDoubles())) }
    )

    fun partOne(steps: Int): Long {
        repeat(steps) {
            step()
        }

        return moons.sumOf { it.totalEnergy() }.toLong()
    }

    fun partTwo(): Long {
        val patternsByAxis = Axis.THREE_DIMENSIONAL.associateWith { Patterns(moons.size) }

        while (!patternsByAxis.values.all { it.found() }) {
            step()

            patternsByAxis.forEach { (axis, pattern) ->
                moons.forEachIndexed { index, moon ->
                    pattern.add(index, moon.velocity.on(axis).toInt())
                }
            }
        }

        return lcm(patternsByAxis.values.map { it.patterns.first().pattern.size.toLong() })
    }

    private fun step() {
        moons.forEach { it.applyGravity(moons) }
        moons.forEach { it.move() }
    }
}

data class Patterns(
    val patterns: List<Pattern>
) {
    constructor(size: Int) : this(MutableList(size) { Pattern() })

    fun add(index: Int, value: Int) {
        if (!found()) {
            patterns[index].add(value)
        }
    }

    fun found() = patterns.all { it.found() }
}

data class Pattern(
    val pattern: MutableList<Int> = mutableListOf(),
    var rollingSum: Int = 0
) {
    fun add(value: Int) {
        pattern += value
        rollingSum += value
    }

    fun found() = rollingSum == 0 && pattern.lastOrNull() == 0
}

data class Moon(
    var position: Point3d,
    var velocity: Vector3d = Vector3d.ZERO
) {
    fun applyGravity(moons: List<Moon>) {
        this.velocity += gravitationalPullFrom(moons)
    }

    fun gravitationalPullFrom(moons: List<Moon>) = Axis.THREE_DIMENSIONAL
        .associateWith { axis -> gravitationalPullFrom(moons, axis) }
        .let { Vector3d(it) }

    // can be all moons as no impact on self
    fun gravitationalPullFrom(moons: List<Moon>, axis: Axis) = moons
        .sumOf { (it.position.on(axis) - this.position.on(axis)).coerceIn(-1.0, 1.0) }

    fun move() {
        this.position += velocity
    }

    fun potentialEnergy() = abs(position.x) + abs(position.y) + abs(position.z)

    fun kineticEnergy() = abs(velocity.x) + abs(velocity.y) + abs(velocity.z)

    fun totalEnergy() = potentialEnergy() * kineticEnergy()
}