package shared

import java.util.Collections.unmodifiableList
import java.util.Collections.unmodifiableSet

operator fun <T> Set<T>.plus(other: Set<T>): Set<T> {
    val result = HashSet(this)
    result.addAll(other)
    return unmodifiableSet(result)
}

operator fun <T> List<T>.plus(other: List<T>): List<T> {
    val result = ArrayList(this)
    result.addAll(other)
    return unmodifiableList(result)
}

fun <T> List<T>.groupByIndexRemainder(divisor: Int): List<List<T>> {
    return this.indices
        .groupBy { it % divisor }
        .map { (_, indices) -> indices.map { this[it] } }
}