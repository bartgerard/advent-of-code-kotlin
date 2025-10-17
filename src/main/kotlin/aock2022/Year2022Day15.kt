package aock2022

import shared.Point2d
import shared.length
import shared.sanitize
import shared.toIntegers
import shared.toLongRange
import shared.without
import kotlin.math.absoluteValue

data class Year2022Day15(
    private val samples: List<Sample>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.toIntegers() }
            .map {
                Sample(
                    Point2d(it[0], it[1]),
                    Point2d(it[2], it[3])
                )
            }
    )

    fun partOne(y: Long) = run { BeaconExclusionZone(samples) }.countBeaconLessZones(y)
    fun partTwo(max: Long) = run { BeaconExclusionZone(samples) }
        .findDistressSignal(max)!!
        .let { tuningFrequency(it) }

    fun tuningFrequency(p: Point2d) = p.x * 4000000L + p.y
}

data class BeaconExclusionZone(
    private val samples: List<Sample>
) {
    fun countBeaconLessZones(y: Long): Long {
        val impactedSpots = samples.mapNotNull { it.impactOn(y) }
        val beacons = samples.map { it.beacon }
            .filter { it.y.toLong() == y }
            .map { it.x..it.x }
            .map { it.toLongRange() }

        val beaconLessSpots = impactedSpots.without(beacons)
        return beaconLessSpots.sumOf { it.length() }
    }

    fun findDistressSignal(max: Long): Point2d? {
        val maxRange = (0..max)

        return maxRange.asSequence()
            .mapNotNull { y ->
                val impactedSpots = samples.mapNotNull { it.impactOn(y) }
                val beaconSpots = maxRange.without(impactedSpots)

                if (beaconSpots.isEmpty()) {
                    null
                } else {
                    Point2d(beaconSpots.first().first.toInt(), y.toInt())
                }
            }
            .firstOrNull()
    }
}

data class Sample(
    val sensor: Point2d,
    val beacon: Point2d
) {
    fun impactOn(y: Long): LongRange? {
        val distance = sensor.manhattan(beacon)

        val perpendicularDistanceSensorToY = (sensor.y - y).absoluteValue

        return if (perpendicularDistanceSensorToY > distance) {
            null
        } else {
            val remainingDistance = distance - perpendicularDistanceSensorToY
            sensor.x.minus(remainingDistance)..sensor.x.plus(remainingDistance)
        }
    }
}