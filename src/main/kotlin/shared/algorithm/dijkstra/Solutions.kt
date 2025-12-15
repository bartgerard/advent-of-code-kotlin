package shared.algorithm.dijkstra

data class Solutions<E>(
    val source: E,
    val destination: E?,
    val path: Paths<E>
) {
    fun cost() = path[destination]?.first()?.cost ?: Long.MAX_VALUE

    fun vertices(): Set<E> {
        val result = mutableSetOf<E>()
        val remaining = mutableListOf(destination ?: return result)

        while (remaining.isNotEmpty()) {
            val vertex = remaining.removeFirst()
            result.add(vertex)
            remaining += path[vertex]!!.mapNotNull { it.source }
                .filter { it !in result && it !in remaining }
        }

        return result
    }
}