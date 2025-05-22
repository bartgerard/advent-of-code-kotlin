package shared

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

/**
 * Generate Strings based on available options at each segment.
 * [["A"], ["B", "C"], ["D"]] -> ["ABD","ACD"]
 */
fun List<Collection<CharSequence>>.generate(): List<String> = fold(listOf("")) { acc, options ->
    acc.flatMap { previous -> options.map { previous + it } }
}

fun <T> Collection<T>.combinations() = flatMap { i -> map { j -> Pair(i, j) } }

fun List<String>.allShortest() = minOf { it.length }
    .let { min -> filter { it.length == min } }

infix fun <T> Iterable<T>.difference(other: Iterable<T>) = (this union other) - (this intersect other)

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