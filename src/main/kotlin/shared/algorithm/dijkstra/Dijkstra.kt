package shared.algorithm.dijkstra

import java.util.*


class Dijkstra {
    companion object {
        @JvmStatic
        fun <E> findShortestPath(
            start: E,
            isEnd: (E) -> Boolean,
            neighbors: (path: Path<E>, current: E) -> List<E>,
            costFunction: (current: E, next: E) -> Long? = { _, _ -> 1 }
        ): Solution<E> {
            val nextVertices = PriorityQueue<Vertex<E>>(compareBy { it.cost })
            nextVertices.add(Vertex(start, 0L))

            val path = Path(start)
            val settled = mutableSetOf<E>()

            while (nextVertices.isNotEmpty()) {
                val currentVertex = nextVertices.poll()
                val current = currentVertex.destination

                if (!settled.add(current)) {
                    // stale queue entry, a cheaper one was already processed
                    continue
                }

                if (isEnd(current)) {
                    return Solution(start, current, path)
                }

                for (next in neighbors(path, current)) {
                    if (next in settled) {
                        continue
                    }

                    // null cost: the edge is not traversable
                    val cost = currentVertex.cost + (costFunction(current, next) ?: continue)

                    if (cost < path.costTo(next)) {
                        path[next] = Step(current, cost)
                        nextVertices += Vertex(next, cost)
                    }
                }
            }

            return Solution(start, null, path)
        }

        @JvmStatic
        fun <E> findShortestPaths(
            start: E,
            isEnd: (E) -> Boolean,
            neighbors: (E) -> List<E>,
            costFunction: (current: E, next: E) -> Long? = { _, _ -> 1 }
        ): Solutions<E> {
            val nextVertices = PriorityQueue<Vertex<E>>(compareBy { it.cost })
            nextVertices.add(Vertex(start, 0L))
            val previousCosts = mutableMapOf(start to 0L)

            val paths = Paths(start)
            var end: E? = null

            while (end == null && nextVertices.isNotEmpty()) {
                val currentVertex = nextVertices.poll()
                val currentEdge = currentVertex.destination

                if (currentVertex.cost > (previousCosts[currentEdge] ?: Long.MAX_VALUE)) {
                    // stale queue entry, a cheaper one was already processed
                    continue
                }

                if (isEnd(currentEdge)) {
                    end = currentEdge
                }

                // null cost: the edge is not traversable
                val newVertices = neighbors(currentEdge)
                    .mapNotNull { next ->
                        costFunction(currentEdge, next)?.let { cost -> Vertex(next, currentVertex.cost + cost) }
                    }

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

