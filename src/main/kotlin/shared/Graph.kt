package shared

data class Graph<T>(
    val nodes: MutableMap<T, MutableSet<T>> = mutableMapOf()
) {
    constructor(edges: List<Pair<T, T>>) : this() {
        edges.forEach { addEdge(it) }
    }

    fun addNode(value: T): MutableSet<T> = nodes.computeIfAbsent(value) { mutableSetOf() }

    fun addEdge(edge: Pair<T, T>) {
        val node1 = addNode(edge.first)
        val node2 = addNode(edge.second)

        node1 += edge.second
        node2 += edge.first
    }

    fun findAllPaths(
        start: T,
        end: T,
        canVisit: (T, List<T>) -> Boolean,
    ): List<List<T>> {
        val results = mutableListOf<List<T>>()

        fun visit(current: T, path: List<T>) {
            if (current == end) {
                results += path + current
                return
            }

            nodes[current]?.forEach { neighbor ->
                if (canVisit(neighbor, path)) {
                    visit(neighbor, path + current)
                }
            }
        }

        visit(start, emptyList())
        return results
    }

}