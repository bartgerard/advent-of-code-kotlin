package ec2025

import org.apache.commons.lang3.Validate.isTrue
import shared.minMaxByOrNull
import shared.sanitize
import shared.toLongs
import java.util.Objects.isNull

data class Year2025Quest05(
    private val input: Map<Long, Fishbone>
) {
    constructor(input: String) : this(
        input.sanitize().lines()
            .map { it.split(":") }
            .associate { (id, numbers) -> id.toLong() to Fishbone.from(numbers.toLongs()) }
    )

    fun partOne() = input.values.sumOf { it.quality() }

    fun partTwo() = input.values.map { it.quality() }
        .minMaxByOrNull { it }!!
        .let { (min, max) -> max - min }

    fun partThree(): Long = input.entries
        .sortedWith(
            compareByDescending<Map.Entry<Long, Fishbone>> { it.value }
                .thenByDescending { it.key }
        )
        .withIndex()
        .sumOf { (index, entry) -> (index + 1) * entry.key }
}

data class Fishbone(
    private val segments: List<Segment>
) : Comparable<Fishbone> {
    companion object {
        fun from(numbers: List<Long>): Fishbone {
            val segments = mutableListOf<Segment>()

            numbers.forEach { number ->
                segments.firstOrNull { it.add(number) } ?: run {
                    segments += Segment(number)
                }
            }

            return Fishbone(segments)
        }
    }

    fun quality() = segments
        .map { it.middle }
        .joinToString("")
        .toLong()

    fun levels() = segments.map { it.number() }

    override fun compareTo(other: Fishbone): Int {
        // If two swords have different qualities, a higher quality score means a better sword.
        val qualityComparison = this.quality().compareTo(other.quality())
        if (qualityComparison != 0) {
            return qualityComparison
        }

        // If the quality of both swords is the same, the numbers resulting from the subsequent levels of the fishbone should be compared, starting from the top.
        // A higher score on the first level, which differs between swords, indicates a better sword.
        val thisLevels = this.levels()
        val otherLevels = other.levels()

        isTrue(thisLevels.size == otherLevels.size)

        for (i in 0 until thisLevels.size) {
            val levelComparison = thisLevels[i].compareTo(otherLevels[i])
            if (levelComparison != 0) {
                return levelComparison
            }
        }

        // If all common levels are equal, longer list is better (has more levels)
        return thisLevels.size.compareTo(otherLevels.size)
    }
}

data class Segment(
    var left: Long?,
    val middle: Long,
    var right: Long?
) {
    constructor(middle: Long) : this(null, middle, null)

    fun add(value: Long): Boolean = when {
        value < middle && isNull(left) -> {
            left = value
            true
        }

        value > middle && isNull(right) -> {
            right = value
            true
        }

        else -> false
    }

    fun number() = buildString {
        left?.let { append(it) }
        append(middle)
        right?.let { append(it) }
    }
        .toLong()
}