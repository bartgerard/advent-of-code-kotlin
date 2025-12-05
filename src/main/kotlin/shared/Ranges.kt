package shared

import kotlin.math.max
import kotlin.math.min

fun Collection<Long>.toRanges(): List<LongRange> {
    if (this.isEmpty()) {
        return emptyList()
    } else if (this.size == 1) {
        return listOf(this.first()..this.first())
    }

    val sorted = this.sorted()
    val nonConsecutiveIndices = (1..<sorted.size).filter { sorted[it - 1] + 1 != sorted[it] }

    val borderIndices = buildList {
        add(0)
        addAll(nonConsecutiveIndices)
        add(sorted.size)
    }

    return (0..<borderIndices.size)
        .zipWithNext { i, j -> sorted[borderIndices[i]]..sorted[borderIndices[j] - 1] }
}

fun allIntersections(ranges: Collection<LongRange>): List<LongRange> {
    if (ranges.isEmpty()) {
        return emptyList()
    }

    val keyValues = buildSet {
        addAll(ranges.map { it.first })
        addAll(ranges.map { it.last + 1 })
    }
        .sorted()

    if (keyValues.size == 1) {
        return listOf(keyValues[0]..keyValues[0])
    }

    return (0..<keyValues.size)
        .zipWithNext { i, j -> keyValues[i]..<keyValues[j] }
}

fun Collection<LongRange>.usedIntersections(): List<LongRange> = allIntersections(this)
    .filter { intersection -> this.any { it.contains(intersection.first) } }

fun Collection<LongRange>.gaps(): List<LongRange> {
    val sortedIntersections = this.usedIntersections()

    if (sortedIntersections.isEmpty() || sortedIntersections.size == 1) {
        return emptyList()
    }

    return sortedIntersections.zipWithNext()
        .filter { it.first.last + 1 != it.second.first }
        .map { it.first.last + 1..<it.second.first }
}

fun Collection<LongRange>.merge(): List<LongRange> {
    val sortedIntersections = this.usedIntersections()

    if (sortedIntersections.size <= 1) {
        return sortedIntersections
    }

    val nonConsecutiveIndices = (1..<sortedIntersections.size)
        .filter { sortedIntersections[it - 1].last + 1 != sortedIntersections[it].first() }

    val borderIndices = buildList {
        add(0)
        addAll(nonConsecutiveIndices)
        add(sortedIntersections.size)
    }

    return (0..<borderIndices.size)
        .zipWithNext { i, j -> sortedIntersections[borderIndices[i]].first..sortedIntersections[borderIndices[j] - 1].last }
}

fun MutableList<LongRange>.mergeOne(newRange: LongRange) {
    val overlappingRanges = buildList {
        addAll(this@mergeOne.filter { (it.first - 1) in newRange || (it.last + 1) in newRange })
        add(newRange)
    }
    removeAll(overlappingRanges)
    add(overlappingRanges.minOf { it.first }..overlappingRanges.maxOf { it.last })

    sortBy { it.first }
}

fun Collection<LongRange>.mergeOne(newRange: LongRange): List<LongRange> {
    val overlappingRanges = buildList {
        addAll(this@mergeOne.filter { (it.first - 1) in newRange || (it.last + 1) in newRange })
        add(newRange)
    }

    return buildList {
        addAll(this@mergeOne)
        removeAll(overlappingRanges)
        add(overlappingRanges.minOf { it.first }..overlappingRanges.maxOf { it.last })
    }
        .sortedBy { it.first }
}

fun LongRange.containsRange(other: LongRange) = this.first <= other.first && other.last <= this.last

fun LongRange.overlaps(other: LongRange) = this.first <= other.last && other.first <= this.last

fun LongRange.without(subtrahend: LongRange): List<LongRange> {
    if (this == subtrahend) {
        return emptyList()
    } else if (!overlaps(subtrahend)) {
        return listOf(this)
    }

    val sequence1 = if (this.first < subtrahend.first) {
        sequenceOf(this.first..<subtrahend.first)
    } else {
        emptySequence()
    }
    val sequence2 = if (subtrahend.last < this.last) {
        sequenceOf(subtrahend.last + 1..this.last)
    } else {
        emptySequence()
    }

    return sequenceOf(sequence1, sequence2)
        .flatten()
        .toList()
}

fun LongRange.without(subtrahends: Collection<LongRange>): List<LongRange> {
    val intersectingSubtrahends = subtrahends.filter { overlaps(it) }

    if (intersectingSubtrahends.isEmpty()) {
        return listOf(this)
    } else if (intersectingSubtrahends.size == 1) {
        return without(intersectingSubtrahends.first())
    }

    val innerGaps = intersectingSubtrahends.gaps().filter { overlaps(it) }
    val min: Long = intersectingSubtrahends.minOfOrNull { it.first } ?: Long.MIN_VALUE
    val max: Long = intersectingSubtrahends.maxOfOrNull { it.last } ?: Long.MAX_VALUE

    val outerGaps = without(min..max)

    return sequenceOf(innerGaps, outerGaps)
        .flatten()
        .sortedBy { it.first }
        .toList()
}

fun Collection<LongRange>.without(subtrahends: Collection<LongRange>): List<LongRange> =
    this.merge().flatMap { it.without(subtrahends) }

fun IntRange.length(): Int = last - first + 1
fun LongRange.length(): Long = last - first + 1
fun LongProgression.length(): Long = max(first, last) - min(first, last) + 1

fun IntRange.toLongRange() = first.toLong()..last.toLong()

fun main() {
    println((1L..5L).length())
    println(listOf(1L, 2L, 3L, 10L, 11L, 12L).toRanges())
    println(listOf((1L..5L), (3L..10L), (23L..40L)).merge())
    println(listOf((3L..10L), (23L..40L)).mergeOne(11L..39L))
}

operator fun LongRange.contains(other: LongRange) = this.contains(other.first) && this.contains(other.last)