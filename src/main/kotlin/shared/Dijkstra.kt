package shared

import java.util.*

class Dijkstra {
    companion object {
        fun <E> findShortestPath(
            start: E,
            isEnd: (E) -> Boolean,
            neighbors: (path: Path<E>, current: E) -> List<E>,
            costFunction: (current: E, next: E) -> Long? = { _, _ -> 1 }
        ): Solution<E> {
            val nextVertices = PriorityQueue<Vertex<E>>(compareBy { it.cost })
            nextVertices.add(Vertex(start, 0L))

            val path = Path(start)

            while (nextVertices.isNotEmpty()) {
                val currentVertex = nextVertices.poll()
                val currentEdge = currentVertex.destination

                if (isEnd(currentEdge)) {
                    return Solution<E>(start, currentEdge, path)
                }

                val newVertices = neighbors(path, currentEdge)
                    .filter { !path.containsKey(it) }
                    .map { Vertex(it, currentVertex.cost + (costFunction.invoke(currentEdge, it) ?: Long.MAX_VALUE)) }

                nextVertices += newVertices
                newVertices.forEach { path.put(it.destination, Step(currentEdge, it.cost)) }
            }

            return Solution(start, null, path);
        }

        fun <E> findShortestPaths(
            start: E,
            isEnd: (E) -> Boolean,
            neighbors: (E) -> List<E>,
            costFunction: (current: E, next: E) -> Long? = { _, _ -> 1 }
        ): Solutions<E> {
            val nextVertices = PriorityQueue<Vertex<E>>(compareBy { it.cost })
            nextVertices.add(Vertex(start, 0L))
            val previousCosts = mutableMapOf<E, Long>()

            val paths = Paths(start)
            var end: E? = null

            while (end == null && nextVertices.isNotEmpty()) {
                val currentVertex = nextVertices.poll()
                val currentEdge = currentVertex.destination

                if (isEnd(currentEdge)) {
                    end = currentEdge
                }

                val newVertices = neighbors(currentEdge)
                    .map { Vertex(it, currentVertex.cost + (costFunction.invoke(currentEdge, it) ?: Long.MAX_VALUE)) }

                for (newVertex in newVertices) {
                    val previousCost = previousCosts[newVertex.destination] ?: Long.MAX_VALUE

                    if (previousCost < newVertex.cost) {
                        continue
                    }

                    val nextStep = Step(currentEdge, newVertex.cost)

                    if (newVertex.cost < previousCost) {
                        previousCosts[newVertex.destination] = newVertex.cost
                        paths[newVertex.destination] = mutableSetOf(nextStep)
                        nextVertices += newVertex
                    } else {
                        paths[newVertex.destination]!! += nextStep
                    }
                }
            }

            return Solutions(start, end, paths)
        }
    }

}

data class Vertex<E>(
    val destination: E,
    val cost: Long
)

data class Step<E>(
    val source: E?,
    val cost: Long
)

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

data class Solution<E>(
    val source: E,
    val destination: E?,
    val path: Path<E>
) {
    fun cost() = path.costTo(destination)

    fun fullPath() = path.fullPathTo(destination)
}

data class Paths<E>(
    val steps: MutableMap<E, MutableSet<Step<E>>>
) : MutableMap<E, MutableSet<Step<E>>> by steps {
    constructor(start: E) : this(mutableMapOf(start to mutableSetOf(Step(null, 0L))))
}

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