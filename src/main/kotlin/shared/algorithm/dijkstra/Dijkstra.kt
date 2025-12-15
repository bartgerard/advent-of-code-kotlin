package shared.algorithm.dijkstra

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
                    return Solution(start, currentEdge, path)
                }

                val newVertices = neighbors(path, currentEdge)
                    .filter { !path.containsKey(it) }
                    .map { Vertex(it, currentVertex.cost + (costFunction.invoke(currentEdge, it) ?: Long.MAX_VALUE)) }

                nextVertices += newVertices
                newVertices.forEach { path.put(it.destination, Step(currentEdge, it.cost)) }
            }

            return Solution(start, null, path)
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

