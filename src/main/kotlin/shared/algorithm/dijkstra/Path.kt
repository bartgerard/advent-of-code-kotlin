package shared.algorithm.dijkstra

data class Path<E>(
    val steps: MutableMap<E, Step<E>>
) : MutableMap<E, Step<E>> by steps {
    constructor(start: E) : this(mutableMapOf(start to Step(null, 0L)))

    fun costTo(point: E?): Long = steps[point]?.cost ?: Long.MAX_VALUE

    fun fullPathTo(destination: E?): List<E> {
        val result = mutableListOf<E>()
        var current = destination

        while (current != null) {
            result.add(current)
            current = steps[current]!!.source
        }

        return result.reversed()
    }
}