package ec2025

import shared.ceilDiv
import shared.sanitize
import shared.toLongs
import kotlin.math.floor

data class Year2025Quest04(
    private val axes: List<Axis>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { line -> Axis(line.toLongs().map { Gear(it) }) }
    )

    fun partOne() = 2025 * axes.first().gears.first().teeth / axes.last().gears.first().teeth
    fun partTwo() = ceilDiv(10000000000000L * axes.last().gears.first().teeth, axes.first().gears.first().teeth)
    fun partThree(): Double {
        val drivingTeeth = axes.dropLast(1).fold(1.0) { acc, axis -> acc * axis.gears.last().teeth }
        val drivenTeeth = axes.drop(1).fold(1.0) { acc, axis -> acc * axis.gears.first().teeth }

        return floor(100L * drivingTeeth / drivenTeeth)
    }
}

data class Axis(
    val gears: List<Gear>
)

data class Gear(
    val teeth: Long
)