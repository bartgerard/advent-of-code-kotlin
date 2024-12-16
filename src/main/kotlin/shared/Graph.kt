package shared

import java.util.*

/*
data class Vertex<T>(
    val label: String,
    val values: T
)

data class Edge(
    val label: String,
    val v1: String,
    val v2: String
)

 */

class Dijkstra {
    companion object {
        fun <E> findShortestPath(
            start: E,
            isEnd: (E) -> Boolean,
            neighbors: (E) -> List<E>,
            costFunction: (current: E, next: E) -> Long? = { _, _ -> 1 }
        ): Solution<E> {
            val nextVertices = PriorityQueue<Vertex<E>>(compareBy { it.cost })
            nextVertices.add(Vertex(start, 0L))

            val path = mutableMapOf<E, Path<E>>(start to Path(null, 0L))

            while (nextVertices.isNotEmpty()) {
                val currentVertex = nextVertices.poll()
                val currentEdge = currentVertex.destination

                if (isEnd(currentEdge)) {
                    return Solution<E>(start, currentEdge, path)
                }

                val newVertices = neighbors(currentEdge)
                    .filter { !path.containsKey(it) }
                    .map { Vertex(it, currentVertex.cost + (costFunction.invoke(currentEdge, it) ?: Long.MAX_VALUE)) }

                nextVertices += newVertices
                newVertices.forEach { path.put(it.destination, Path(currentEdge, it.cost)) }
            }

            return Solution(start, null, path);
        }
    }

}

data class Vertex<E>(
    val destination: E,
    val cost: Long
)

data class Path<E>(
    val source: E?,
    val cost: Long
)

data class Solution<E>(
    val source: E,
    val destination: E?,
    val path: Map<E, Path<E>>
) {
    fun cost() = path[destination]?.cost ?: Long.MAX_VALUE

    fun fullPath() = fullPathTo(destination)
    fun fullPathTo(destination: E?): List<E> {
        val result = mutableListOf<E>()
        var current = destination

        while (current != null) {
            result.add(current)
            current = path[current]!!.source
        }

        return result.reversed()
    }
}

/*
fun main() {
    val cost: Map<String, Map<String, Long>> = mapOf("a" to mapOf("b" to 1L))

    val result = Dijkstra.findShortestPath("a") { from: String, to: String -> cost[from]?.get(to) }
}

 */
