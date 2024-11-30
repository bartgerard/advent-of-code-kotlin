package shared

fun <T> union(sets: Collection<Set<T>>): Set<T> = sets.flatten().fold(emptySet()) { acc, set -> acc + set }

fun <T> union(lists: List<List<T>>): List<T> = lists.flatten().fold(emptyList()) { acc, list -> acc + list }

fun <T> List<T>.groupByIndexRemainder(divisor: Int): List<List<T>> {
    return this.indices
        .groupBy { it % divisor }
        .map { (_, indices) -> indices.map { this[it] } }
}