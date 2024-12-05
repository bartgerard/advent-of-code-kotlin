package shared

fun <T> union(sets: Collection<Set<T>>): Set<T> = sets.flatten().fold(emptySet()) { acc, set -> acc + set }

fun <T> union(lists: List<List<T>>): List<T> = lists.flatten().fold(emptyList()) { acc, list -> acc + list }

fun <T> List<T>.groupByIndexRemainder(divisor: Int): List<List<T>> {
    return this.indices
        .groupBy { it % divisor }
        .map { (_, indices) -> indices.map { this[it] } }
}

fun <T> List<List<T>>.transpose() = this[0].indices.map { column -> this.indices.map { row -> this[row][column] } }

//fun <T> List<T>.frequencies() = this.groupBy { it }.mapValues { it.value.size }
fun <T> List<T>.frequencies() = this.groupingBy { it }.eachCount()


fun <T> List<T>.withoutIndex(index: Int) = this.filterIndexed { i, _ -> i != index }

//fun List<Long>.intervals() = (1..<this.size).map { this[it] - this[it - 1] }
fun List<Long>.intervals() = this.zipWithNext { a, b -> b - a }
