package shared

fun Collection<Long>.toRanges(): List<LongRange> {
    if (this.isEmpty()) {
        return emptyList()
    } else if (this.size == 1) {
        return listOf(this.first()..this.first())
    }

    val sorted = this.sorted()
    val nonConsecutiveIndices = (1..<sorted.size).filter { sorted[it - 1] + 1 != sorted[it] }

    val borderIndices = buildList<Int> {
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

    val keyValues = buildList<Long> {
        addAll(ranges.map { it.first })
        addAll(ranges.map { it.last + 1 })
    }
        .distinct()
        .sorted()

    if (keyValues.size == 1) {
        return listOf(keyValues[0]..keyValues[0])
    }

    return (0..<keyValues.size)
        .zipWithNext { i, j -> keyValues[i]..(keyValues[j] - 1) }
}

fun Collection<LongRange>.usedIntersections() = allIntersections(this)
    .filter { intersection -> this.any { it.contains(intersection.start) } }

fun Collection<LongRange>.merge(): List<LongRange> {
    val sortedIntersections = this.usedIntersections()

    if (sortedIntersections.isEmpty()) {
        return emptyList()
    } else if (sortedIntersections.size == 1) {
        return listOf(sortedIntersections.first())
    }

    val nonConsecutiveIndices = (1..<sortedIntersections.size)
        .filter { sortedIntersections[it - 1].last + 1 != sortedIntersections[it].first() }

    val borderIndices = buildList<Int> {
        add(0)
        addAll(nonConsecutiveIndices)
        add(sortedIntersections.size)
    }

    return (0..<borderIndices.size)
        .zipWithNext { i, j -> sortedIntersections[borderIndices[i]].first..sortedIntersections[borderIndices[j] - 1].last }
}

fun MutableList<LongRange>.mergeOne(newRange: LongRange) {
    val overlappingRanges = buildList {
        addAll(this@mergeOne.filter { (it.start - 1) in newRange || (it.last + 1) in newRange })
        add(newRange)
    }
    removeAll(overlappingRanges)
    add(overlappingRanges.minOf { it.first }..overlappingRanges.maxOf { it.last })

    sortBy { it.start }
}

fun Collection<LongRange>.mergeOne(newRange: LongRange): List<LongRange> {
    val overlappingRanges = buildList {
        addAll(this@mergeOne.filter { (it.start - 1) in newRange || (it.last + 1) in newRange })
        add(newRange)
    }

    return buildList {
        addAll(this@mergeOne)
        removeAll(overlappingRanges)
        add(overlappingRanges.minOf { it.first }..overlappingRanges.maxOf { it.last })
    }
        .sortedBy { it.start }
}

fun IntRange.length() = this.last - this.first + 1
fun LongRange.length() = this.last - this.first + 1

fun main() {
    println((1L..5L).length())
    println(listOf(1L, 2L, 3L, 10L, 11L, 12L).toRanges())
    println(listOf((1L..5L), (3L..10L), (23L..40L)).merge())
    println(listOf((3L..10L), (23L..40L)).mergeOne(11L..39L))
}