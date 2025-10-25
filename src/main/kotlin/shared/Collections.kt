package shared

import java.util.Objects.isNull

fun <T> union(sets: Collection<Set<T>>): Set<T> = sets.flatten().fold(emptySet()) { acc, set -> acc + set }

fun <T> union(lists: List<List<T>>): List<T> = lists.flatten().fold(emptyList()) { acc, list -> acc + list }

fun <T> List<T>.groupByIndexRemainder(divisor: Int): List<List<T>> = this.indices
    .groupBy { it % divisor }
    .map { (_, indices) -> indices.map { this[it] } }

fun <T> List<List<T>>.transpose() = this[0].indices.map { column -> this.indices.map { row -> this[row][column] } }

//fun <T> List<T>.frequencies() = this.groupBy { it }.mapValues { it.value.size }
fun <T> List<T>.frequencies() = this.groupingBy { it }.eachCount()


fun <T> List<T>.withoutIndex(index: Int) = this.filterIndexed { i, _ -> i != index }

//fun List<Long>.intervals() = (1..<this.size).map { this[it] - this[it - 1] }
fun List<Long>.intervals() = this.zipWithNext { a, b -> b - a }

fun <T> List<T>.cyclic() = CyclicList<T>(this)

data class CyclicList<T>(val list: List<T>) : List<T> by list {
    override operator fun get(index: Int): T = list[index % list.size]
}

fun <T> List<T>.zipWithNextCyclical() = zipWithNext() + (last() to first())

/**
 * Generate Strings based on available options at each segment.
 * [["A"], ["B", "C"], ["D"]] -> ["ABD","ACD"]
 */
fun List<Collection<CharSequence>>.generate(): List<String> = fold(listOf("")) { acc, options ->
    acc.flatMap { previous -> options.map { previous + it } }
}

fun <T> Iterable<T>.combinations() = asSequence().flatMap { i -> map { j -> Pair(i, j) } }

fun <T> Iterable<T>.distinctPairs() = sequence {
    val list = this@distinctPairs.toList()

    for (i in 0..list.size) {
        for (j in i + 1..<list.size) {
            yield(list[i] to list[j])
        }
    }
}

fun <T> Iterable<T>.allPairs(includeSelf: Boolean = false) = sequence {
    val list = this@allPairs.toList()

    for (i in list.indices) {
        for (j in list.indices) {
            if (includeSelf || i != j) {
                yield(list[i] to list[j])
            }
        }
    }
}

fun <T> Iterable<T>.indicesOf(elements: Collection<T>) = elements.map { indexOf(it) }

fun List<String>.allShortest() = minOf { it.length }
    .let { min -> filter { it.length == min } }

infix fun <T> Iterable<T>.difference(other: Iterable<T>) = (this union other) - (this intersect other)

fun <T> Iterable<T>.firstRepeated(): T? {
    val seen = mutableSetOf<T>()

    for (element in this) {
        if (!seen.add(element)) {
            return element
        }
    }

    return null
}

fun <T> Sequence<T>.firstRepeated(): T? {
    val seen = mutableSetOf<T>()

    for (element in this) {
        if (!seen.add(element)) {
            return element
        }
    }

    return null
}

fun <T> Set<T>.permutations(): Sequence<List<T>> = this.toList().permutations()

// advantage -> lazy evaluation!!
// example: listOf(1, 2, 3, 4, 5).permutations().toList()
fun <T> List<T>.permutations(): Sequence<List<T>> = sequence {
    if (size == 1) {
        yield(this@permutations)
    } else {
        for (index in indices) {
            val element = this@permutations[index]
            val remaining = this@permutations.withoutIndex(index)
            for (permutations in remaining.permutations()) {
                yield(listOf(element) + permutations)
            }
        }
    }
}

fun <T : Comparable<T>> Iterable<T>.takeWhileIncreasing() = takeWhileIncreasingBy(naturalOrder())

fun <T> Iterable<T>.takeWhileIncreasingBy(comparator: Comparator<T>) = takeWhileIncreasingBy(comparator) { it }

fun <T, K : Comparable<K>> Iterable<T>.takeWhileIncreasingBy(selector: (T) -> K) = takeWhileIncreasingBy(naturalOrder(), selector)

fun <T, K> Iterable<T>.takeWhileIncreasingBy(
    comparator: Comparator<K>,
    selector: (T) -> K,
): List<T> {
    var max: K? = null
    val result = mutableListOf<T>()

    for (element in this) {
        val value = selector(element)

        if (isNull(max) || comparator.compare(value, max) > 0) {
            result += element
            max = value
        } else {
            break
        }
    }

    return result
}

fun <T : Comparable<T>> Iterable<T>.takeOnlyIncreasing() = takeOnlyIncreasingBy(naturalOrder())

fun <T> Iterable<T>.takeOnlyIncreasingBy(comparator: Comparator<T>) = takeOnlyIncreasingBy(comparator) { it }

fun <T, K : Comparable<K>> Iterable<T>.takeOnlyIncreasingBy(selector: (T) -> K) = takeOnlyIncreasingBy(naturalOrder(), selector)

fun <T, K> Iterable<T>.takeOnlyIncreasingBy(
    comparator: Comparator<K>,
    selector: (T) -> K,
): List<T> {
    var max: K? = null
    val result = mutableListOf<T>()

    for (element in this) {
        val value = selector(element)

        if (isNull(max) || comparator.compare(value, max) > 0) {
            result += element
            max = value
        }
    }

    return result
}

fun <T : Comparable<T>> Iterable<T>.takeVisibleFromHeight(height: T) = takeVisibleFromHeightBy(height, naturalOrder())

fun <T> Iterable<T>.takeVisibleFromHeightBy(height: T, comparator: Comparator<T>) = takeVisibleFromHeightBy(height, comparator) { it }

fun <T, K : Comparable<K>> Iterable<T>.takeVisibleFromHeightBy(
    height: K,
    selector: (T) -> K
) = takeVisibleFromHeightBy(height, naturalOrder(), selector)

/**
 *                 6
 * X           5   6
 * 5       4   5   6
 * 5       4   5   6
 * 5   2   4   5   6
 * 5 1 2 1 4 1 5 1 6
 *
 * [1,2,1,4,5] visible from X
 * [1, 6] is not visible
 */
fun <T, K> Iterable<T>.takeVisibleFromHeightBy(
    height: K,
    comparator: Comparator<K>,
    selector: (T) -> K,
): List<T> {
    val result = mutableListOf<T>()

    for (element in this) {
        val value = selector(element)

        result += element
        if (comparator.compare(value, height) >= 0) {
            break
        }
    }

    return result
}

fun Iterable<Int>.product() = this.fold(1, Int::times)
inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int = this.map(selector).fold(1, Int::times)

fun Iterable<Long>.product() = this.fold(1L, Long::times)

@OptIn(kotlin.experimental.ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long = this.map(selector).fold(1L, Long::times)

fun <T : Comparable<T>> Iterable<T>.minMaxOrNull(): Pair<T, T>? = minMaxByOrNull { it }

fun <T, K : Comparable<K>> Iterable<T>.minMaxByOrNull(selector: (T) -> K): Pair<T, T>? {
    val iterator = iterator()

    if (!iterator.hasNext()) {
        return null
    }

    var minElement = iterator.next()
    var maxElement = minElement
    var minValue = selector(minElement)
    var maxValue = minValue

    for (element in iterator()) {
        val value = selector(element)

        if (value < minValue) {
            minElement = element
            minValue = value
        } else if (maxValue < value) {
            maxElement = element
            maxValue = value
        }
    }

    return minElement to maxElement
}